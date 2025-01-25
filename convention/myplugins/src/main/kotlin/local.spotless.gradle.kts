plugins {
    id("com.diffplug.spotless")
}

spotless {
    kotlin {
        target("**/*.kt", "**/*.kts")
        targetExclude("**/convention/myplugins/build/**/*.*")
        ktlint().editorConfigOverride(
            mapOf(
                "android" to "true",
            ),
        )

        indentWithSpaces()
        endWithNewline()
    }
    format("kts") {
        target("**/*.kts")
        targetExclude("**/build/**/*.kts")
        // Look for the first line that doesn't have a block comment (assumed to be the license)
//        licenseHeaderFile(rootProject.file("spotless/copyright.kts"), "(^(?![\\/ ]\\*).*$)")
    }
    format("xml") {
        target("**/*.xml")
        targetExclude("**/build/**/*.xml")
        // Look for the first XML tag that isn't a comment (<!--) or the xml declaration (<?xml)
//        licenseHeaderFile(rootProject.file("spotless/copyright.xml"), "(<[^!?])")
    }

    // Don't add spotless as dependency for the Gradle's check task to facilitate separated codebase checks
    //isEnforceCheck = false
}