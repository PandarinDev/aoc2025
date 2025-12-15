package com.pandarin.aoc2025.day07.part2

import com.pandarin.aoc2025.util.FileUtils

private const val SPLITTER = '^'
private const val LEFT = -1
private const val RIGHT = 1
private const val DOWN = 0

// A list of integers represent the path of the beam. Every entry in the path is a move down for the beam.
// -1 represent a move to the left, 0 represents straight down, +1 represent a move to the right.
// In the end we want to count the number of unique paths that we have.
typealias Path = List<Int>

fun processTree(lines: List<String>, path: Path, beam: Int): List<Path> {
    val currentLines = lines[path.size]
    if (currentLines[beam] == SPLITTER) {
        return listOf(path.plus(LEFT), path.plus(RIGHT))
    }
    return listOf(path.plus(DOWN))
}

fun main() {
    val lines = FileUtils.readLines("day07_test.txt")
    val startingPosition = lines[0].indexOf('S')
    val calculatePosition = { path: Path -> startingPosition + path.sum() }
    var paths = processTree(lines, emptyList(), startingPosition).toSet()
    while (paths.first().size != lines.size) {
        paths = paths.flatMap { processTree(lines, it, calculatePosition(it)) }.toSet()
    }
    println(paths.size)
}