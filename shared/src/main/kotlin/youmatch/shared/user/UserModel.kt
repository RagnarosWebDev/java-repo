package youmatch.shared.user

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

enum class Statuses {
    analyzed,
}

enum class Gender {
    male,
    female
}

data class UserMetadata(val cohort: String, val subCohortLove: Int)

@Document("users")
data class UserModel(
    @Id val id: ObjectId = ObjectId(),
    val tgId: Int,
    val status: Statuses,
    val balance: Int,
    val gender: Gender,
    val loveLanguages: List<String>,
    val metadata: UserMetadata,
    val viewedUsers: List<Int>
)