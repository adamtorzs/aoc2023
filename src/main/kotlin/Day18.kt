import kotlin.math.absoluteValue

class Day18 : Day {

    override fun solve1(input: String): Any =
        solve(input, 1)

    override fun solve2(input: String): Any =
        solve(input, 2)


    private fun solve(input: String, task: Int): Long {
        val digPoints = mutableListOf(0L to 0L)
        val outerPoints: Long = input.lines().sumOf { calculateAndAddToList(it, task, digPoints) }
        return ((shoelaceArea(digPoints) + 1 - outerPoints / 2) + outerPoints)
    }

    private fun getDirFromLine(line: String, task: Int): Direction {
        return when (task) {
            1 -> line.split(" ")[0][0].toDirection()
            2 -> line.split("#")[1][5].toDirection()
            else -> Direction.UP
        }
    }

    private fun getStepsFromLine(line: String, task: Int): Long {
        return when (task) {
            1 -> line.split(" ")[1].toLong()
            2 -> line.split("#")[1].substring(0..4).toLong(16)
            else -> 0
        }
    }

    private fun calculateAndAddToList(line: String, task: Int, list: MutableList<Point<Long>>): Long {
        val steps = getStepsFromLine(line, task)
        list.add(
            getDirFromLine(line, task).nextInDirection(
                list.last().first,
                list.last().second,
                getStepsFromLine(line, task)
            )
        )
        return steps
    }

    private fun shoelaceArea(v: List<Point<Long>>): Long =
        v.indices.sumOf { v[it].second * v[(it + 1) % v.size].first - v[(it + 1) % v.size].second * v[it].first }.absoluteValue / 2

    override fun fileName(): String = "18"

    private fun Char.toDirection(): Direction = when (this) {
        'L', '2' -> Direction.LEFT
        'R', '0' -> Direction.RIGHT
        'U', '3' -> Direction.UP
        'D', '1' -> Direction.DOWN
        else -> Direction.UP
    }
}

typealias Point<T> = Pair<T, T>