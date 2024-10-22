plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

group = "dev.eco.assign"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("myPlugin") {
            id = "dev.eco.assign.description-plugin"
            implementationClass = "dev.eco.assign.DescriptionPlugin"
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
