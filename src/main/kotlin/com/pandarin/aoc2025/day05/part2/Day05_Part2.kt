package com.pandarin.aoc2025.day05.part2

import com.pandarin.aoc2025.util.FileUtils
import com.pandarin.aoc2025.util.pairs
import kotlin.math.max
import kotlin.math.min

private typealias Range = Pair<Long, Long>

private fun rangeLength(range: Range): Long = range.second - range.first + 1

private fun isOverlapping(first: Range, second: Range) =
        (first.first >= second.first && first.first <= second.second) || // First one starts inside second one
        (second.first >= first.first && second.first <= first.second) // Second one starts inside the first one

private fun mergeRanges(first: Range, second: Range) = min(first.first, second.first) to max(first.second, second.second)

private fun resolveOverlaps(ranges: List<Range>): List<Range> {
    val currentRanges = ranges.toMutableList()
    val getOverlapping = { currentRanges.pairs().firstOrNull { isOverlapping(it.first, it.second) } }
    var overlapping = getOverlapping()
    while (overlapping != null) {
        currentRanges.remove(overlapping.first)
        currentRanges.remove(overlapping.second)
        val merged = mergeRanges(overlapping.first, overlapping.second)
        currentRanges.add(merged)
        // Find the next overlapping range
        overlapping = getOverlapping()
    }
    return currentRanges
}

fun main() {
    val input = FileUtils.readLines("day05.txt", keepEmptyLines = true)
    val separatorIndex = input.indexOf("")
    // Parse ranges first
    val freshRanges = mutableListOf<Pair<Long, Long>>()
    for (i in 0..<separatorIndex) {
        val entry = input[i].split('-')
        freshRanges.add(entry[0].toLong() to entry[1].toLong())
    }
    // Resolve overlaps and sum the range lengths
    val resolvedRanges = resolveOverlaps(freshRanges)
    val result = resolvedRanges.sumOf { rangeLength(it) }
    println(result)
}