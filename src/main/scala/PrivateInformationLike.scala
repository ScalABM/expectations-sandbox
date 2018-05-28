
trait PrivateInformationLike {

  def params: Map[String, Double]

}


case class PrivateInformation(memory: Double, params: Map[String, Double])