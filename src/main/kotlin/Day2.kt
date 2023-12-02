import java.lang.Integer.max

class Day2 : Day {
    override fun fileName(): String = "2"

    override fun solve1(input: String): Any {
        val red = 12
        val green = 13
        val blue = 14
        var sum = 0
        val lines = input.lines()

        for(line in lines) {
            val split1 = line.split(": ")
            var id = Integer.parseInt(split1[0].split(" ")[1])
            for(ball in split1[1].split(Regex("[,;]")) ) {
                val split2 = ball.trim().split(" ")
                val count = Integer.parseInt(split2[0])
                val possible = when(split2[1]) {
                    "red" -> count <= red
                    "green" -> count <= green
                    "blue" -> count <= blue
                    else -> false
                }
                if(!possible) {
                    id = 0
                }
            }
            sum += id
        }
        return sum
    }

    override fun solve2(input: String): Any {
        var sum = 0
        val lines = input.lines()
        for(line in lines) {
            var red = 0
            var green = 0
            var blue = 0

            val split1 = line.split(": ")
            for(ball in split1[1].split(Regex("[,;]"))) {
                val split2 = ball.trim().split(" ")
                val count = Integer.parseInt(split2[0])
                when(split2[1]) {
                    "red" -> red = max(red, count)
                    "green" -> green = max(green, count)
                    "blue" -> blue = max(blue, count)
                }
            }
            sum += red * green * blue
        }

        return sum
    }

}