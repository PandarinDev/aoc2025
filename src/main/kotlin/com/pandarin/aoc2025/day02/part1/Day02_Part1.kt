package com.pandarin.aoc2025.day02.part1

import com.pandarin.aoc2025.util.FileUtils

private fun parseRange(entry: String): Pair<Long, Long> {
    val result = entry.split('-')
    return result[0].toLong() to result[1].toLong()
}

fun main() {
    val entries = FileUtils.readLines("day02.txt")
    val ranges = entries
        .flatMap { it.split(',') }
        .filter { it.isNotEmpty() }
        .map { parseRange(it) }
    var result = 0L
    for (range in ranges) {
        for (value in range.first..range.second) {
            val valueStr = value.toString()
            // Values that are not of even length cannot be repeating
            if (valueStr.length % 2 == 1) {
                continue
            }
            val halfLength = valueStr.length / 2
            val firstHalf = valueStr.take(halfLength)
            val secondHalf = valueStr.substring(halfLength)
            if (firstHalf == secondHalf) {
                result += value
            }
        }
    }
    println(result)
}