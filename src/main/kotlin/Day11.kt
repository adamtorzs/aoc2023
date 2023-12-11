import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day11 : Day {

    private val expFactor = 1_000_000L

    override fun solve1(input: String): Any {

        var expanded: List<String> = input.lines().flatMap {
            if (it.all { c -> c == '.' }) listOf(it, it) else listOf(it)
        }

        for (i in expanded.first().indices.reversed()) {
            if (expanded.all { it[i] == '.' }) {
                expanded = expanded.map { it.substring(0, i + 1) + it[i] + it.substring(i + 1) }
            }
        }

        var sum = 0L

        val galaxies = ArrayList<Pair<Int, Int>>()
        for (i in expanded.indices) {
            for (j in expanded[i].indices) {
                if (expanded[i][j] == '#') {
                    galaxies.add(i to j)
                }
            }
        }

        galaxies.forEachIndexed { i, galaxy ->
            if (i != galaxies.lastIndex) {
                sum += countDistances(galaxy, galaxies.subList(i + 1, galaxies.size), expanded)
            }
        }

        return sum
    }

    override fun solve2(input: String): Any {
        var expanded = input.lines().map { if (it.all { c -> c == '.' }) "M".repeat(it.length) else it }
        for (i in expanded.first().indices.reversed()) {
            if (expanded.all { it[i] == '.' || it[i] == 'M' }) {
                expanded = expanded.map { it.substring(0, i) + 'M' + it.substring(i + 1) }
            }
        }

        var sum: Long = 0
        val galaxies = expanded.flatMapIndexed { i, row ->
            row.mapIndexedNotNull { j, char ->
                if (char == '#') i to j else null
            }
        }

        galaxies.forEachIndexed { i, galaxy ->
            if (i != galaxies.lastIndex) {
                sum += countDistances(galaxy, galaxies.subList(i + 1, galaxies.size), expanded)
            }
        }

        return sum
    }

    private fun countDistances(galaxy: Pair<Int, Int>, subList: List<Pair<Int, Int>>, space: List<String>): Long {
        var sum: Long = 0

        for (secondGalaxy in subList) {
            val d = columnsBetween(galaxy.second, secondGalaxy.second, space) + linesBetween(
                galaxy.first,
                secondGalaxy.first,
                space
            )
            sum += d
        }

        return sum
    }

    private fun linesBetween(line1: Int, line2: Int, space: List<String>): Long {
        val jumps = space.subList(line1, line2).filter { it[0] == 'M' }.size

        return abs(line2 - line1) + (jumps * (expFactor - 1))
    }

    private fun columnsBetween(column1: Int, column2: Int, space: List<String>): Long {
        val jumps =
            space.first().substring(min(column1, column2), max(column1, column2)).filter { it == 'M' }.length

        return abs(column2 - column1) + (jumps * (expFactor - 1))
    }

    override fun fileName(): String = "11"

}