package com.gabrielspassos.poc

import java.math.BigDecimal

@main
def main(): Unit = {
  val zero1 = BigDecimal.ZERO
  val zero2 = BigDecimal("0")
  val zero3 = BigDecimal("0.0")
  val zero4 = BigDecimal("0.00")
  val zero5 = BigDecimal.valueOf(0)
  val zero6 = BigDecimal.valueOf(0.0)

  println("Zeros POC!")

  println(s"$zero1 == $zero2: ${zero1.equals(zero2)}")
  println(s"$zero1 == $zero3: ${zero1.equals(zero3)}")
  println(s"$zero1 == $zero4: ${zero1.equals(zero4)}")
  println(s"$zero1 == $zero5: ${zero1.equals(zero5)}")
  println(s"$zero1 == $zero6: ${zero1.equals(zero6)}")
}