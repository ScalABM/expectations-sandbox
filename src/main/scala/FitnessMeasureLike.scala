trait FitnessMeasureLike {
  this: ForecastRuleLike =>

  def fitness: Double

  /** Update rule for fitness.
    *
    * @param args variable args.
    * @todo find better function signature.
    */
  def update(args: Double*): Unit

}



