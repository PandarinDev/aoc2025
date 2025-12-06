package com.pandarin.aoc2025.util

class FileUtils {

    companion object {

        fun readString(filePath: String) =
            FileUtils::class.java.getResourceAsStream("/$filePath")!!.bufferedReader().use { it.readText() }

        fun readLines(filePath: String, keepEmptyLines: Boolean = false): List<String> {
            val result = readString(filePath).lines()
            if (keepEmptyLines) {
                return result
            }
            return result.filter { it.isNotEmpty() }
        }

    }

}