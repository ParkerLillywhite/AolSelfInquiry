plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("junit:junit:4.13.1")
    implementation("com.h2database:h2:1.4.191")
    implementation("org.springframework:spring-core:6.0.9")
    implementation("org.springframework:spring-web:6.0.9")
    implementation("org.springframework.boot:spring-boot-starter:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-data-rest:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-security:3.1.0")
    implementation("jakarta.persistence:jakarta.persistence-api:2.2.3")
    implementation("mysql:mysql-connector-java:8.0.27")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("org.projectlombok:lombok:1.18.26")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.mockito:mockito-core:2.24.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

}

tasks.test {
    useJUnitPlatform()
}