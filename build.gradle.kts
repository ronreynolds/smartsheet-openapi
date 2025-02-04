plugins {
    id("java")
    id("org.openapi.generator").version("7.11.0") // latest as of 2025-01-20
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
val jacksonVersion          = "2.17.1"  // from build/generated/api/build.gradle
val jakartaAnnotationVersion= "1.3.5"   // from build/generated/api/build.gradle
val javaxAnnotationVersion  = "1.3.2"
val jUnitJupiterVersion     = "5.10.2"  // from build/generated/api/build.gradle
val lombokVersion           = "1.18.32"
val slf4jVersion            = "1.7.25"

val buildDirectory          = layout.buildDirectory.get()
val openapiSource           = "$rootDir/src/main/resources/smartsheet-v2-openapi-v3.0.3.json"

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
    implementation("org.apache.httpcomponents:httpmime:$httpComponentsVersion")
    implementation("org.apache.httpcomponents:httpclient:$httpComponentsVersion") {   // request-building (even with native client)
        exclude(group = "commons-logging", module = "commons-logging")
    }
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
//    // for com.fasterxml.jackson.dataformat.yaml.YAMLFactory
//    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonVersion")
    implementation("org.openapitools:jackson-databind-nullable:0.2.1")
    implementation("jakarta.annotation:jakarta.annotation-api:$jakartaAnnotationVersion")

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
        "invokerPackage"            to "com.ronreynolds.smartsheet",
        "apiPackage"                to "com.ronreynolds.smartsheet.api",
        "modelPackage"              to "com.ronreynolds.smartsheet.model",
        "library"                   to "native",    // consider "okhttp-gson"
        "openApiNullable"           to "false",
        "hideGenerationTimestamp"   to "true",
        "useEnumCaseInsensitive"    to "true", // some endpoints return enums in slightly different case than specified
        "serializationLibrary"      to "jackson" // consider gson instead of jackson to serialize
        //        "additionalModelTypeAnnotations" to "@lombok.Value @lombok.NoArgsConstructor @lombok.Builder"
    ))
}

tasks.compileJava {
    options.encoding = "UTF-8"
    dependsOn(tasks.openApiGenerate)
}

tasks.compileTestJava {
    options.encoding = "UTF-8"
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