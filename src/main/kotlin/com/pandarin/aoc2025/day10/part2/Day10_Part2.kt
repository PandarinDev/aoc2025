package com.pandarin.aoc2025.day10.part2

import com.pandarin.aoc2025.util.Edge
import com.pandarin.aoc2025.util.FileUtils
import com.pandarin.aoc2025.util.Graph
import java.util.*
import kotlin.math.abs
import kotlin.math.pow

private data class MachineConfiguration(
    val goal: List<Int>,
    val buttons: List<List<Int>>
)

private fun parseMachineConfiguration(line: String): MachineConfiguration {
    val bracketIndex = line.indexOf(']')
    val parse = { str: String -> str.substring(1, str.lastIndex).split(',').map { it.toInt() } }
    val parts = line.substring(bracketIndex + 2).split(' ')
    val goal = parse(parts.first { it.startsWith('{') })
    val buttons = parts.filter { it.startsWith('(') }
        .map { buttonStr -> parse(buttonStr) }
    return MachineConfiguration(goal, buttons)
}

private fun applyButton(node: List<Int>, button: List<Int>): List<Int> {
    val result = node.toMutableList()
    for (idx in button) {
        result[idx]++
    }
    return result
}

// TODO: Graph repesentation is way too slow for part 2. Need to represent as a system of linear
// equations and use Gauss elimination in order to solve configurations in a reasonable time window.
private fun solveConfiguration(config: MachineConfiguration): Long {
    // We need to build a graph where the nodes are the light switch states and the edges are
    // the button presses. All nodes must be connected in the system, then we just do a BFS
    // and the first solution that we find is the least number of presses required to solve.
    // Note that we will lazily build our graph.
    val graph = Graph<List<Int>>()
    val startingNode = List(config.goal.size) { _ -> 0 }
    graph.vertices.add(startingNode)
    val queue = LinkedList<List<Int>>()
    val nodeDistanceLookup = mutableMapOf(startingNode to 0L)
    val prepareNode = { node: List<Int>, distance: Long ->
        for (button in config.buttons) {
            val afterButtonNode = applyButton(node, button)
            if (graph.vertices.add(afterButtonNode)) {
                graph.edges.add(Edge(node, afterButtonNode))
                queue.add(afterButtonNode)
                nodeDistanceLookup[afterButtonNode] = distance
            }
        }
    }
    queue.add(startingNode)
    while (true) {
        val node = queue.poll()
        val distance = nodeDistanceLookup[node]!!
        if (node == config.goal) return distance
        if (graph.edgesFrom(node).isEmpty()) {
            prepareNode(node, distance + 1L)
        }
    }
}

fun main() {
    val machineConfigurations = FileUtils.readLines("day10.txt").map { parseMachineConfiguration(it) }
    val result = machineConfigurations.sumOf { solveConfiguration(it) }
    println(result)
}