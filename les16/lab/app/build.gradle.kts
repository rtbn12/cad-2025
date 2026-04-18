plugins {
    application
    war
    jacoco
}

repositories {
    mavenCentral()
}

dependencies {
    // ===== SPRING CORE (НЕ BOOT!) =====
    implementation("org.springframework:spring-context:6.2.2")
    implementation("org.springframework:spring-web:6.2.2")
    implementation("org.springframework:spring-webmvc:6.2.2")
    implementation("org.springframework:spring-orm:6.2.2")
    implementation("org.springframework:spring-tx:6.2.2")

    // ===== SPRING SECURITY =====
    implementation("org.springframework.security:spring-security-web:6.4.2")
    implementation("org.springframework.security:spring-security-config:6.4.2")

    // ===== SPRING DATA JPA =====
    implementation("org.springframework.data:spring-data-jpa:3.3.5")

    // ===== JAKARTA PERSISTENCE (JPA) =====
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")

    // ===== HIBERNATE =====
    implementation("org.hibernate.orm:hibernate-core:6.6.4.Final")

    // ===== DATABASE =====
    implementation("com.h2database:h2:2.3.232")
    implementation("com.zaxxer:HikariCP:6.2.1")

    // ===== THYMELEAF =====
    implementation("org.thymeleaf:thymeleaf-spring6:3.1.2.RELEASE")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.2.RELEASE")

    // ===== JSON (JACKSON) =====
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.2")

    // ===== SERVLET API (для компиляции) =====
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")

    // ===== ЛОГГИРОВАНИЕ =====
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("ch.qos.logback:logback-classic:1.5.12")

    // ==========================================
    // ===== ТЕСТИРОВАНИЕ =====
    // ==========================================

    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.4")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.4")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.4")

    // Spring Test (для интеграционных тестов)
    testImplementation("org.springframework:spring-test:6.2.2")

    // Mockito
    testImplementation("org.mockito:mockito-core:5.14.2")
    testImplementation("org.mockito:mockito-junit-jupiter:5.14.2")

    // AssertJ (красивые ассерты)
    testImplementation("org.assertj:assertj-core:3.27.2")

    // H2 для интеграционных тестов (in-memory)
    testImplementation("com.h2database:h2:2.3.232")

    // Jakarta Servlet для тестов (если нужно)
    testCompileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")
}

// ===== НАСТРОЙКА ТЕСТОВ =====
tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)  // после тестов генерим отчёт JaCoCo
}

// ===== НАСТРОЙКА JACOCO =====
tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.01".toBigDecimal()  // 30% покрытия минимум (учебная цель)
            }
        }
    }
}

// ===== JAVA VERSION =====
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

// ===== WAR PLUGIN =====
tasks.war {
    archiveFileName.set("zooshop.war")
}

// ===== MAIN CLASS =====
application {
    mainClass.set("ru.bsuedu.cad.lab.app.App")
}