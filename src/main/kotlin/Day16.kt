class Day16 : Day {

    override fun solve1(input: String): Any {
        return beam(Pair(0, 0), Direction.RIGHT, input.lines()).count()
    }

    override fun solve2(input: String): Any {
        val lines = input.lines()
        val startPoints = ArrayList<Pair<Direction, Pair<Int, Int>>>()
        for (i in lines.indices) {
            startPoints.add(Direction.RIGHT to (i to 0))
            startPoints.add(Direction.LEFT to (i to lines.first().lastIndex))
        }
        for (i in lines.first().indices) {
            startPoints.add(Direction.DOWN to (0 to i))
            startPoints.add(Direction.UP to (lines.lastIndex to i))
        }
        return startPoints.maxOf { beam(it.second, it.first, lines).count() }
    }

    private fun beam(
        currentPoint: Pair<Int, Int>,
        currentDirection: Direction,
        field: List<String>,
        visited: HashSet<Pair<Direction, Pair<Int, Int>>> = HashSet()
    ): Set<Pair<Int, Int>> {
        if (currentPoint.first < 0 || currentPoint.second < 0 || currentPoint.first == field.size || currentPoint.second == field.first().length) {
            return HashSet()
        }
        if (visited.contains(currentDirection to currentPoint)) {
            return HashSet()
        }


        visited.add(Pair(currentDirection, currentPoint))
        val energized = HashSet<Pair<Int, Int>>()
        energized.add(currentPoint)

        return when (field[currentPoint.first][currentPoint.second]) {
            '.' -> energized.plus(
                beam(
                    currentDirection.nextInDirection(currentPoint.first, currentPoint.second),
                    currentDirection,
                    field,
                    visited
                )
            )

            '/', '\\' -> {
                val nextDir = currentDirection.changeDir(field[currentPoint.first][currentPoint.second])
                val nextPos = nextDir.nextInDirection(currentPoint.first, currentPoint.second)
                energized.plus(beam(nextPos, nextDir, field, visited))
            }

            '|' -> when (currentDirection.isVertical()) {
                true -> energized.plus(
                    beam(
                        currentDirection.nextInDirection(currentPoint.first, currentPoint.second),
                        currentDirection,
                        field,
                        visited
                    )
                )

                false -> {
                    energized.plus(beam(currentPoint, Direction.UP, field, visited))
                        .plus(beam(currentPoint, Direction.DOWN, field, visited))
                }
            }

            '-' -> when (currentDirection.isVertical()) {
                false -> energized.plus(
                    beam(
                        currentDirection.nextInDirection(currentPoint.first, currentPoint.second),
                        currentDirection,
                        field,
                        visited
                    )
                )

                true -> {
                    energized
                        .plus(beam(currentPoint, Direction.LEFT, field, visited))
                        .plus(beam(currentPoint, Direction.RIGHT, field, visited))
                }
            }

            else -> HashSet()
        }
    }



    override fun fileName(): String = "16"
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    fun changeDir(mirror: Char): Direction {
        return when (Pair(this, mirror)) {
            Pair(UP, '/'), Pair(DOWN, '\\') -> RIGHT
            Pair(UP, '\\'), Pair(DOWN, '/') -> LEFT
            Pair(LEFT, '\\'), Pair(RIGHT, '/') -> UP
            Pair(LEFT, '/'), Pair(RIGHT, '\\') -> DOWN
            else -> throw IllegalArgumentException()
        }
    }

    fun nextInDirection(line: Int, col: Int): Pair<Int, Int> {
        return when (this) {
            UP -> Pair(line - 1, col)
            DOWN -> Pair(line + 1, col)
            RIGHT -> Pair(line, col + 1)
            LEFT -> Pair(line, col - 1)
        }
    }

    fun isVertical(): Boolean = this == UP || this == DOWN
}