package com.gabrielspassos

import scala.collection.mutable

object GFG:

  // Function to construct bitonic
  // sequence of length N from
  // integers in the range [L, R]
  def bitonicSequence(num: Int, lower: Int, upper: Int): mutable.ArrayDeque[Int] =
    // If sequence is not possible
    if num > (upper - lower) * 2 + 1 then
      println(-1)
      return mutable.ArrayDeque.empty

    // Store the resultant list
    val ans = mutable.ArrayDeque[Int]()

    for i <- 0 until math.min(upper - lower + 1, num - 1) do
      ans.addOne(upper - i)

    // If size of deque < n
    for i <- 0 until (num - ans.size) do
      // Add elements from start
      ans.prepend(upper - i - 1)

    // Print the stored list
    println(ans)

    ans

  @main def runGFG(): Unit =
    // Function Call
    val result1 = bitonicSequence(5, 3, 10)
    assert(result1 == mutable.ArrayDeque(9, 10, 9, 8, 7))

    val result2 = bitonicSequence(5, 2, 5)
    assert(result2 == mutable.ArrayDeque(4, 5, 4, 3, 2))

