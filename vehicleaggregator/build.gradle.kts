plugins {
	java
	id("org.springframework.boot") version "2.7.0"
	id("io.spring.dependency-management") version "1.1.5"
}

group = "com.fewservices"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	//testImplementation("io.projectreactor:reactor-test")
	//testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    //testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner:3.1.5") {
        //exclude(group="org.springframework.cloud", module="spring-cloud-contract-wiremock")
      //  exclude(group="commons-logging", module="commons-logging")
    //}
    //testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner:3.1.5")

    // https://mvnrepository.com/artifact/com.github.tomakehurst/wiremock-standalone
    //testImplementation("com.github.tomakehurst:wiremock:2.32.0")
    testImplementation("com.github.tomakehurst:wiremock-jre8:2.32.0")

    //testImplementation("org.wiremock:wiremock:3.6.0")
    //testImplementation("org.wiremock:wiremock-jetty12:3.6.0")
    //testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock:3.1.10")
}


/*dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.github.tomakehurst:wiremock-jre8:2.32.0")
    testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock:3.1.0")
    testImplementation("io.projectreactor:reactor-test")
}*/







tasks.withType<Test> {
	useJUnitPlatform()
}
