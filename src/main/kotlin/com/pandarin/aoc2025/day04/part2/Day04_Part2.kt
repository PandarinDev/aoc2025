package com.pandarin.aoc2025.day04.part2

import com.pandarin.aoc2025.util.FileUtils

private const val THRESHOLD = 4

private fun getNeighborCoordinates(x: Int, y: Int): List<Pair<Int, Int>> {
    return listOf(
        x + 1 to y,
        x - 1 to y,
        x to y + 1,
        x to y - 1,
        x + 1 to y + 1,
        x + 1 to y - 1,
        x - 1 to y + 1,
        x - 1 to y - 1
    )
}

private fun getRollValue(rolls: Array<BooleanArray>, x: Int, y: Int): Int {
    // Return 0 on out of bounds
    if (x < 0 || x >= rolls[0].size || y < 0 || y >= rolls.size) {
        return 0
    }
    // Otherwise return 1/0 based on whether there is a roll
    return if (rolls[x][y]) 1 else 0
}

fun findAccessibleRolls(rolls: Array<BooleanArray>): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    for (y in 0..<rolls.size) {
        for (x in 0..<rolls[0].size) {
            if (!rolls[x][y]) continue
            val neighborRolls = getNeighborCoordinates(x, y)
                .sumOf { getRollValue(rolls, it.first, it.second) }
            if (neighborRolls < THRESHOLD) {
                result.add(x to y)
            }
        }
    }
    return result
}

fun main() {
    // Parse the rolls into a 2D array
    val lines = FileUtils.readLines("day04.txt")
    val rolls = Array(lines[0].length) { BooleanArray(lines.size) }
    for ((y, line) in lines.withIndex()) {
        for ((x, c) in line.withIndex()) {
            rolls[x][y] = (c == '@')
        }
    }
    // Remove accessible rolls until we can no longer remove any
    var result = 0
    var accessibleRolls: List<Pair<Int, Int>>
    do {
        accessibleRolls = findAccessibleRolls(rolls)
        result += accessibleRolls.size
        for ((x, y) in accessibleRolls) {
            rolls[x][y] = false
        }
    } while (!accessibleRolls.isEmpty())
    println(result)
}