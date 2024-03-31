import io.papermc.hangarpublishplugin.model.Platforms

plugins {
    id("java")
    id("io.papermc.hangar-publish-plugin") version "0.1.0"
}

group = "io.github.alejomc26"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

hangarPublish {
    publications.register("plugin") {
        version.set(project.version as String)
        channel.set("Snapshot") // We're using the 'Snapshot' channel
        id.set("hangar-project")
        apiKey.set(System.getenv("HANGAR_API_TOKEN"))
        platforms {
            register(Platforms.PAPER) {
                // TODO: If you're using ShadowJar, replace the jar lines with the appropriate task:
                //   jar.set(tasks.shadowJar.flatMap { it.archiveFile })
                // Set the jar file to upload
                jar.set(tasks.jar.flatMap { it.archiveFile })

                // Set platform versions from gradle.properties file
                val versions: List<String> = (property("paperVersion") as String)
                        .split(",")
                        .map { it.trim() }
                platformVersions.set(versions)

                // TODO: Configure your plugin dependencies, if any
                dependencies {
                    // Example for a dependency found on Hangar
                    hangar("Maintenance") {
                        required.set(false)
                    }
                    // Example for an external dependency
                    url("Debuggery", "https://github.com/PaperMC/Debuggery") {
                        required.set(true)
                    }
                }
            }
        }
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.jar {
    destinationDirectory.set(file("/C:/Games/1.20.1/plugins"))
}