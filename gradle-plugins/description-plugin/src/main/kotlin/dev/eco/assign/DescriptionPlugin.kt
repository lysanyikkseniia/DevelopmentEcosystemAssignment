package dev.eco.assign

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class DescriptionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.task("describeApp") {
            doLast {
                val sourceFiles = countSourceFiles(project)
                val classFiles = countClassFiles(project)
                val gitInfo = getGitInfo(project)

                println("Application Description:")
                println("Number of source files: $sourceFiles")
                println("Number of class files: $classFiles")
                println("Latest commit: ${gitInfo.first}")
                println("Author of latest commit: ${gitInfo.second}")
            }
        }
    }

    private fun countSourceFiles(project: Project): Int {
        val srcDirs = listOf("src/commonMain/kotlin", "src/androidMain/kotlin", "src/desktopMain/kotlin")
        return srcDirs.sumOf { dir ->
            println(project.file(dir).absolutePath)
            val sourceDir = project.file(dir)
            if (sourceDir.exists()) {
                sourceDir.walk().filter { it.isFile && it.extension == "kt" }.count()
            } else 0
        }
    }

    private fun countClassFiles(project: Project): Int {
        val buildDir = project.buildDir
        return buildDir.walk().filter { it.isFile && it.extension == "class" }.count()
    }

    private fun getGitInfo(project: Project): Pair<String, String> {
        val latestCommit = "git log -1 --pretty=%H".executeCommand(project.rootDir)
        val commitAuthor = "git log -1 --pretty=%an".executeCommand(project.rootDir)
        return Pair(latestCommit.trim(), commitAuthor.trim())
    }

    private fun String.executeCommand(workingDir: File): String {
        return ProcessBuilder(*this.split(" ").toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()
            .inputStream
            .bufferedReader()
            .readText()
    }
}
