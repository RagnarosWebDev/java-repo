package org.youmatch.app.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.youmatch.app.services.IExtendsFeedService
import youmatch.shared.feed.FeedModel


@RestController()
class FeedController {
    @Autowired
    lateinit var feetService: IExtendsFeedService

    @PostMapping("findPeople")
    fun enter(@RequestHeader(HttpHeaders.AUTHORIZATION) auth: String, @RequestParam("limit") limit: Int = 30): List<FeedModel> {
        val userId = auth.split(" ").last().toInt()
        return feetService.formFeeds(userId, limit)
    }
}