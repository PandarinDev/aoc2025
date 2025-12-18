package com.pandarin.aoc2025.day09.part2

import com.pandarin.aoc2025.util.FileUtils
import com.pandarin.aoc2025.util.pairs
import com.pandarin.aoc2025.util.zipWithNextWrapped
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private data class Tile(val x: Long, val y: Long)

private fun parseTile(line: String): Tile = line.split(',')
    .map { it.toLong() }
    .let { Tile(it[0], it[1]) }

private fun tilesBetween(first: Tile, second: Tile): List<Tile> {
    return if (first.x == second.x) {
        (min(first.y, second.y)..max(first.y, second.y)).map { Tile(first.x, it) }
    }
    else if (first.y == second.y) {
        (min(first.x, second.x)..max(first.x, second.x)).map { Tile(it, first.y) }
    }
    else throw IllegalArgumentException("Input tiles are not on the same row/column.")
}

private fun range(first: Long, second: Long) = min(first, second)..max(first, second)

private fun isValidRectangle(fencesByRow: Map<Long, List<Tile>>, first: Tile, second: Tile): Boolean {
    for (y in range(first.y, second.y)) {
        if (!fencesByRow.contains(y)) return false
        val rowFenceRanges = fencesByRow[y]!!
            .sortedBy { it.x }
            .zipWithNext()
        for (x in range(first.x, second.x)) {
            // If X does not fall into any of the fence ranges it falls outside the fence boundary
            if (rowFenceRanges.none { x >= it.first.x && x <= it.second.x }) {
                return false
            }
        }
    }
    return true
}

private fun area(first: Tile, second: Tile) = abs(first.x - second.x + 1) * abs(first.y - second.y + 1)

fun main() {
    val redTiles = FileUtils.readLines("day09.txt").map { parseTile(it) }
    val greenTiles = redTiles
        .zipWithNextWrapped()
        .flatMap { tilesBetween(it.first, it.second) }
        .toSet()
    val fencesByRow = greenTiles.groupBy { it.y }
    val largestRectangle = redTiles.pairs()
        .sortedByDescending { area(it.first, it.second) }
        .first { isValidRectangle(fencesByRow, it.first, it.second) }
    println(area(largestRectangle.first, largestRectangle.second))
}