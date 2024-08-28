package org.youmatch.app.repositories

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import youmatch.shared.user.UserModel

@Repository
public interface UserRepository: MongoRepository<UserModel, String> {
    fun findByTgId(tgId: Number): UserModel
}