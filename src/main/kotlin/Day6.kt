import kotlin.math.*

class Day6 : Day{
    override fun solve1(input: String): Any {
        val times = input.lines().first().split(" ").filter { it.isNotBlank() }.subtract(listOf("Time:")).map { it.toLong() }
        val distances = input.lines().last().split(" ").filter { it.isNotBlank() }.subtract(listOf("Distance:")).map { it.toLong() }
        return times.zip(distances).map {
            countRecords(it.first, it.second)
        }.reduce {acc, el -> acc * el}
    }

    override fun solve2(input: String): Any {
        val time = input.lines().first().filter { it != ' ' }.split(":")[1].toLong()
        val distance = input.lines().last().filter { it != ' ' }.split(":")[1].toLong()
        return countRecords(time, distance)
    }

    private fun countRecords(time : Long, distance: Long) : Long {
        val first = (-time+sqrt((time*time - 4 * 1 * distance).toDouble()) )/(-2) + 0.0001
        val second = (-time-sqrt((time*time - 4 * 1 * distance).toDouble()) )/(-2) - 0.0001
        val start = ceil(min(first, second)).toLong()
        val end = floor(max(first, second)).toLong()
        return end - start + 1
    }

    override fun fileName(): String = "6"
}