package com.pandarin.aoc2025.day06.part1

import com.pandarin.aoc2025.util.FileUtils

fun main() {
    val lines = FileUtils.readLines("day06.txt")
    // Parse the numbers
    val separatorRegex = Regex("\\s+")
    val parseEntries = { line: String -> line.split(separatorRegex).filter { it.isNotEmpty() } }
    val numbers = Array(parseEntries(lines[0]).size) { LongArray(lines.size - 1) }
    for (y in 0..<numbers[0].size) {
        val entries = parseEntries(lines[y]).map { it.toLong() }
        for (x in 0..<numbers.size) {
            numbers[x][y] = entries[x]
        }
    }
    // Parse and execute operations
    var result = 0L
    val operations = parseEntries(lines[lines.size - 1])
    val getOperationApplier = { str: String -> when(str) {
        "+" -> { first: Long, second: Long -> first + second }
        "*" -> { first: Long, second: Long -> first * second }
        else -> throw IllegalArgumentException("Invalid operation '$str'")
    } }
    for ((x, operation) in operations.withIndex()) {
        val applier = getOperationApplier(operation)
        result += numbers[x].reduce(applier)
    }
    println(result)
}