package com.gabrielspassos.sample

import org.junit.jupiter.api.Assertions.{assertEquals, assertTrue}
import org.junit.jupiter.api.Test

class SampleTest {
  
  @Test
  def shouldTestAdd(): Unit = {
    assertTrue(true)
    assertEquals(6, 4 + 2)
  }
}
