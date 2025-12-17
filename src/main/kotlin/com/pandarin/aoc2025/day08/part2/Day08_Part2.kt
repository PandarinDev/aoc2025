package com.pandarin.aoc2025.day08.part2

import com.pandarin.aoc2025.util.Edge
import com.pandarin.aoc2025.util.FileUtils
import com.pandarin.aoc2025.util.Graph
import com.pandarin.aoc2025.util.pairs
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.system.exitProcess

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

fun main() {
    val boxes = FileUtils.readLines("day08.txt").map { parseBox(it) }
    val circuits = Graph(boxes)
    val processedPairs = mutableSetOf<Pair<JunctionBox, JunctionBox>>()
    while (true) {
        val groups = circuits.groups()
        val closest = boxes.pairs()
            .filter { !processedPairs.contains(it) }
            .minBy { it.first.distance(it.second) }
        processedPairs.add(closest)
        val differentCircuits = groups.none { it.contains(closest.first) && it.contains(closest.second) }
        if (differentCircuits) {
            circuits.edges.add(Edge(closest.first, closest.second))
            // No need to recompute the groups - if the size of the previous groups is 2 and we just connected
            // junction boxes from the two different circuits, it is guaranteed that the network is connected.
            if (groups.size == 2) {
                println(closest.first.x * closest.second.x)
                exitProcess(0)
            }
        }
    }
}