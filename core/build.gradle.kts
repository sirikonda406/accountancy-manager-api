plugins {
    id("org.springframework.boot") version "3.3.4"
}

tasks.bootRun {
    systemProperties.set("spring.profiles.active", "DEV")
}

tasks.withType<Jar>(){
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

springBoot {
    mainClass.set("com.ca.account.manager.CAAccountancyManager")
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    this.mainClass.set("com.ca.account.manager.CAAccountancyManager")
    this.archiveFileName.set("accountancy-manager-0.0.1-SNAPSHOT.jar")
}

tasks.bootBuildImage{
    verboseLogging=true
    imageName="accountancy-manager:dev"
}


tasks.test {
    useJUnitPlatform()
    maxParallelForks = 1
}

dependencies {
    implementation(project(":common"))
    implementation(project(":billing"))
    implementation(project(":reports"))
    implementation(project(":payments"))
    implementation(project(":security"))
    implementation(project(":tasks"))

    implementation("org.springframework.boot:spring-boot-starter")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")

    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude(group = "org.apache.tomcat.embed", module = "tomcat-embed-el")
    }
}

