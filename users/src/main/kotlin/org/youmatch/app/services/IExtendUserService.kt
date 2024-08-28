package org.youmatch.app.services;

import org.youmatch.app.dto.EnterUserDto
import youmatch.shared.user.IUserService
import youmatch.shared.user.UserModel

interface IExtendUserService: IUserService {
    fun enter(dto: EnterUserDto): UserModel
}
