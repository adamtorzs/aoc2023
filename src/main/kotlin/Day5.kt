import java.util.*
import kotlin.math.min

class Day5 : Day {

    private var seeds: List<Long> = ArrayList()

    private val seedToSoil: TreeMap<Long, Range> = TreeMap()
    private val soilToFertilizer: TreeMap<Long, Range> = TreeMap()
    private val fertilizerToWater: TreeMap<Long, Range> = TreeMap()
    private val waterToLight: TreeMap<Long, Range> = TreeMap()
    private val lightToTemp: TreeMap<Long, Range> = TreeMap()
    private val tempToHumidity: TreeMap<Long, Range> = TreeMap()
    private val humidityToLoc: TreeMap<Long, Range> = TreeMap()
    override fun solve1(input: String): Any {
        val lines = input.lines()
        seeds = lines.first().split(" ").subtract(listOf("seeds:")).map(String::toLong).toList()
        parseData(lines.subList(2, lines.size))
        return seeds.minOf { getLocationForSeed(it) }
    }

    override fun solve2(input: String): Any {
        var minLocation = Long.MAX_VALUE

        seeds
            .withIndex()
            .groupBy { it.index / 2 }
            .map { out -> out.value.map { it.value }.zipWithNext().first() }
            .parallelStream()
            .forEach { pair ->
                for (seed in pair.first..(pair.first + pair.second)) {
                    minLocation = min(getLocationForSeed(seed), minLocation)
                }
            }
        return minLocation
    }

    override fun fileName(): String = "5"


    private fun getLocationForSeed(seed: Long): Long =
        seed.findInRange(seedToSoil)
            .findInRange(soilToFertilizer)
            .findInRange(fertilizerToWater)
            .findInRange(waterToLight)
            .findInRange(lightToTemp)
            .findInRange(tempToHumidity)
            .findInRange(humidityToLoc)

    private fun Long.findInRange(map: TreeMap<Long, Range>): Long {
        return map.floorEntry(this)?.value?.convertSource(this) ?: this

    }

    private fun String.toLongList(delimiter: String = " "): List<Long> =
        this.trim().split(delimiter).filter { it.isNotEmpty() }.map { it.toLong() }

    private fun parseData(lines: List<String>) {
        var currList: TreeMap<Long, Range> = TreeMap()
        for (line in lines) {
            when (line) {
                "seed-to-soil map:" -> currList = seedToSoil
                "soil-to-fertilizer map:" -> currList = soilToFertilizer
                "fertilizer-to-water map:" -> currList = fertilizerToWater
                "water-to-light map:" -> currList = waterToLight
                "light-to-temperature map:" -> currList = lightToTemp
                "temperature-to-humidity map:" -> currList = tempToHumidity
                "humidity-to-location map:" -> currList = humidityToLoc
                else -> {
                    if (line.isNotEmpty()) {
                        val rangeValues = line.toLongList()
                        currList[rangeValues[1]] = Range(rangeValues[1], rangeValues[0], rangeValues[2])
                    }
                }
            }
        }
    }
}

class Range(
    private val source: Long,
    private val destination: Long,
    private val range: Long
) {
    fun convertSource(s: Long): Long? {
        if (s >= source && s < source + range) {
            return s + (destination - source)
        }
        return null
    }
}