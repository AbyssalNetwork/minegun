plugins {
    `java-library`
    `maven-publish`
}

group = "org.vardinsdev"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
    withSourcesJar()
}

dependencies {
    implementation("net.minestom:minestom:2026.03.03-1.21.11")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}