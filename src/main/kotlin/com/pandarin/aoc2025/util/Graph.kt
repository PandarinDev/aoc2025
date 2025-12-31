package com.pandarin.aoc2025.util

import java.util.LinkedList

data class Edge<T>(val first: T, val second: T)

class Graph<T>(
    val vertices: MutableSet<T> = mutableSetOf(),
    val edges: MutableSet<Edge<T>> = mutableSetOf()) {

    fun edgesWith(vertex: T) = edges.filter { it.first == vertex || it.second == vertex }

    fun edgesFrom(vertex: T) = edges.filter { it.first == vertex }

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

    fun pathsBetween(from: T, to: T): List<List<T>> {
        data class Path(val current: T, val path: List<T>)
        val paths = mutableListOf<List<T>>()
        val toWalk = LinkedList<Path>()
        toWalk.add(Path(from, emptyList()))
        while (toWalk.isNotEmpty()) {
            val vertexWithPath = toWalk.pop()
            // If we arrived to our destination save the path and continue
            if (vertexWithPath.current == to) {
                paths.add(vertexWithPath.path)
                continue
            }
            // Otherwise add all outgoing edges that we need to visit
            val edges = edgesFrom(vertexWithPath.current)
            for (edge in edges) {
                val path = vertexWithPath.path.toMutableList()
                path.add(edge.second)
                toWalk.add(Path(edge.second, path))
            }
        }
        return paths
    }

}