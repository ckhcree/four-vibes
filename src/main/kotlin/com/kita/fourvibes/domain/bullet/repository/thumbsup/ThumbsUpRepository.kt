package com.kita.fourvibes.domain.bullet.repository.thumbsup

import com.kita.fourvibes.domain.bullet.repository.model.thumbsup.ThumbsUp
import com.kita.fourvibes.domain.bullet.repository.model.thumbsup.ThumbsUpId
import org.springframework.data.jpa.repository.JpaRepository

interface ThumbsUpRepository : JpaRepository<ThumbsUp, ThumbsUpId>