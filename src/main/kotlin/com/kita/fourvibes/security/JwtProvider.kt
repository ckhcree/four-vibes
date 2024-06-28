package com.kita.fourvibes.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.Date

@Component
class JwtProvider(
    @Value("\${auth.jwt.issuer}")
    private val issuer: String,

    @Value("\${auth.jwt.secret}")
    private val secret: String,

    @Value("\${auth.jwt.expiration-hour}")
    private val expirationHour: Long,
) {
    fun generateAccessToken(nickname: String, subject: String): String {
        return generateToken(nickname, subject, Duration.ofHours(expirationHour))
    }

    fun validateToken(jwt: String): Result<Jws<Claims>> {
        return runCatching {
            val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)
        }
    }

    private fun generateToken(nickname: String, subject: String, expirationPeriod: Duration): String {

        val now = Instant.now()
        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

        val claims: Claims = Jwts.claims().add(mapOf("nickname" to nickname)).build()

        return Jwts.builder()
            .subject(subject)
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }
}