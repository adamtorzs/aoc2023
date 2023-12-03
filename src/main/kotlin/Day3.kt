import kotlin.math.max
import kotlin.math.min

class Day3 : Day {
    override fun solve1(input: String): Any {
        val lines = input.lines()
        val y = lines.size
        val x = lines[0].length
        var cx = 0
        var cy = 0
        var sum = 0

        while (cy < y) {
            if (lines[cy][cx].isDigit()) {
                val startCol = cx
                while (cx != x && lines[cy][cx].isDigit()) {
                    cx++
                }
                val endCol = cx - 1
                if (!surroundedByPeriods(lines, cy, startCol, endCol)) {
                    sum += lines[cy].substring(startCol, endCol + 1).toInt()
                }
            } else {
                cx++
            }
            if (cx == x) {
                cx = 0
                cy++
            }
        }

        return sum


    }

    override fun solve2(input: String): Any {
        val lines = input.lines()

        var sum = 0
        for (i in lines.indices) {
            for (j in 0..<lines[i].length) {
                if (lines[i][j] == '*') {
                    sum += numbersAround(lines, i, j)
                }
            }
        }
        return sum
    }

    private fun surroundedByPeriods(input: List<String>, line: Int, startCol: Int, endCol: Int): Boolean {

        for (x in max(startCol - 1, 0)..min(endCol + 1, input[line].lastIndex)) {
            if ((line != 0 && input[line - 1][x] != '.') ||
                (input.size - 1 != line && input[line + 1][x] != '.')
            ) {
                return false
            }
        }

        return !((startCol != 0 && input[line][startCol - 1] != '.') ||
                (endCol != input[line].lastIndex && input[line][endCol + 1] != '.'))
    }

    private fun numbersAround(lines: List<String>, i: Int, j: Int): Int {
        val foundNumber = ArrayList<Int>()
        if (i != 0) {
            foundNumber.addAll(findNumsInLine(lines, i - 1, j))
        }
        if (i != lines.lastIndex) {
            foundNumber.addAll(findNumsInLine(lines, i + 1, j))
        }
        foundNumber.addAll(findNumsInLine(lines, i, j))

        return if (foundNumber.size == 2) {
            foundNumber[0] * foundNumber[1]
        } else {
            0
        }
    }

    private fun findNumsInLine(lines: List<String>, i: Int, j: Int): ArrayList<Int> {
        val foundNumber = ArrayList<Int>()
        if (lines[i][j].isDigit()) {
            val cols = getNumberStartEnd(lines, i, j)
            foundNumber.add(lines[i].substring(cols.first, cols.second).toInt())
        } else {
            if (j != 0 && lines[i][j - 1].isDigit() && !lines[i][j].isDigit()) {
                val cols = getNumberStartEnd(lines, i, j - 1)
                foundNumber.add(lines[i].substring(cols.first, cols.second).toInt())
            }
            if (j != lines[i].lastIndex && lines[i][j + 1].isDigit() && !lines[i][j].isDigit()) {
                val cols = getNumberStartEnd(lines, i, j + 1)
                foundNumber.add(lines[i].substring(cols.first, cols.second).toInt())
            }
        }
        return foundNumber
    }

    private fun getNumberStartEnd(lines: List<String>, i: Int, j: Int): Pair<Int, Int> {
        var startCol = j
        var endCol = j
        while (startCol != 0 && lines[i][startCol - 1].isDigit()) {
            startCol--
        }
        while (endCol != lines[i].lastIndex && lines[i][endCol + 1].isDigit()) {
            endCol++
        }
        return Pair(startCol, endCol + 1)
    }

    override fun fileName(): String = "3"

}