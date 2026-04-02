plugins {
    war
    id("io.spring.dependency-management") version "1.1.6"
    application
}

group = "ru.bsuedu.cad.lab"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework:spring-framework-bom:6.1.14")
        mavenBom("org.springframework.data:spring-data-bom:2024.0.5")
    }
}

dependencies {
    implementation("org.springframework:spring-webmvc")
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-orm")
    implementation("org.springframework.data:spring-data-jpa")
    implementation("org.hibernate.orm:hibernate-core:6.4.4.Final")
    implementation("com.h2database:h2:2.2.224")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.1")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.16.1")
    implementation("org.thymeleaf:thymeleaf-spring6:3.1.2.RELEASE")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("jakarta.transaction:jakarta.transaction-api:2.0.1")
    implementation("ch.qos.logback:logback-classic:1.5.6")
}

tasks.withType<War> {
    archiveFileName.set("zooshop.war")
    webXml = file("src/main/webapp/WEB-INF/web.xml")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

application {
    mainClass.set("ru.bsuedu.cad.lab.app.App")
}