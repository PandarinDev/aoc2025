package com.pandarin.aoc2025.util

fun <T> List<T>.pairs() = flatMapIndexed { i, first -> drop(i + 1).map { second -> first to second } }