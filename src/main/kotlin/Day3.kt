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

        for (x in max(startCol - 1, 0)..min(endCol + 1, input[line].length - 1)) {
            if ((line != 0 && input[line - 1][x] != '.') ||
                (input.size - 1 != line && input[line + 1][x] != '.')
            ) {
                return false
            }
        }

        return !((startCol != 0 && input[line][startCol - 1] != '.') ||
                (endCol != input[line].length - 1 && input[line][endCol + 1] != '.'))
    }

    private fun numbersAround(lines: List<String>, i: Int, j: Int): Int {
        val foundNumber = ArrayList<Int>()
        foundNumber.addAll(findAbove(lines, i, j))
        foundNumber.addAll(findBelow(lines, i, j))
        foundNumber.addAll(findInLine(lines, i, j))

        return if (foundNumber.size == 2) {
            foundNumber[0] * foundNumber[1]
        } else {
            0
        }
    }

    private fun findAbove(lines: List<String>, i: Int, j: Int): ArrayList<Int> {
        val foundNumber = ArrayList<Int>()
        if (i == 0) {
            return foundNumber
        }
        if (lines[i - 1][j].isDigit()) {
            val cols = getNumberStartEnd(lines, i - 1, j)
            foundNumber.add(lines[i - 1].substring(cols.first, cols.second).toInt())
            return foundNumber
        }
        if (j != 0 && lines[i - 1][j - 1].isDigit() && !lines[i - 1][j].isDigit()) {
            val cols = getNumberStartEnd(lines, i - 1, j - 1)
            foundNumber.add(lines[i - 1].substring(cols.first, cols.second).toInt())
        }
        if (j != lines[i].length - 1 && lines[i - 1][j + 1].isDigit() && !lines[i - 1][j].isDigit()) {
            val cols = getNumberStartEnd(lines, i - 1, j + 1)
            foundNumber.add(lines[i - 1].substring(cols.first, cols.second).toInt())
        }
        return foundNumber
    }

    private fun findBelow(lines: List<String>, i: Int, j: Int): ArrayList<Int> {
        val foundNumber = ArrayList<Int>()
        if (i == lines.size - 1) {
            return foundNumber
        }
        if (lines[i + 1][j].isDigit()) {
            val cols = getNumberStartEnd(lines, i + 1, j)
            foundNumber.add(lines[i + 1].substring(cols.first, cols.second).toInt())
        }
        if (j != 0 && lines[i + 1][j - 1].isDigit() && !lines[i + 1][j].isDigit()) {
            val cols = getNumberStartEnd(lines, i + 1, j - 1)
            foundNumber.add(lines[i + 1].substring(cols.first, cols.second).toInt())
        }
        if (j != 0 && lines[i + 1][j + 1].isDigit() && !lines[i + 1][j].isDigit()) {
            val cols = getNumberStartEnd(lines, i + 1, j + 1)
            foundNumber.add(lines[i + 1].substring(cols.first, cols.second).toInt())
        }
        return foundNumber
    }

    private fun findInLine(lines: List<String>, i: Int, j: Int): ArrayList<Int> {
        val foundNumber = ArrayList<Int>()
        if (j != 0 && lines[i][j - 1].isDigit()) {
            val cols = getNumberStartEnd(lines, i, j - 1)
            foundNumber.add(lines[i].substring(cols.first, cols.second).toInt())
        }
        if (j != lines[i].length - 1 && lines[i][j + 1].isDigit()) {
            val cols = getNumberStartEnd(lines, i, j + 1)
            foundNumber.add(lines[i].substring(cols.first, cols.second).toInt())
        }
        return foundNumber
    }

    private fun getNumberStartEnd(lines: List<String>, i: Int, j: Int): Pair<Int, Int> {
        var startCol = j
        var endCol = j
        while (startCol != 0 && lines[i][startCol - 1].isDigit()) {
            startCol--
        }
        while (endCol != lines[i].length - 1 && lines[i][endCol + 1].isDigit()) {
            endCol++
        }
        return Pair(startCol, endCol + 1)
    }

    override fun fileName(): String = "3"

}