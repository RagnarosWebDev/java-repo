package youmatch.shared.feed

import youmatch.shared.user.UserModel

data class FeedModel(val userModel: UserModel,
                     val compatibility: Int)