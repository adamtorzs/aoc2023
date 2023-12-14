import kotlin.math.min

class Day13 : Day {
    override fun solve1(input: String): Any {
        val cases = input.split("\r\n\r\n")
        return solve(cases, 0)
    }

    override fun solve2(input: String): Any {
        val cases = input.split("\r\n\r\n")
        return solve(cases, 1)
    }

    private fun solve(input: List<String>, targetDiff: Int): Int {
        var cols = 0
        var lines = 0
        for (case in input) {
            cols += columnsMirror(case.lines().filter { it.isNotEmpty() }, targetDiff)
            lines += linesMirror(case.lines().filter { it.isNotEmpty() }, targetDiff)
        }
        return lines * 100 + cols
    }

    private fun linesMirror(input: List<String>, targetDiff: Int): Int {
        for (currLine in 0..<input.lastIndex) {
            var diff = 0
            val distanceFromEdge = min(currLine, input.lastIndex - currLine - 1)
            for (i in 0..distanceFromEdge) {
                diff += input[currLine - i].zip(input[currLine + 1 + i]).count { (char1, char2) -> char1 != char2 }
            }
            if (diff == targetDiff) {
                return currLine + 1
            }
        }
        return 0
    }

    private fun columnsMirror(input: List<String>, targetDiff: Int): Int {
        for (currCol in 0..<input.first().length - 1) {
            var diff = 0
            val distanceFromEdge = min(currCol, input.first().length - 1 - currCol - 1)
            for (i in 0..distanceFromEdge) {
                diff += input.count { it[currCol - i] != it[currCol + i + 1] }
            }
            if (diff == targetDiff) {
                return currCol + 1
            }
        }
        return 0
    }

    override fun fileName(): String = "13"
}