package com.pandarin.aoc2025.day01.part1

import com.pandarin.aoc2025.util.FileUtils

private enum class Rotation(private val func: (Int, Int) -> Int) {
    LEFT({ a, b -> (a - b).mod(100) }),
    RIGHT({ a, b -> (a + b) % 100 });

    operator fun invoke(dial: Int, rotation: Int): Int = func(dial, rotation)

    companion object {

        fun fromEntry(entry: String) =
            if (entry.startsWith("L")) LEFT
            else RIGHT

    }

}

fun main() {
    var dial = 50
    var result = 0
    val entries = FileUtils.readLines("day01.txt")
    for (entry in entries) {
        val value = entry.substring(1).toInt()
        val rotation = Rotation.fromEntry(entry)
        dial = rotation(dial, value)
        if (dial == 0) {
            ++result
        }
    }
    println(result)
}