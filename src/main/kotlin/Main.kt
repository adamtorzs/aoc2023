import java.io.File

fun main() {
    val day: Day = Day11()
    val input : String = File(day.fileName() + ".txt").readText()

    println(day.solve1(input))
    println(day.solve2(input))
}