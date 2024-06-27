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
@RequestMapping("/api/v1/bullets/{id}/comments")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping
    fun createComment(
        @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(commentRequest))
    }

    @PutMapping("/{id}")
    fun updateComment(
        @PathVariable id: Long,
        @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(id, commentRequest))
    }

    @DeleteMapping("/{id}")
    fun deleteComment(
        @PathVariable id: Long,
        @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<Unit> {
        commentService.deleteComment(id, commentRequest)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(null)
    }
}