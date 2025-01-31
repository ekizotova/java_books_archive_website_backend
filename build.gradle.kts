plugins {
	id("java")
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.asciidoctor.jvm.convert") version "3.3.2"
}

group = "cvut.fit"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}



extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
	testImplementation("org.springframework.security:spring-security-test")
	//testImplementation("org.junit.jupiter:junit-jupiter-api")
	//testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
	testImplementation("org.mockito:mockito-core:3.12.4")
	implementation("javax.servlet:javax.servlet-api:4.0.1")
	implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
	implementation("io.springfox:springfox-swagger2:2.6.1")
	implementation("io.springfox:springfox-swagger-ui:2.6.1")
	implementation("io.springfox:springfox-boot-starter:3.0.0")
	testImplementation("com.h2database:h2")

}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
