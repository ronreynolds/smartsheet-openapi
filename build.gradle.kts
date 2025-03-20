plugins {
    id("java")
    id("maven-publish")
    id("org.openapi.generator").version("7.12.0") // latest as of 2025-02-28
}

group               = "com.ronreynolds"
version             = "0.1.2-SNAPSHOT"
val buildDirectory  = layout.buildDirectory.get()
val openapiSource   = "$rootDir/src/main/resources/smartsheet-v2-openapi-v3.0.3.json"

// library versions
val assertJVersion              = "3.27.3"      // 2025-01-18
val findBugsVersion             = "3.0.2"       // from build/generated/api/build.gradle
val jacksonVersion              = "2.17.1"      // from build/generated/api/build.gradle
val jakartaAnnotationVersion    = "1.3.5"       // from build/generated/api/build.gradle
val jUnitJupiterVersion         = "5.10.2"      // from build/generated/api/build.gradle
val logbackVersion              = "1.5.17"      // 2025-02-25
val lombokVersion               = "1.18.32"
val slf4jVersion                = "1.7.25"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    //
    // needed by openapi-generated code (javax.annotation.Generated, apache.http.*)
    implementation("com.google.code.findbugs:jsr305:$findBugsVersion")    // for javax.annotation.Nullable
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("jakarta.annotation:jakarta.annotation-api:$jakartaAnnotationVersion")

    //
    // needed by our code
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:jul-to-slf4j:$slf4jVersion") // openapi-gen logs to java.util.logging; route it into slf4j/logback
    runtimeOnly   ("ch.qos.logback:logback-classic:$logbackVersion")

    //
    // test dependencies
    testRuntimeOnly     ("org.junit.platform:junit-platform-launcher")
    testImplementation  ("org.junit.jupiter:junit-jupiter:$jUnitJupiterVersion")
    testImplementation  ("org.assertj:assertj-core:$assertJVersion")

    //
    // Lombok dependencies
    compileOnly             ("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor     ("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly         ("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor ("org.projectlombok:lombok:$lombokVersion")
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

// https://github.com/OpenAPITools/openapi-generator/blob/master/modules/openapi-generator-gradle-plugin/README.adoc
openApiGenerate {
    inputSpec.set(openapiSource)
    outputDir.set("$buildDirectory/generated/api")
    templateDir.set("$rootDir/src/main/resources/templates") // where the custom mustaches live...

    // packages to generate
    invokerPackage.set("$group.smartsheet")
    apiPackage.set("$group.smartsheet.api")
    modelPackage.set("$group.smartsheet.model")

    generatorName.set("java")   // language for client (duh)
    library.set("native")   // the HTTP client lib; see java-generator docs for full list

    generateApiTests.set(true)
    generateApiDocumentation.set(false)     // for now no point
    generateModelDocumentation.set(false)   // for now no point

    // when they say "verbose" they REALLY mean it (pipe to file if you set this to true)
    verbose.set(false)

    // some generator-specific config options - https://openapi-generator.tech/docs/generators/java/
    // https://github.com/OpenAPITools/openapi-generator/blob/master/modules/openapi-generator/src/main/java/org/openapitools/codegen/languages/AbstractJavaCodegen.java
    configOptions.set(mutableMapOf(
        "openApiNullable"         to "false", // OpenAPI Jackson Nullable library (not needed?)
        "hideGenerationTimestamp" to "true",  // seems kinda pointless
        "generateBuilders"        to "true",
    //  "useJakartaEe"            to "true"   // for Java-17+
    //  "asyncNative"             to "true" - async clients rather than synchronous blocking ones?
    ))
}

tasks.compileJava {
    options.encoding = "UTF-8"
    dependsOn(tasks.openApiGenerate)
}

tasks.compileTestJava {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}