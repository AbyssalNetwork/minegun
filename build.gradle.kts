plugins {
    `java-library`
    `maven-publish`
}

// 1. Updated to your custom domain
group = "org.vardinsdev"
version = "1.0.0" // Removed SNAPSHOT for a cleaner first release

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
    // Optional: Standard practice for libraries to include sources/docs
    withSourcesJar()
    withJavadocJar()
}

// 2. Added the Publishing block for JitPack
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

tasks.register<JavaExec>("run") {
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.minegun.demo.Main")
}

repositories {
    mavenCentral()
    // Added Minestom's repo if it's not in Central
    maven("https://jitpack.io")
}

dependencies {
    implementation("net.minestom:minestom:2026.03.03-1.21.11")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}