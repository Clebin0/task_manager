plugins {
    kotlin("jvm") version "2.2.20"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.xerial:sqlite-jdbc:3.50.3.0")
    testImplementation(kotlin("test-junit5"))
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.12.2")
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("GerenciadorTarefasKt")
}

tasks.test {
    useJUnitPlatform()
}
