plugins {
    id("java-library")
    kotlin("jvm")
}

group = "com.bindglam.felis"
version = property("version").toString()

repositories {
    mavenCentral()
}

dependencies {
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

kotlin {
    jvmToolchain(21)
}