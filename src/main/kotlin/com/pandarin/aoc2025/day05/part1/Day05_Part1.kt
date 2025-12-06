package com.pandarin.aoc2025.day05.part1

import com.pandarin.aoc2025.util.FileUtils

fun main() {
    val input = FileUtils.readLines("day05.txt", keepEmptyLines = true)
    val separatorIndex = input.indexOf("")
    // Parse ranges first
    val freshRanges = mutableListOf<Pair<Long, Long>>()
    for (i in 0..<separatorIndex) {
        val entry = input[i].split('-')
        freshRanges.add(entry[0].toLong() to entry[1].toLong())
    }
    // Count the number of fresh IDs
    var result = 0
    for (i in (separatorIndex + 1)..<input.size) {
        val entry = input[i].toLong()
        val isFresh = freshRanges.any { entry in it.first..it.second }
        if (isFresh) {
            ++result
        }
    }
    println(result)
}