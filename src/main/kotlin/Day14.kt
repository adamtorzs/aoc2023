class Day14 : Day {
    override fun solve1(input: String): Any {
        val lines = input.lines()
        var sum = 0
        lines.first().indices.forEach {
            var top = lines.size
            for (i in lines.indices) {
                when (lines[i][it]) {
                    'O' -> {
                        sum += top
                        top--
                    }

                    '#' -> top = lines.size - i - 1
                }
            }
        }

        return sum
    }


    override fun solve2(input: String): Any {
        val foundLines = HashMap<Int, List<String>>()
        var lines = input.lines()
        var cycleLen = 0
        var cycleStart = 0
        for (i in 0..1000000000) {
            lines = lines.cycle()
            if (foundLines.values.contains(lines)) {
                cycleLen = i - foundLines.entries.first { it.value == lines }.key
                cycleStart = i - cycleLen
                break
            } else {
                foundLines[i] = lines
            }
        }

        return (foundLines[cycleStart + (1000000000 - cycleStart - 1) % cycleLen] ?: listOf("")).calculateWeight()
    }

    override fun fileName() = "14"

    private fun List<String>.rotato(): List<String> {
        val numRows = this.size
        val numCols = this[0].length
        val rotatedMatrix = MutableList(numCols) { "" }
        for (i in 0..<numCols) {
            val newRow = StringBuilder()
            for (j in numRows - 1 downTo 0) {
                newRow.append(this[j][i])
            }
            rotatedMatrix[i] = newRow.toString()
        }

        return rotatedMatrix
    }

    private fun List<String>.tilt(): List<String> {
        val result = ArrayList<String>()
        for (line in this) {
            var newLine = line
            while ("O." in newLine) {
                newLine = newLine.replace("O.", ".O")
            }
            result.add(newLine)
        }
        return result
    }

    private fun List<String>.cycle(): List<String> {
        return this.rotato().tilt().rotato().tilt().rotato().tilt().rotato().tilt()
    }

    private fun List<String>.calculateWeight(): Int {
        val topLine = this.size
        var sum = 0
        this.forEachIndexed { i, line -> sum += (topLine - i) * line.count { it == 'O' } }
        return sum
    }

}