plugins {
    id("java")
    id("org.openapi.generator").version("7.7.0")
    id("jacoco")
}

group   = "com.ronreynolds"
version = "0.0.1-SNAPSHOT"

// library versions
val assertJVersion          = "3.24.2"
val commonsLangVersion      = "3.14.0"
val findBugsVersion         = "3.0.2"
val guavaVersion            = "33.2.1-jre"
val httpComponentsVersion   = "4.5.14"
val jacocoVersion           = "0.8.10"
val javaxAnnotationVersion  = "1.3.2"
val jUnitJupiterVersion     = "5.9.2"
val lombokVersion           = "1.18.32"
val slf4jVersion            = "1.7.25"
val buildDirectory          = layout.buildDirectory.get()

val openapiSource           = "$rootDir/src/main/resources/openapi-3.1.0-smartsheet-v2.yaml"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    // needed by openapi-generated code (javax.annotation.Generated, apache.http.*)
    implementation("javax.annotation:javax.annotation-api:$javaxAnnotationVersion")
    implementation("com.google.code.findbugs:jsr305:$findBugsVersion")    // javax.annotation.Nullable (not in javax-api)
    implementation("org.apache.httpcomponents:httpclient:$httpComponentsVersion") {   // request-building (even with native client)
        exclude(group = "commons-logging", module = "commons-logging")
    }
    implementation("org.apache.httpcomponents:httpmime:$httpComponentsVersion")

    // Lombok dependencies
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    // test dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.jupiter:junit-jupiter:$jUnitJupiterVersion")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
}

sourceSets {
    main {
        java {
            srcDirs("src/main/java", "$buildDirectory/generated/api/src/main/java")
        }
    }
}

openApiValidate {
    inputSpec.set(openapiSource)
}

openApiGenerate {
    generatorName.set("java")
    inputSpec.set(openapiSource)
    outputDir.set("$buildDirectory/generated/api")
    library.set("native")
    configOptions.set(mutableMapOf(
        "invokerPackage"          to "com.ronreynolds.smartsheet",
        "apiPackage"              to "com.ronreynolds.smartsheet.api",
        "modelPackage"            to "com.ronreynolds.smartsheet.model",
        "openApiNullable"         to "false",
        "hideGenerationTimestamp" to "true",
        "useEnumCaseInsensitive"  to "true", // some endpoints return enums in slightly different case than specified
//        "additionalModelTypeAnnotations" to "@lombok.Value @lombok.NoArgsConstructor @lombok.Builder"
    ))
}

tasks.compileJava {
    dependsOn(tasks.openApiGenerate)
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}

tasks.withType<Test> {
    useJUnitPlatform()
}