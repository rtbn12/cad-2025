plugins {
    war
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // ========== SPRING CORE ==========
    implementation("org.springframework:spring-context:6.2.2")
    implementation("org.springframework:spring-webmvc:6.2.2")
    implementation("org.springframework:spring-orm:6.2.2")
    implementation("org.springframework.data:spring-data-jpa:3.3.2")

    // ========== SPRING SECURITY (лаба 7) ==========
    implementation("org.springframework.security:spring-security-web:6.2.2")
    implementation("org.springframework.security:spring-security-config:6.2.2")

    // ========== JPA / HIBERNATE ==========
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("org.hibernate.orm:hibernate-core:6.5.2.Final")

    // ========== DATABASE (H2 + Connection Pool) ==========
    implementation("com.h2database:h2:2.2.224")
    implementation("com.zaxxer:HikariCP:5.1.0")

    // ========== THYMELEAF (шаблоны HTML) ==========
    implementation("org.thymeleaf:thymeleaf-spring6:3.1.2.RELEASE")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.2.RELEASE")

    // ========== JSON (Jackson для REST) ==========
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.2")

    // ========== SERVLET API (для Tomcat) ==========
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")

    // ========== ЛОГГИРОВАНИЕ ==========
    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("ch.qos.logback:logback-classic:1.5.6")


}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "ru.bsuedu.cad.lab.app.App"
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}