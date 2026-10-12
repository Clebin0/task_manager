plugins {
    kotlin("jvm") version "2.2.20"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.xerial:sqlite-jdbc:3.50.3.0")
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("GerenciadorTarefasKt")
}
