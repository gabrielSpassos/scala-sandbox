package com.gabrielspassos.client

import org.springframework.stereotype.Component

import java.security.SecureRandom
import scala.annotation.tailrec

@Component
class CustomerManagerClient {
  
  def getCustomerCpfByExternalId1(externalId1: String): Option[String] = {
    // Simulate fetching CPF by external ID
    Option(CpfGenerator.generate())
  }
}

object CpfGenerator {

  private val rnd = SecureRandom()

  def generate(): String = {
    @tailrec
    def loop(): Array[Int] = {
      val base = Array.fill(9)(rnd.nextInt(10))
      val d1 = computeFirstDigit(base)
      val d2 = computeSecondDigit(base, d1)
      val cpfDigits = base ++ Array(d1, d2)
      if allEqual(cpfDigits) then loop() else cpfDigits
    }

    loop().mkString
  }

  private def computeFirstDigit(digits: Array[Int]): Int = {
    val sum = digits.indices.foldLeft(0) { (acc, i) =>
      acc + digits(i) * (10 - i)
    }
    val rem = sum % 11
    if rem < 2 then 0 else 11 - rem
  }

  private def computeSecondDigit(digits: Array[Int], firstDigit: Int): Int = {
    val sum = digits.indices.foldLeft(0) { (acc, i) =>
      acc + digits(i) * (11 - i)
    } + firstDigit * 2
    val rem = sum % 11
    if rem < 2 then 0 else 11 - rem
  }

  private def allEqual(xs: Array[Int]): Boolean = xs.forall(_ == xs(0))
  
}