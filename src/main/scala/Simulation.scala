import scala.util.Random
import collection.parallel.ParSeq

object Simulation extends App {

  val numberActor: Int = 1000

  val simulationLength: Int = 10000

  val prng = new Random(42)

  val publicInformation = new PublicInformation(initialPrice = 100.0, dividend = 3.0, netInterestRate = 0.05)

  val adaProbability = 0.33
  val wtfProbability = 0.33
  val fundamentalistProbability = 1 - adaProbability - wtfProbability

  val actors = for (i <- 0 until numberActor) yield {
    if (prng.nextDouble() < adaProbability) {
      AdaptiveForecastRule(Vector(0.65), publicInformation)
    } else if (prng.nextDouble() < wtfProbability) {
      TrendFollowingForecastRule(Vector(1.4, 0.4), publicInformation)
    } else {
      FundamentalistForecastRule(Vector(), publicInformation)
    }
  }


  def average(values: ParSeq[Double]): Double = {
    values.sum / values.length
  }


  def marketPrice(priceForecasts: ParSeq[Double], publicInformation: PublicInformationLike): Double = {
    val discountFactor = 1 / (1 + publicInformation.netInterestRate)
    val averagePriceForecast = average(priceForecasts)
    discountFactor * (averagePriceForecast + publicInformation.dividend)

  }


  for (t <- 0 until simulationLength) {

    // forecast the price (parallelizable!)
    val priceForecasts = actors.par.map(actor => actor.forecast())

    // compute the market price
    val price = marketPrice(priceForecasts, publicInformation)

    // update the historical values
    publicInformation.updateHistoricalValues(price)

    // update forecast errors (parallelizable!)
    actors.par.foreach(actor => actor.updateForecastError(price))

  }

  println(publicInformation.historicalValues.reverse)

}
