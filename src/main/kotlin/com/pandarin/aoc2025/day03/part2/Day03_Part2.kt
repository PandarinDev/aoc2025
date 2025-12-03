package com.pandarin.aoc2025.day03.part2

import com.pandarin.aoc2025.util.FileUtils
import kotlin.math.pow

private const val ITERATIONS = 12

private fun findMaxDigitIndex(entry: String, startIndex: Int, endIndex: Int): Int {
    for (digitInt in 9 downTo 0) {
        val digit = digitInt.digitToChar()
        val digitIndex = entry.indexOf(digit, startIndex)
        if (digitIndex in startIndex..<endIndex) {
            return digitIndex
        }
    }
    return -1
}

private fun findMaxJoltage(entry: String): Long {
    var startIndex = 0
    return (0..<ITERATIONS).sumOf {
        val charactersToReserve = ITERATIONS - it - 1
        val endIndex = entry.length - charactersToReserve
        val index = findMaxDigitIndex(entry, startIndex, endIndex)
        startIndex = index + 1
        val maxDigit = entry[index].digitToInt()
        maxDigit * (10.0.pow(ITERATIONS - it - 1)).toLong()
    }
}

fun main() {
    val entries = FileUtils.readLines("day03.txt")
    val result = entries.sumOf { findMaxJoltage(it) }
    println(result)
}