import java.util.regex.Pattern

class Day15 : Day {
    override fun solve1(input: String): Any {
        return input.split(",").sumOf { hash(it) }
    }

    override fun solve2(input: String): Any {
        val codes = input.split(",")
        val boxes = HashMap<Int, ArrayList<String>>()
        for (code in codes) {
            val label = code.split(Pattern.compile("[=\\-]"))[0]
            val hash = hash(label)
            val operation = code[label.length]
            when (operation) {
                '-' -> boxes[hash]?.removeIf { it.startsWith(label) }
                '=' -> boxes.getOrPut(hash) { ArrayList() }.replaceOrAdd(code, label)
            }
        }

        return boxes.entries.sumOf {
            it.value.mapIndexed { i, label -> (it.key + 1) * (i + 1) * (label.split("=")[1].toInt()) }.sum()
        }
    }

    override fun fileName() = "15"

    private fun hash(input: String): Int {
        var currentValue = 0
        for (c in input) {
            currentValue += c.code
            currentValue *= 17
            currentValue %= 256
        }
        return currentValue
    }

    private fun ArrayList<String>.replaceOrAdd(code: String, label: String) {
        this.replaceAll {
            if (it.startsWith(label)) {
                code
            } else {
                it
            }
        }
        if (this.none { it.startsWith(label) }) {
            this.add(code)
        }
    }

}