package com.kita.fourvibes.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.Date

@PropertySource("classpath:jwt.yml")
@Component
class JwtProvider(
    @Value("\${issuer}") private val issuer: String,
    @Value("\${secret}") private val secret: String,
    @Value("\${expiration-hour}") private val expirationHour: Long,
) {
    fun generateToken(subject: String, expirationPeriod: Duration): String {
        val claims: Claims = Jwts.claims().build()

        val now = Instant.now()
        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

        return Jwts.builder()
            .subject(subject)
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }

    fun validateToken(jwt: String): Result<Jws<Claims>> {
        return runCatching {
            val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)
        }
    }
}