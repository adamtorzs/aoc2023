class Day1 : Day {
    override fun fileName(): String = "1"

    override fun solve1(input: String): Any {
        var sum = 0
        for(line in input.lines()) {
            val removedChar = line.replace(Regex("[a-zA-Z]"), "")
            sum += Integer.parseInt((removedChar.first().toString() + removedChar.last().toString()))
        }
        return sum
    }

    override fun solve2(input: String): Any {
        var sum = 0
        for(line in input.lines()) {
            sum += findFirstDigit(line) * 10 + findLastDigit(line)
        }
        return sum
    }

    private fun findFirstDigit(input: String): Int {
        for(i in input.indices) {
            val sub = input.substring(i)
            val num = convertNum(sub)
            if(num > 0) return num
        }
        return 0
    }

    private fun findLastDigit(input: String): Int {
        for(i in (input.length - 1) downTo 0) {
            val sub = input.substring(i, input.length)
            val num = convertNum(sub)
            if(num > 0) return num
        }
        return 0
    }

    private fun convertNum(input: String) =
        when {
            input.first().isDigit() -> input.first().digitToInt()
            input.startsWith("one") -> 1
            input.startsWith("two") -> 2
            input.startsWith("three") -> 3
            input.startsWith("four") -> 4
            input.startsWith("five") -> 5
            input.startsWith("six") -> 6
            input.startsWith("seven") -> 7
            input.startsWith("eight") -> 8
            input.startsWith("nine") -> 9
            else -> 0
        }

}