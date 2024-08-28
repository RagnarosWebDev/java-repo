package org.youmatch.app.impl

import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Filters.nin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import org.youmatch.app.services.IExtendsFeedService
import youmatch.shared.feed.FeedModel
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.Aggregation.match
import org.springframework.data.mongodb.core.aggregation.ArrayOperators
import org.springframework.data.mongodb.core.aggregation.BooleanOperators.And.and
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.youmatch.app.repositories.UserRepository
import youmatch.shared.user.Statuses
import youmatch.shared.user.UserModel

val closeLoveCohortsMap = mapOf(
    Pair("0", listOf()),
    Pair("1", listOf("3", "5", "9", "17")),
    Pair("2", listOf("3", "6", "10", "18")),
    Pair("3", listOf("1", "2", "5", "6", "9", "10", "17", "18")),
    Pair("4", listOf("5", "6", "12", "20")),
    Pair("5", listOf("1", "3", "4", "6", "9", "12", "17", "20")),
    Pair("6", listOf("2", "3", "4", "5", "10", "12", "18", "20")),
    Pair("8", listOf("9", "10", "12", "24")),
    Pair("9", listOf("1", "3", "5", "8", "10", "12", "17", "24")),
    Pair("10", listOf("2", "3", "6", "8", "9", "12", "18")),
    Pair("12", listOf("4", "5", "6", "8", "9", "10", "20", "24")),
    Pair("16", listOf("17", "18", "20", "24")),
    Pair("17", listOf("1", "3", "5", "9", "16", "18", "20", "24")),
    Pair("18", listOf("2", "3", "6", "10", "16", "17", "20", "24")),
    Pair("20", listOf("4", "5", "6", "12", "16", "17", "18", "24")),
    Pair("24", listOf("8", "9", "10", "12", "16", "17", "18", "20")),
)

@Service
class FeedServiceImpl: IExtendsFeedService {
    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun formFeeds(userId: Int, limit: Int): List<FeedModel> {
        val user = userRepository.findByTgId(userId)

        val closeLoveArray = closeLoveCohortsMap[user.metadata.subCohortLove.toString()]!!

        val compatibilityAggregation = Aggregation.addFields()
            .addField("compatibility").withValue(
                ConditionalOperators.Cond.`when`(Criteria.where("metadata.subCohortLove").ne(null)).then(
                    ConditionalOperators.Cond.`when`(Criteria.where("metadata.subCohortLove").`is`(user.metadata.subCohortLove)).then(80)
                        .otherwise(
                            ConditionalOperators.Cond.`when`(ArrayOperators.In.arrayOf("metadata.subCohortLove").containsValue(closeLoveArray)).then(70)
                                .otherwise(60)
                        )
                ).otherwise(60)
            ).build()

        val result = mongoTemplate.aggregate(
            Aggregation.newAggregation(
                listOf(
                    match(
                        Criteria.where("status").`is`(Statuses.analyzed)
                            .and("tgId").nin(user.viewedUsers + user.tgId)
                    ),
                    Aggregation.limit(limit.toLong()),
                    compatibilityAggregation,
                    Aggregation.sort(Sort.by("compatibility")),
                    Aggregation.addFields().addField("userModel").withValue("\$\$ROOT").build()
                )
            ),
            "users",
            FeedModel::class.java
        ).mappedResults


        userRepository.save(user.copy(viewedUsers = user.viewedUsers + result.map { it.userModel.tgId }))

        return result
    }
}