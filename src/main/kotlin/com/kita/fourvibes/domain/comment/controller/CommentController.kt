package com.kita.fourvibes.domain.comment.controller

import com.kita.fourvibes.domain.comment.controller.request.CommentRequest
import com.kita.fourvibes.domain.comment.controller.request.CommentResponse
import com.kita.fourvibes.domain.comment.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/bullets/{bulletId}/comments")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping
    fun createComment(
        @PathVariable bulletId: Long,
        @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(bulletId, commentRequest))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable bulletId: Long, @PathVariable commentId: Long,
        @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(bulletId, commentRequest))
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable bulletId: Long, @PathVariable commentId: Long,
        @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<Unit> {
        commentService.deleteComment(bulletId, commentRequest)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(null)
    }
}