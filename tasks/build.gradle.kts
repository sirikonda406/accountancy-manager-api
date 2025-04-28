plugins {
    java
}


tasks.test {
    useJUnitPlatform()
    maxParallelForks = 1
}

dependencies {
    implementation(project(":common"))

    //testImplementation("com.github.javafaker:javafaker")
    //testImplementation("org.testcontainers:junit-jupiter")
    //testImplementation("org.testcontainers:postgresql")

    //testRuntimeOnly("org.postgresql:postgresql") {
    //   because("tests need the Postgres JDBC driver")
    //}
}
