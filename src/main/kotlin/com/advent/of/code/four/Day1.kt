package com.advent.of.code.four

import Util.getLinesList
import kotlin.math.abs
import kotlin.system.measureTimeMillis

object Day1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val lineList = getLinesList(FILENAME)
        val list1Arr = mutableListOf<Int>()
        val list2Arr = mutableListOf<Int>()
        lineList.forEach { line ->
            line
                .split(DELIM)
                .map {
                    Integer.parseInt(it)
                }
                .also { intLine ->
                    list1Arr.add(intLine[0])
                    list2Arr.add(intLine[1])
                }
        }

        val time1 = measureTimeMillis {
            getSimilarityScore(list1Arr, list2Arr)
        }
        val time2 = measureTimeMillis {
            getDistanceOfArrays(list1Arr, list2Arr)
        }

        println("time1: $time1\ntime2: $time2")
        print(getSimilarityScore(list1Arr, list2Arr))
        getDistanceOfArrays(list1Arr, list2Arr)
    }

    private fun getSimilarityScore(arr1: List<Int>, arr2: List<Int>): Int {
        val map1 = mutableMapOf<Int, Int>()
        val map2 = mutableMapOf<Int, Int>()
        val combinedMap = mutableMapOf<Int, Int>()

        arr1.forEach { num ->
            map1[num] = map1[num]?.plus(1) ?: 1
        }
        arr2.forEach {  num ->
            map2[num] = map2[num]?.plus(1) ?: 1
        }

        map1.forEach { (k, _) ->
            combinedMap[k] = (map1[k] ?: 0) * (map2[k]?: 0)
        }

        val result = combinedMap.entries.fold(0) { acc, entry ->
           acc + (entry.key * entry.value)
        }

        return result
    }

    private fun getDistanceOfArrays(arr1: List<Int>, arr2: List<Int>): Int {
        val arr1sorted = arr1.sorted()
        val arr2sorted = arr2.sorted()
        val distanceArr = arr1.zip(arr2) { i, j -> abs(i - j)}
        val sum = distanceArr.fold(0) { acc, i -> acc + i }

        return sum
    }

}


private const val FILENAME = "src/main/res/day1.txt"
private const val EXAMPLE_FILENAME = "src/main/res/example-day1.txt"
private const val DELIM = "   "
