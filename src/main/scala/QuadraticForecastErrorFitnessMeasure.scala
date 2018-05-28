trait QuadraticForecastErrorFitnessMeasure extends FitnessMeasureLike {
  this: ForecastRuleLike =>

  val eta: Double

  var fitness: Double = 0.0

  def update(observedValue: Double): Unit = {
    -math.pow(forecastError, 2) + eta * fitness
  }

}
