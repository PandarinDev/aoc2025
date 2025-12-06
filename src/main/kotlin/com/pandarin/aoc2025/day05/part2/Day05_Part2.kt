package com.pandarin.aoc2025.day05.part2

import com.pandarin.aoc2025.util.FileUtils

fun rangeLength(range: Pair<Long, Long>): Long = range.second - range.first + 1

fun main() {
    val input = FileUtils.readLines("day05.txt", keepEmptyLines = true)
    val separatorIndex = input.indexOf("")
    // Parse ranges first
    val freshRanges = mutableListOf<Pair<Long, Long>>()
    for (i in 0..<separatorIndex) {
        val entry = input[i].split('-')
        freshRanges.add(entry[0].toLong() to entry[1].toLong())
    }
    // Resolve overlaps
    val resolvedRanges = mutableListOf<Pair<Long, Long>>()
    for (i in 0..<freshRanges.size) {
        val range = freshRanges[i]
        var adjustedRange: Pair<Long, Long>? = range
        for (j in 0..<freshRanges.size) {
            if (i == j) continue
            val other = freshRanges[j]
            if (range.first >= other.first && range.first <= other.second) {
                // If the range is entirely contained by another, just skip it
                if (range.second <= other.second) {
                    adjustedRange = null
                    break
                }
                // If it is partially contained adjust it
                adjustedRange = (other.second + 1) to range.second
            }
        }
        if (adjustedRange != null) {
            resolvedRanges.add(adjustedRange)
        }
    }
    resolvedRanges.forEach { println("[${it.first}, ${it.second}]") }
    // Sum range lengths
    val result = resolvedRanges.sumOf { rangeLength(it) }
    println(result)
}