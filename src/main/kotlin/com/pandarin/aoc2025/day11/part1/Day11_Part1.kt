package com.pandarin.aoc2025.day11.part1

import com.pandarin.aoc2025.util.Edge
import com.pandarin.aoc2025.util.FileUtils
import com.pandarin.aoc2025.util.Graph

private data class Device(
    val name: String,
    val connections: List<String>
)

private fun parseEntry(entry: String): Device {
    val parts = entry.split(':')
    val name = parts[0]
    val connections = parts[1].split(' ').filter { it.isNotEmpty() }
    return Device(name, connections)
}

private const val START_NODE = "you"
private const val END_NODE = "out"

fun main() {
    val devices = FileUtils.readLines("day11.txt").map { parseEntry(it) }
    val graph = Graph<String>()
    // Add vertices and edges
    for (device in devices) {
        graph.vertices.add(device.name)
        for (connected in device.connections) {
            graph.edges.add(Edge(device.name, connected))
        }
    }
    // Count the number of unique paths between "you" and "out"
    println(graph.pathsBetween(START_NODE, END_NODE).size)
}