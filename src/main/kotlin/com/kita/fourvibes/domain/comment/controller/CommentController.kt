package com.kita.fourvibes.domain.comment.controller

import com.kita.fourvibes.domain.comment.service.CommentService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/comments")
class CommentController(commentService: CommentService)