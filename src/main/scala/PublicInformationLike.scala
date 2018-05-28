/** Trait representing publicly observable information. */
trait PublicInformationLike {

  def dividend: Double

  var historicalValues: Vector[Double]

  def netInterestRate: Double

  def averageValue: Double = {
    historicalValues.sum / historicalValues.length
  }

  /** Fundamental value is assumed to be public knowledge. */
  def fundamentalValue: Double = {
    dividend / netInterestRate
  }

  def updateHistoricalValues(value: Double): Unit = {
    historicalValues = historicalValues.+:(value)
  }

}


case class PublicInformation(initialPrice: Double,
                             dividend: Double,
                             netInterestRate: Double) extends PublicInformationLike {

  var historicalValues: Vector[Double] = Vector(initialPrice)

}
