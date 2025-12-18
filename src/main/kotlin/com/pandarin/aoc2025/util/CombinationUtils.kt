package com.pandarin.aoc2025.util

fun <T> List<T>.pairs() = flatMapIndexed { i, first -> drop(i + 1).map { second -> first to second } }

fun <T> List<T>.zipWithNextWrapped() = mapIndexed { index, elem ->
    if (index != lastIndex) elem to this[index + 1]
    else elem to this[0]
}
