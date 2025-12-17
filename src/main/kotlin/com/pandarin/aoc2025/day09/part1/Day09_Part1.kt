package com.pandarin.aoc2025.day09.part1

import com.pandarin.aoc2025.util.FileUtils
import com.pandarin.aoc2025.util.pairs
import kotlin.math.abs

private data class Tile(val x: Long, val y: Long)

private fun parseTile(line: String): Tile = line.split(',')
    .map { it.toLong() }
    .let { Tile(it[0], it[1]) }

private fun area(first: Tile, second: Tile) = abs(first.x - second.x + 1) * abs(first.y - second.y + 1)

fun main() {
    val tiles = FileUtils.readLines("day09.txt").map { parseTile(it) }
    val maxTileArea = tiles.pairs().maxOf { area(it.first, it.second) }
    println(maxTileArea)
}