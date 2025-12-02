package com.pandarin.aoc2025.day02.part2

import com.pandarin.aoc2025.util.FileUtils

private fun parseRange(entry: String): Pair<Long, Long> {
    val result = entry.split('-')
    return result[0].toLong() to result[1].toLong()
}

private fun isRepeating(entry: String): Boolean {
    for (i in 1..<entry.length) {
        val prefix = entry.take(i)
        val remainder = entry.substring(i)
        // If remainder is shorter than prefix we can safely return false
        if (remainder.length < prefix.length) {
            return false
        }
        // Skip if remainder is not divisible by prefix
        if (remainder.length % prefix.length != 0) {
            continue
        }
        var match = true
        for (j in 0..<remainder.length step prefix.length) {
            if (remainder.substring(j, j + prefix.length) != prefix) {
                match = false
                break
            }
        }
        if (match) {
            return true
        }
    }
    return false
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
            if (isRepeating(value.toString())) {
                result += value
            }
        }
    }
    println(result)
}