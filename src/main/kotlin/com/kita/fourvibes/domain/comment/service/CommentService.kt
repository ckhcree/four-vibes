package com.kita.fourvibes.domain.comment.service

import com.kita.fourvibes.domain.comment.repository.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentService(commentRepository: CommentRepository)