package com.pandarin.aoc2025.day07.part1

import com.pandarin.aoc2025.util.FileUtils

private const val SPLITTER = '^'

fun main() {
    val lines = FileUtils.readLines("day07.txt")
    val startingPosition = lines[0].indexOf('S')
    var beams = setOf(startingPosition)
    var splits = 0
    for (y in 1..<lines.size) {
        val currentLine = lines[y]
        val newBeams = mutableSetOf<Int>()
        for (beam in beams) {
            // If we encounter a splitter the beam splits into two
            if (currentLine[beam] == SPLITTER) {
                newBeams.add(beam - 1)
                newBeams.add(beam + 1)
                ++splits
            }
            // Otherwise it continues downwards
            else newBeams.add(beam)
        }
        beams = newBeams
    }
    println(splits)
}