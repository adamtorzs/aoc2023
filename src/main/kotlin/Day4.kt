import kotlin.math.pow

class Day4 : Day {
    override fun solve1(input: String): Any {
        val lines = input.lines()
        var sum = 0

        for (line in lines) {
            val wins = getWinsForLine(line).second
            if (wins != 0) {
                sum += 2.0.pow(wins - 1).toInt()
            }
        }
        return sum
    }

    override fun solve2(input: String): Any {
        val lines = input.lines()
        val countMap = ArrayList<Int>()
        countMap.add(0)
        for (i in lines.indices) {
            countMap.add(1)
        }
        for (line in lines) {
            val wins = getWinsForLine(line)
            if (wins.second != 0) {
                for (i in 1..wins.second) {
                    countMap[wins.first + i] += countMap[wins.first]
                }
            }
        }
        return countMap.sum()
    }

    private fun getWinsForLine(line: String): Pair<Int, Int> {
        val split = line.split(":")
        val id = split[0].split(" ").last().toInt()
        val numbers = split[1].split("|")
        val winning = numbers[0].toIntList()
        val own = numbers[1].trim().toIntList()
        return Pair(id, winning.count { own.contains(it) })
    }

    private fun String.toIntList(delimiter: String = " "): List<Int> =
        this.trim().split(delimiter).filter { it.isNotEmpty() }.map { it.toInt() }

    override fun fileName(): String = "4"
}