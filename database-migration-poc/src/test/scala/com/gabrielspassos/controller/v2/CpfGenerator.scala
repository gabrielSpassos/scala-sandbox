package com.gabrielspassos.controller.v2

import java.security.SecureRandom
import scala.annotation.tailrec

object CpfGenerator:

  private val rnd = SecureRandom()

  private def computeFirstDigit(digits: Array[Int]): Int =
    // weights 10..2
    val sum = digits.indices.foldLeft(0) { (acc, i) =>
      acc + digits(i) * (10 - i)
    }
    val rem = sum % 11
    if rem < 2 then 0 else 11 - rem

  private def computeSecondDigit(digits: Array[Int], firstDigit: Int): Int =
    // weights 11..2
    val sum = digits.indices.foldLeft(0) { (acc, i) =>
      acc + digits(i) * (11 - i)
    } + firstDigit * 2
    val rem = sum % 11
    if rem < 2 then 0 else 11 - rem

  private def allEqual(xs: Array[Int]): Boolean =
    xs.forall(_ == xs(0))

  def generate(): String =
    @tailrec
    def loop(): Array[Int] =
      val base = Array.fill(9)(rnd.nextInt(10))
      val d1 = computeFirstDigit(base)
      val d2 = computeSecondDigit(base, d1)
      val cpfDigits = base ++ Array(d1, d2)
      if allEqual(cpfDigits) then loop() else cpfDigits

    loop().mkString

@main def runDemo(): Unit =
  println("\nCompact CPFs:")
  (1 to 5).foreach(_ => println(CpfGenerator.generate()))
