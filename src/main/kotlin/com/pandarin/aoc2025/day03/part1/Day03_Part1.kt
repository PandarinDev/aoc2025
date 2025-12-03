package com.pandarin.aoc2025.day03.part1

import com.pandarin.aoc2025.util.FileUtils

fun findMaxJoltage(entry: String): Int {
    for (first in 9 downTo 0) {
        val firstChar = first.digitToChar()
        val firstCharIdx = entry.indexOf(firstChar)
        // Skip this first digit if it is not present
        if (firstCharIdx == -1) {
            continue
        }
        for (second in 9 downTo 0) {
            val secondChar = second.digitToChar()
            val secondCharIdx = entry.indexOf(secondChar, firstCharIdx + 1)
            if (secondCharIdx != -1) {
                return first * 10 + second
            }
        }
    }
    return 0
}

fun main() {
    val entries = FileUtils.readLines("day03.txt")
    val result = entries.sumOf { findMaxJoltage(it) }
    println(result)
}