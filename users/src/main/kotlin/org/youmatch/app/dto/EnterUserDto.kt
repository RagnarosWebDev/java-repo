package org.youmatch.app.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize
data class TelegramUserInfo  @JsonCreator constructor(val tgId: Int, val isMale: Boolean)


@JsonSerialize
data class EnterUserDto @JsonCreator constructor(val user: TelegramUserInfo)