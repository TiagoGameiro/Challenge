import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.21"
	kotlin("plugin.spring") version "1.5.21"
}

group = "com.angels"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation("com.google.code.gson:gson:2.8.6")
	implementation("org.springframework.boot:spring-boot-starter-hateoas")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	runtimeOnly("org.postgresql:postgresql")

	implementation("org.modelmapper:modelmapper:2.3.5")
	implementation ("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.12.5")
	implementation ("org.springframework.retry:spring-retry:1.1.2.RELEASE")

	implementation("org.jetbrains.exposed:exposed-core:0.34.2")
	implementation("org.jetbrains.exposed:exposed-dao:0.34.2")
	implementation("org.jetbrains.exposed:exposed-jdbc:0.34.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

springBoot {
	mainClassName = "com.angels.challenge.ChallengeApplicationKt"
}
