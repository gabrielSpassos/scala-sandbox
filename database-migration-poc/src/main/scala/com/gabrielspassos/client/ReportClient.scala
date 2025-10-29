package com.gabrielspassos.client

import org.springframework.stereotype.Component

import scala.util.Random

@Component
class ReportClient {
  
  def generateReport(): String = {
    // Simulate report generation
    val reportId = Random().nextInt()
    s"Fake report content with id: $reportId"
  }

}
