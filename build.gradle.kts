plugins {
    id("java")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

tasks.register<JavaExec>("run") {
  classpath = sourceSets["main"].runtimeClasspath
  mainClass.set("com.minegun.demo.Main")
}

group = "com.minegun"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.minestom:minestom:2026.03.03-1.21.11")
}

tasks.test {
    useJUnitPlatform()
}
