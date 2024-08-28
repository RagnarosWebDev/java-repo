package org.youmatch.app.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.youmatch.app.dto.EnterUserDto
import org.youmatch.app.services.IExtendUserService
import youmatch.shared.user.UserModel


@RestController()
class UsersController {
    @Autowired
    lateinit var userService: IExtendUserService

    @PostMapping("enter")
    fun enter(@RequestBody body: EnterUserDto): UserModel {
        return userService.enter(body)
    }
}