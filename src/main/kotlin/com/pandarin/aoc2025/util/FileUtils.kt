package com.pandarin.aoc2025.util

import java.io.File

class FileUtils {

    companion object {

        fun readString(filePath: String) =
            FileUtils::class.java.getResourceAsStream("/$filePath")!!.bufferedReader().use { it.readText() }

        fun readLines(filePath: String) =
            readString(filePath).lines().filter { it.isNotEmpty() }

    }

}