package com.kita.fourvibes.domain.bullet.controller

import com.kita.fourvibes.domain.bullet.controller.request.BulletResponse
import com.kita.fourvibes.domain.bullet.controller.request.CreateBulletRequest
import com.kita.fourvibes.domain.bullet.controller.request.DeleteBulletRequest
import com.kita.fourvibes.domain.bullet.controller.request.UpdateBulletRequest
import com.kita.fourvibes.domain.bullet.service.BulletService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/bullets")
class BulletController(
    private val bulletService: BulletService,
) {
    @GetMapping()
    fun getAllBullet(): ResponseEntity<List<BulletResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(bulletService.findAllBullets())
    }

    @GetMapping("/{bulletId}")
    fun getBulletById(
        @PathVariable bulletId: Long
    ): ResponseEntity<BulletResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(bulletService.findBulletById(bulletId))
    }

    @PostMapping()
    fun createBullet(
        @RequestBody
        @Valid createBulletRequest: CreateBulletRequest
    ): ResponseEntity<BulletResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(bulletService.createBullet(createBulletRequest))
    }

    @PutMapping("/{bulletId}")
    fun updateBullet(
        @PathVariable bulletId: Long,
        @RequestBody updateBulletRequest: UpdateBulletRequest
    ): ResponseEntity<BulletResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(bulletService.updateBullet(bulletId, updateBulletRequest))
    }

    @DeleteMapping("/{bulletId}")
    fun deleteBullet(
        @PathVariable bulletId: Long,
        @RequestBody deleteBulletRequest: DeleteBulletRequest
    ): ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(bulletService.deleteBullet(bulletId, deleteBulletRequest))
    }

    @PostMapping("/{bulletId}/thumbsups")
    fun likeBullet(
        @PathVariable bulletId: Long,
    ): ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(bulletService.thumbsUp())
    }

    @DeleteMapping("/{bulletId}/thumbsups")
    fun cancelLikeBullet(
        @PathVariable bulletId: Long,
    ): ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(bulletService.cancelThumbsUp())
    }
}