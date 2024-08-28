package org.youmatch.app.repositories

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import youmatch.shared.user.UserModel

@Repository
public interface UserRepository: MongoRepository<UserModel, String> {}