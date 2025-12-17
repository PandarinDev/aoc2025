package com.pandarin.aoc2025.util

data class Edge<T>(val first: T, val second: T)

class Graph<T>(
    val vertices: MutableList<T> = mutableListOf(),
    val edges: MutableSet<Edge<T>> = mutableSetOf()) {

    constructor(vertices: List<T>) : this(vertices.toMutableList(), mutableSetOf()) {}

    // TODO: This implementation is heavily suboptimal which causes day 8 to run pretty slow. Optimize.
    fun groups(): List<List<T>> {
        val result = vertices.map { mutableListOf(it) }.toMutableList()
        for (edge in edges) {
            val alreadyGrouped = result.any { it.contains(edge.first) && it.contains(edge.second) }
            if (!alreadyGrouped) {
                val firstGroup = result.first { it.contains(edge.first) }
                val secondGroup = result.first { it.contains(edge.second) }
                firstGroup.addAll(secondGroup)
                result.remove(secondGroup)
            }
        }
        return result
    }

}