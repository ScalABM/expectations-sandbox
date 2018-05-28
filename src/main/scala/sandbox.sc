trait DiscreteChoiceLike[T] {

  def alternatives: Seq[T]

  def probabilities: Seq[T]


}

def fitnessMeasures(rules: Vector[FitnessMeasureLike]): Vector[Double] = {
  rules.map(r => r.fitness)
}

def choiceProbability(beta: Double, index: Int, fitnessMeasures: Vector[Double]): Double = {
  math.exp(beta * fitnessMeasures(index)) / normalizationConstant(beta, fitnessMeasures)
}

def normalizationConstant(beta: Double, fitnessMeasures: Vector[Double]): Double = {
  fitnessMeasures.map(m => math.exp(beta * m)).sum
}