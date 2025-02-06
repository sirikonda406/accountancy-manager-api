plugins {
    java
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.ca.account.manager"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

allprojects{
    apply(plugin = "io.spring.dependency-management")
    repositories {
        mavenCentral()
        google()
        maven {
            url = uri("https://maven.google.com")
        }
    }
}

sourceSets {
    create("integration-test") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

val integrationTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.implementation.get())
}


configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

subprojects {

    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "java")


    dependencies {
        implementation("org.slf4j:slf4j-api")

        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")

        implementation("org.modelmapper:modelmapper:3.2.0")
        implementation("org.apache.commons:commons-lang3:3.16.0")
        implementation("org.springframework.boot:spring-boot-starter-web")

        //Flyway
        implementation("org.flywaydb:flyway-core")
        implementation("org.flywaydb:flyway-database-postgresql")

        //API Documentation
        implementation("io.swagger:swagger-annotations:1.6.10")
        implementation("io.swagger.core.v3:swagger-annotations:2.2.8")

        //Fixtures
        testImplementation("org.springframework.boot:spring-boot-starter-web")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        implementation("org.springframework.security:spring-security-test")

        testImplementation("org.testcontainers:testcontainers")
        testImplementation("org.testcontainers:junit-jupiter")
        testImplementation("org.testcontainers:postgresql")

    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.5")
            mavenBom( "org.testcontainers:testcontainers-bom:1.20.1")
        }
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

}
