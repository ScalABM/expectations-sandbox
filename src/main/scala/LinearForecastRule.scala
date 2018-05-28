/**
  * @note Should be some way to represent a wide class of expectations rules as simple linear filters. Linear filter
  */
sealed trait LinearForecastRule extends ForecastRuleLike {

  protected def weights: Vector[Double]

  /** Computes a weighted sum.
    *
    * @param values
    * @param weights
    * @return the weighted sum of values.
    */
  protected def weightedSum(values: Vector[Double], weights: Vector[Double]): Double = {
    val weightedValues = weights zip values map {case (x, y) => x * y}
    weightedValues.sum
  }

}


/** Adaptive Expectations rule.
  *
  * @param params
  * @param publicInformation
  */
case class AdaptiveForecastRule(params: Vector[Double],
                                publicInformation: PublicInformationLike) extends LinearForecastRule {

  protected def weights: Vector[Double] = {
    Vector(1.0, 1 - params.head)
  }

  def forecast(): Double = {
    val values = Vector(publicInformation.historicalValues.head, forecastError)
    weightedSum(values, weights)
  }

  /* Initialize forecast error. */
  protected var _forecastError: Double = 0.0

}


/** Extrapolative expectations formation rule.
  *
  * @param publicInformation
  */
case class TrendFollowingForecastRule(params: Vector[Double],
                                      publicInformation: PublicInformationLike) extends LinearForecastRule {

  protected def weights: Vector[Double] = params

  def forecast(): Double = {
    weightedSum(publicInformation.historicalValues, weights)
  }

  /* Initialize forecast error. */
  protected var _forecastError: Double = 0.0

}

/*
/** "Anchor and adjust" expectations formations rule.
  *
  * @param privateInformation
  * @param publicInformation
  */
case class AnchorAdjustForecastRule(privateInformation: PrivateInformationLike,
                                    publicInformation: PublicInformationLike,
                                    fitnessRule: Option[FitnessMeasureLike]) extends LinearForecastRule {

  def forecast: Double = {
    val values = publicInformation.historicalValues :+ publicInformation.averageValue  // note use of append operator!
    weightedSum(values, weights)
  }

}

*/



