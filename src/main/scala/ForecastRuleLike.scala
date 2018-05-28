trait ForecastRuleLike {

  /* Cached forecast error */
  protected var _forecastError: Double

  /** The actual forecast. */
  def forecast(): Double

  /** The forecast error. */
  def forecastError: Double = _forecastError

  /** parameters for the forecast rule. */
  def params: Vector[Double]

  /** All publicly available information is assumed to be common knowledge. */
  protected def publicInformation: PublicInformationLike

  /** Updates the forecast error given some newly observed value of the variable. */
  def updateForecastError(observedValue: Double): Unit = {
    _forecastError = forecast - observedValue
  }

}
