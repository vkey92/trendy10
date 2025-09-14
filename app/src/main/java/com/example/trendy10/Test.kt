fun main() {

    longestSubstringWithoutRepeatingCharacters("aabbcde")
}



fun longestSubstringWithoutRepeatingCharacters(str:String){
    val map = mutableMapOf<Char, Int>()
    var j = 0
    var maxLength = 0

    for (i in str.indices) {

        val currentChar = str[i]
        if (map.containsKey(currentChar) && map[currentChar]!! >= j) {
            j = map[currentChar]!! + 1
        }
        map[currentChar] = i
        maxLength = i - j + 1
    }

    println("Longest length = $maxLength")
}

fun moveZeroEndWithMaintainOrder(arr: IntArray) {
    val resultArray = mutableListOf<Int>()
    var zeroCount = 0
    for (i in arr.indices) {
        if (arr[i] == 0) {
          zeroCount++
        } else {
            resultArray.add(arr[i])
        }
    }
   repeat(zeroCount){
       resultArray.add(0)
   }
    println("final result == ${resultArray.joinToString()}")
}

fun reverseString(str: String) {
    val charArray: CharArray = str.toCharArray()
    var k = charArray.size
    for (i in 0 until charArray.size / 2) {
        k--
        var temp = charArray[i]
        charArray[i] = charArray[k]
        charArray[k] = temp

    }
    val reverse = String(charArray)
    println(reverse)

}

fun findElementsOfTargetSum(arr: IntArray, target: Int): IntArray {
    val map = mutableMapOf<Int, Int>()
    for (i in arr.indices) {
        val component = target - arr[i]
        if (map.containsKey(component)) {
            return intArrayOf(map[component]!!, i)
        }
        map[arr[i]] = i
    }
    return intArrayOf()
}

fun reversedArray() {
    var arr = arrayOf(1, 2, 3, 4, 5, 6)
    var k = arr.size
    for (i in 0 until arr.size / 2) {
        k--
        println(i)
        val temp = arr[i]
        arr[i] = arr[k]
        arr[k] = temp
    }

    println("Original: ${arr.joinToString()}")
}

fun findSecondLargestElementInArray() {
    val arr = arrayOf(10, 7, 3, 4, 5, 6)
    var firstHigh = 0
    //var secondHigh = arr.filter { it != arr.maxOrNull() }.maxOrNull()
    var secondHigh = 0
    var thirdHigh = 0
    for (i in arr.indices) {
        if (arr[i] > firstHigh) {
            secondHigh = firstHigh
            thirdHigh = secondHigh
            firstHigh = arr[i]
        } else if (arr[i] > secondHigh && arr[i] != firstHigh) {
            secondHigh = arr[i]
        } else if (arr[i] > thirdHigh && arr[i] != thirdHigh) {
            thirdHigh = arr[i]
        }
    }

    println("Original: $secondHigh")
    println("Original: $thirdHigh")
}

fun findMinElementInArray() {
    val arr = arrayOf(10, 7, 3, 4, 5, 6)
    var min = arr[0]
    for (i in arr.indices) {
        if (arr[i] < min) {
            min = arr[i]
        }
    }

    println("Original: $min")
}

fun sumInSortedArray(target: Int): Boolean {
    val arr = arrayOf(1, 2, 3, 4, 5)
    var left = 0
    var right = arr.size - 1

    while (left < right) {
        val sum = arr[left] + arr[right]
        when {
            sum == target -> return true
            sum < target -> left++
            else -> right--

        }
    }
    return false
}

fun maximumSumSubArray(target: Int) {
    val arr = arrayOf(1, 2, 3, 4, 5)
    var highSum = 0
    for (i in arr.indices step target) {
        val end = minOf(i + target, arr.size)
        val subArray = arr.sliceArray(i until end)
        var totalSum = 0
        for (j in subArray.indices) {
            totalSum += subArray[j]
        }
        if (highSum < totalSum) {
            highSum = totalSum
        }
    }


    println(highSum)

}

fun checkSubArraySumZero(arr: IntArray): Boolean {
    val set = HashSet<Int>()
    var preFixSum = 0
    for (i in arr) {
        preFixSum += i

        if (preFixSum == 0)
            return true
        if (set.contains(preFixSum))
            return true
        set.add(preFixSum)

    }
    return false
}