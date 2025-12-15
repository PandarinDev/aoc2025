package com.pandarin.aoc2025.day06.part2

import com.pandarin.aoc2025.util.FileUtils
import kotlin.math.min

private fun parseColumn(lines: List<String>, startIdx: Int, endIdx: Int): List<String> = lines.map {
    val expectedChars = endIdx - startIdx + 1
    val result = it.substring(startIdx, min(endIdx + 1, it.length))
    // If there are not enough characters in the line pad the number
    result.padEnd(expectedChars, ' ')
}

private fun processColumn(column: List<String>): Long {
    val length = column[0].length
    val numbers = mutableListOf<Long>()
    for (i in 0..<length) {
        var numStr = ""
        for (entry in column.take(column.size - 1)) {
            if (entry[i] != ' ') {
                numStr += entry[i]
            }
        }
        numbers.add(numStr.toLong())
    }
    return when (val operator = column.last().trim()) {
        "+" -> numbers.sum()
        "*" -> numbers.reduce { first, second -> first * second }
        else -> throw IllegalArgumentException("Unknown operator '$operator'")
    }
}

fun main() {
    val lines = FileUtils.readLines("day06.txt")
    // Parse the numbers
    val columns = mutableListOf<List<String>>()
    val operators = lines[lines.size - 1]
    val isOperator = { c: Char -> c == '+' || c == '*' }
    // The index of the operators in the last row determines the indices of the numbers in the columns
    val findNextOperatorIdx = { idx: Int ->
        // Have to subtract 2 from the index since there is a space in front and the operator
        // is at the start of the new number so we want to exclude that also. Null just means
        // that we have reached the end of the line so return line length - 1
        operators.withIndex().drop(idx + 1).firstOrNull { isOperator(it.value) }?.index?.minus(2) ?: (operators.length - 1)
    }
    for ((idx, c) in operators.withIndex()) {
        if (isOperator(c)) {
            val startIdx = idx
            val endIdx = findNextOperatorIdx(idx)
            val column = parseColumn(lines, startIdx, endIdx)
            columns.add(column)
        }
    }
    val result = columns.sumOf { processColumn(it) }
    println(result)
}