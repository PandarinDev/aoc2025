package com.pandarin.aoc2025.day01.part2

import com.pandarin.aoc2025.util.FileUtils

private data class RotateResult(
    val wrapped: Int,
    val value: Int
)

private enum class Rotation(private val func: (Int, Int) -> RotateResult) {
    LEFT({ a, b ->
        /*
         * The dial being at 0 is a special case where we should not count the initial wrap-around.
         * Also care should be taken not to use % for the subtractions as that would yield the remainder,
         * not the true modulo value (e.g. (50 - 80) % 100 would yield -30, not 70).
         */
        if (a == 0) RotateResult(b / 100, (a - b).mod(100))
        else if (a > b) RotateResult(0, a - b)
        else RotateResult(((a - b) / -100) + 1, (a - b).mod(100))
    }),
    RIGHT({ a, b -> RotateResult((a + b) / 100, (a + b) % 100) });

    operator fun invoke(dial: Int, rotation: Int) = func(dial, rotation)

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
        val rotate = Rotation.fromEntry(entry)
        val rotateResult = rotate(dial, value)
        result += rotateResult.wrapped
        dial = rotateResult.value
    }
    println(result)
}