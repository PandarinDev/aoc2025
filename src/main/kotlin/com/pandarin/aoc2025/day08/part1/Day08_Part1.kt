package com.pandarin.aoc2025.day08.part1

import com.pandarin.aoc2025.util.Edge
import com.pandarin.aoc2025.util.FileUtils
import com.pandarin.aoc2025.util.Graph
import com.pandarin.aoc2025.util.pairs
import kotlin.math.pow
import kotlin.math.sqrt

private data class JunctionBox(val x: Int, val y: Int, val z: Int) {

    fun distance(other: JunctionBox): Double = sqrt(
        (x - other.x).toDouble().pow(2.0) +
        (y - other.y).toDouble().pow(2.0) +
        (z - other.z).toDouble().pow(2.0))

}

private fun parseBox(line: String): JunctionBox {
    val entries = line.split(',').map { it.toInt() }
    return JunctionBox(entries[0], entries[1], entries[2])
}

private const val ITERATIONS = 1000

fun main() {
    val boxes = FileUtils.readLines("day08.txt").map { parseBox(it) }
    val circuits = Graph(boxes)
    val processedPairs = mutableSetOf<Pair<JunctionBox, JunctionBox>>()
    for (i in 0..<ITERATIONS) {
        val closest = boxes.pairs()
            .filter { !processedPairs.contains(it) }
            .minBy { it.first.distance(it.second) }
        processedPairs.add(closest)
        circuits.edges.add(Edge(closest.first, closest.second))
    }
    val largestThreeCircuits = circuits.groups()
        .map { it.size }
        .sortedDescending()
        .take(3)
    println(largestThreeCircuits.reduce { acc, i -> acc * i })
}