class Day12 : Day {
    override fun solve1(input: String): Any {
        var sum = 0L
        for (line in input.lines()) {
            val s = line.split(" ")
            val springs = s[0]
            val note = s[1]
            sum += count(springs, note.toIntList(","))!!

        }
        return sum
    }

    override fun solve2(input: String): Any {
        var sum = 0L

        for (line in input.lines()) {
            val s = line.split(" ")
            val springs = s[0] + "?" + s[0] + "?" + s[0] + "?" + s[0] + "?" + s[0]
            val note = s[1] + "," + s[1] + "," + s[1] + "," + s[1] + "," + s[1]

            sum += count(springs, note.toIntList(","))!!

        }
        return sum
    }

    private val memo = HashMap<Pair<String, List<Int>>, Long>()

    private fun count(input: String, groups: List<Int>): Long? {
        if (groups.isEmpty()) return if ("#" in input) 0 else 1
        if (input.isEmpty()) return 0

        return if (memo.containsKey(input to groups)) {
            memo[input to groups]
        } else {
            var result = 0L

            if (".?".contains(input.first())) {
                result += count(input.substring(1, input.length), groups)!!
            }

            if ("#?".contains(input.first()) &&
                groups.first() <= input.length &&
                !input.substring(0, groups.first()).contains(".") &&
                (groups.first() > input.lastIndex || input[groups.first()] != '#')
            ) {
                val newInput = input.drop(groups.first() + 1)
                val newGroups = groups.subList(1, groups.size)
                result += count(newInput, newGroups)!!
            }

            memo[input to groups] = result
            result
        }
    }


    override fun fileName(): String = "12"
}