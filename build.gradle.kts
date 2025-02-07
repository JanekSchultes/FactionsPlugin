import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
    `java-library`
    id("org.spongepowered.gradle.plugin") version "2.2.0"
}

group = "org.example.factions"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.spongepowered.org/maven/") {
        name = "spongepowered-repo"
    }
}

sponge {
    apiVersion("8.2.0")
    license("GPL-3.0")
    loader {
        name(PluginLoaders.JAVA_PLAIN)
        version("1.0")
    }
    plugin("factionsmain") {
        displayName("FactionsMainPlugin")
        entrypoint("org.example.factions.factionsmain.FactionsMain")
        description("The official Factions plugin to the Barbaria server.")
        links {
            // homepage("https://spongepowered.org")
            // source("https://spongepowered.org/source")
            // issues("https://spongepowered.org/issues")
        }
        contributor("Janek Schultes") {
            description("Author")
        }
        dependency("spongeapi") {
            loadOrder(PluginDependency.LoadOrder.AFTER)
            optional(false)
        }
    }
}

val javaTarget = 16 // Sponge targets a minimum of Java 16
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(javaTarget))
}

tasks.withType<JavaCompile>().configureEach {
    options.apply {
        encoding = "utf-8" // Consistent source file encoding
        if (JavaVersion.current().isJava10Compatible) {
            release.set(javaTarget)
        }
    }
}

// Make sure all tasks which produce archives (jar, sources jar, javadoc jar, etc) produce more consistent output
tasks.withType<AbstractArchiveTask>().configureEach {
    isReproducibleFileOrder = true
    isPreserveFileTimestamps = false
}
