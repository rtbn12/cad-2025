plugins {
    java
    war  // вместо application
}

repositories {
    mavenCentral()
}

dependencies {
    // Тестирование
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Guava
    implementation(libs.guava)

    // Spring и Hibernate
    implementation("org.springframework:spring-context:6.1.5")
    implementation("org.springframework:spring-jdbc:6.1.5")
    implementation("org.springframework:spring-orm:6.1.5")
    implementation("org.springframework:spring-tx:6.1.5")
    implementation("org.hibernate.orm:hibernate-core:6.4.4.Final")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.h2database:h2:2.2.224")
    implementation("org.springframework.data:spring-data-jpa:3.2.5")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("ch.qos.logback:logback-classic:1.5.6")

    // Jackson для JSON
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.0")

    // Servlet API — provided scope
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")
    testImplementation("jakarta.servlet:jakarta.servlet-api:6.1.0")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.0")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

// Настройка WAR-задачи
tasks.war {
    archiveFileName = "my-app.war"
}

// Кодировка для компиляции
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

// Кодировка для запуска
tasks.withType<JavaExec> {
    systemProperty("file.encoding", "UTF-8")
}

// Настройка тестов
tasks.named<Test>("test") {
    useJUnitPlatform()
}