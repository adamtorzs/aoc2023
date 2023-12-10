class Day9 : Day {
    override fun solve1(input: String): Any = input.lines().sumOf { predict(it.toIntList()) }
    override fun solve2(input: String): Any = input.lines().sumOf { predict(it.toIntList().reversed()) }
    private fun predict(numbers: List<Int>): Int =
        if (numbers.isEmpty()) 0 else numbers.last() + predict(numbers.zipWithNext { a, b -> b - a })
    override fun fileName(): String = "9"
}