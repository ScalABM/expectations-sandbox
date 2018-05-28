/** Learning rules describe how the parameters of a particular forecast rule evolve over time. */
trait LearningRuleLike {
  this: ForecastRuleLike =>

  def updateParams()

}
