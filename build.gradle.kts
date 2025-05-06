val kotlin_version: String by project
val logback_version: String by project
val ktor_version: String by project

plugins {
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.1.2"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"
}

group = "payment2go.co.id"
version = "0.0.2"

application {
    mainClass = "io.ktor.server.netty.EngineMain"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-server-call-logging")
    implementation("io.ktor:ktor-server-netty")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    // Ktor Client
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-client-logging:2.3.10")
}

ktor {
    fatJar {
        archiveFileName.set("fat.jar")
    }
}

tasks.register("generateVersionFile") {
    val outputDir = file("src/main/kotlin/generated")
    outputs.dir(outputDir)
    doLast {
        outputDir.mkdirs()
        val versionFile = file("$outputDir/Version.kt")
        versionFile.writeText("""
            object Version {
                const val APP_VERSION = "${project.version}"
            }
        """.trimIndent())
    }
}

sourceSets["main"].java.srcDir("src/main/kotlin/generated")

// Pastikan task generateVersionFile dijalankan sebelum compileKotlin
tasks.named("compileKotlin") {
    dependsOn("generateVersionFile")
}
