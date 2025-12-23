package com.pandarin.aoc2025.day10.part1

import com.pandarin.aoc2025.util.Edge
import com.pandarin.aoc2025.util.FileUtils
import com.pandarin.aoc2025.util.Graph
import java.util.*

data class MachineConfiguration(
    val goal: List<Boolean>,
    val buttons: List<List<Int>>
)

private fun parseMachineConfiguration(line: String): MachineConfiguration {
    val bracketIndex = line.indexOf(']')
    val goal = line.substring(1, bracketIndex).map { it == '#' }
    val buttons = line.substring(bracketIndex + 2).split(' ')
        .filter { it.startsWith('(') }
        .map { buttonStr ->
            buttonStr.substring(1, buttonStr.lastIndex).split(',').map { it.toInt() }
        }
    return MachineConfiguration(goal, buttons)
}

private fun applyButton(node: List<Boolean>, button: List<Int>): List<Boolean> {
    val result = node.toMutableList()
    for (idx in button) {
        result[idx] = !node[idx]
    }
    return result
}

private fun solveConfiguration(config: MachineConfiguration): Long {
    // We need to build a graph where the nodes are the light switch states and the edges are
    // the button presses. All nodes must be connected in the system, then we just do a BFS
    // and the first solution that we find is the least number of presses required to solve.
    // Note that we will lazily build our graph.
    val graph = Graph<List<Boolean>>()
    val startingNode = List(config.goal.size) { _ -> false }
    graph.vertices.add(startingNode)
    val queue = LinkedList<List<Boolean>>()
    val nodeDistanceLookup = mutableMapOf(startingNode to 0L)
    val prepareNode = { node: List<Boolean>, distance: Long ->
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
        val node = queue.pop()
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