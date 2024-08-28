plugins {
    id("buildlogic.kotlin-application-conventions")
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
}

dependencies {
    implementation(project(":shared"))
    implementation("org.springframework.boot:spring-boot-starter-web:3.3.3")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb:3.3.3")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
}

application {
    mainClass = "org.youmatch.app.AppKt"
}
