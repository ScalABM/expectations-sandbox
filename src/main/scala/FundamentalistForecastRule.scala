/** Class representing a "fundamentalist" forecasting rule.
  *
  * @param publicInformation
  * @note The fundamental value of the asset is just the ratio between the average dividend and the net interest rate.
  */
case class FundamentalistForecastRule(params: Vector[Double],
                                      publicInformation: PublicInformationLike) extends ForecastRuleLike {

  /** A "fundamentalist" always forecasts the fundamental value of the asset. */
  def forecast(): Double = publicInformation.fundamentalValue

  /* Initialize forecast error. */
  protected var _forecastError: Double = 0.0

}
