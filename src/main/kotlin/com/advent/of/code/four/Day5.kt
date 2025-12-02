package com.advent.of.code.four

import kotlin.system.measureTimeMillis

object Day5 {
    private var pageOrderMap = mutableMapOf<String, List<String>>()
    @JvmStatic
    fun main(args: Array<String>) {
        val file = Util.getFileString(EXAMPLE_FILENAME).split(DELIM)
        val pages = file[1].split("\n").map { it.split(",") }
        val pageOrder = file[0]

        val time = measureTimeMillis {
            firstStar(pageOrder, pages)
            secondStar(pageOrder, pages)
        }
        println("time: $time")
    }

    private fun firstStar(pageOrder: String, pages: List<List<String>>){
        pageOrderMap = getPageOrder(pageOrder)
        val validPages = mutableListOf<List<String>>()
        validPages.addAll(getValidPageLists(pages))
        val sum = getSumOfMidNumbers(validPages.map { page -> page.map { numS -> Integer.parseInt(numS)} })
        println("sum: $sum")

    }

    private fun secondStar(pageOrder: String, pages: List<List<String>>) {
        println(getInvalidPageLists(pages))
    }

    private fun getSumOfMidNumbers(listOfPages: List<List<Int>>): Int {
        var sum = 0
        listOfPages.forEach { page ->
            val midPoint = page[page.size / 2]
            sum += midPoint
        }

        return sum

    }

    private fun getInvalidPageLists(pages: List<List<String>>): MutableList<List<String>> {
        val resultList = mutableListOf<List<String>>()
        pages.forEachIndexed outerloop@ { i, listOfNums ->
            val listOfNumsReversed = listOfNums.reversed()
            pageOrderMap.forEach { (k, v) ->
                val indexOfKey = listOfNumsReversed.indexOf(k)
                if (indexOfKey == -1) return@forEach
                val everythingBeforeNumKey = listOfNumsReversed.subList(indexOfKey + 1, listOfNumsReversed.size)
                if (v.any { it in everythingBeforeNumKey }) {
                    resultList.add(listOfNums)
                    return@outerloop
                }
            }
        }
        return resultList
    }

    private fun getValidPageLists(pages: List<List<String>>): MutableList<List<String>> {
        val resultList = mutableListOf<List<String>>()
        pages.forEachIndexed outerloop@ { i, listOfNums ->
            val listOfNumsReversed = listOfNums.reversed()
            pageOrderMap.forEach { (k, v) ->
                val indexOfKey = listOfNumsReversed.indexOf(k)
                if (indexOfKey == -1) return@forEach
                val everythingBeforeNumKey = listOfNumsReversed.subList(indexOfKey + 1, listOfNumsReversed.size)
                if (v.any { it in everythingBeforeNumKey }) {
                    return@outerloop
                }
            }

            resultList.add(listOfNums)
        }
        return resultList
    }

    private fun getPageOrder(pageOrderString: String): MutableMap<String, List<String>> {
        val listOfPairs = pageOrderString.split("\n")
        val map = mutableMapOf<String, List<String>>()

        for (pairS in listOfPairs) {
            val pair = pairS.split("|")
            val curList = map[pair[0]]
            map[pair[0]] = curList?.plus(pair[1]) ?: listOf(pair[1])
        }

        return map
    }


    private const val FILENAME = "src/main/res/twenty-four/day5.txt"
    private const val EXAMPLE_FILENAME = "src/main/res/twenty-four/example-day5.txt"
    private val DELIM = "\n\n"
}
