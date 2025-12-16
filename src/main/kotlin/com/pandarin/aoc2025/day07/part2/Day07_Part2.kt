package com.pandarin.aoc2025.day07.part2

import com.pandarin.aoc2025.util.FileUtils

private const val SPLITTER = '^'

fun main() {
    val lines = FileUtils.readLines("day07.txt")
    val startingPosition = lines[0].indexOf('S')
    // We are counting how many beams fall into each column
    var beams = mapOf(startingPosition to 1L)
    for (y in 1..<lines.size) {
        val currentLine = lines[y]
        val newBeams = mutableMapOf<Int, Long>()
        for ((beam, count) in beams) {
            // If we encounter a splitter the beam splits into two
            if (currentLine[beam] == SPLITTER) {
                newBeams.merge(beam - 1, count) { old, new -> old + new }
                newBeams.merge(beam + 1, count) { old, new -> old + new }
            }
            // Otherwise it continues downwards
            else newBeams.merge(beam, count) { old, new -> old + new }
        }
        beams = newBeams
    }
    // The solution is the sum of the last row
    println(beams.values.sum())
}