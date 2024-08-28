package org.youmatch.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories(basePackages = arrayOf("org.youmatch.app.repositories"))
open class SpringApp

fun main(args: Array<String>) {
    runApplication<SpringApp>(*args)
}
