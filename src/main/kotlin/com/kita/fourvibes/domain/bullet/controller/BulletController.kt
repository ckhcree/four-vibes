package com.kita.fourvibes.domain.bullet.controller

import com.kita.fourvibes.domain.bullet.controller.request.BulletResponse
import com.kita.fourvibes.domain.bullet.controller.request.CreateBulletRequest
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
@RequestMapping("/api/v1/bullet")
class BulletController(
    private val bulletService: BulletService,
) {
    @GetMapping("/getBullet")
    fun getAllBullet(): ResponseEntity<List<BulletResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(bulletService.findAllBullets())
    }

    @GetMapping("/getBullet/{id}")
    fun getBulletById(@PathVariable id: Long): ResponseEntity<BulletResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(bulletService.findBulletById(id))
    }

    @PostMapping("/createBullet")
    fun createBullet(@RequestBody @Valid createBulletRequest: CreateBulletRequest): ResponseEntity<BulletResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(bulletService.createBullet(createBulletRequest))
    }

    @PutMapping("/updateBullet/{id}")
    fun updateBullet(
        @PathVariable id: Long,
        @RequestBody updateBulletRequest: UpdateBulletRequest
    ): ResponseEntity<BulletResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(bulletService.updateBullet(id, updateBulletRequest))
    }

    @DeleteMapping("/deleteBullet/{id}")
    fun deleteBullet(@PathVariable id: Long): ResponseEntity<Unit> {
        bulletService.deleteBullet(id)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(null)
    }
}