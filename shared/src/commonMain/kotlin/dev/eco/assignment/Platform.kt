package dev.eco.assignment

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform