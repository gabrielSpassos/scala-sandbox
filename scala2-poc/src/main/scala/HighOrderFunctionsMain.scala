import SalaryRaiser.{hugePromotion, mediumPromotion, smallPromotion}

object HighOrderFunctionsMain extends App {

  private val salaries = Seq(20.000, 8.500, 31.000)
  private val increaseInFivePercent = (salary: Double) => {
    val fivePercent = 0.05
    val salaryFivePercent = fivePercent * salary
    salary + salaryFivePercent
  }
  private val increasedSalaries = salaries.map(increaseInFivePercent)

  println(s"Initial salaries $salaries")
  println(s"Small promotion ${smallPromotion(salaries)}")
  println(s"salaries + 5% $increasedSalaries")
  println(s"Medium promotion ${mediumPromotion(salaries)}")
  println(s"Huge promotion ${hugePromotion(salaries)}")
}

object SalaryRaiser {

  private def promotion(salaries: Seq[Double], promotionFunction: Double => Double): Seq[Double] =
    salaries.map(promotionFunction)

  def smallPromotion(salaries: Seq[Double]): Seq[Double] = {
    promotion(salaries, salary => {
      val salaryThreePercent = 0.03 * salary
      salary + salaryThreePercent
    })
  }

  def mediumPromotion(salaries: Seq[Double]): Seq[Double] = {
    promotion(salaries = salaries, promotionFunction = salary => {
      val salaryFivePercent = 0.05 * salary
      val salaryPlusFivePercent = salary + salaryFivePercent
      val bonus = 6 * salaryPlusFivePercent * 0.01
      salaryPlusFivePercent + bonus
    })
  }

  def hugePromotion(salaries: Seq[Double]): Seq[Double] = {
    promotion(salaries = salaries, promotionFunction = salary => {
      val salarySixPercent = 0.06 * salary
      val salaryPlusSixPercent = salary + salarySixPercent
      val bonus = 12 * salaryPlusSixPercent * 0.01
      salaryPlusSixPercent + bonus
    })
  }

}
