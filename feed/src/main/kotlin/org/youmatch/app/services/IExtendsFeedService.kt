package org.youmatch.app.services

import youmatch.shared.feed.FeedModel
import youmatch.shared.feed.IFeedService

interface IExtendsFeedService: IFeedService {
    fun formFeeds(userId: Int, limit: Int = 30): List<FeedModel>
}