package org.youmatch.app.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.youmatch.app.dto.EnterUserDto
import org.youmatch.app.services.IExtendUserService
import org.youmatch.app.repositories.UserRepository
import youmatch.shared.user.Gender
import youmatch.shared.user.Statuses
import youmatch.shared.user.UserMetadata
import youmatch.shared.user.UserModel

// MOCK
@Service
class MockUserServiceImpl: IExtendUserService {

    @Autowired
    lateinit var usersRepository: UserRepository

    override fun enter(dto: EnterUserDto): UserModel {
        return usersRepository.insert(UserModel(
            tgId = dto.user.tgId,
            status = Statuses.analyzed,
            balance = 0,
            gender = Gender.male,
            loveLanguages = listOf(),
            viewedUsers = listOf(),
            metadata = UserMetadata(cohort = "", subCohortLove = 0)
        ))
    }
}