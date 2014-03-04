package model.consommation

object Main {

  def main(args: Array[String]) = {

    def builder(prestation:String, modeConso:String) = (prestation, modeConso) match {
      case ("Offre", "Emplacement") => new OffreEmplacement
      case ("Offre", "Pack") => new OffrePack
      case ("Option", "Emplacement") => new OptionEmplacement
      case ("Option", "Pack") => new OptionPack
    }

    val offreEmpl = builder("Offre", "Emplacement")
    offreEmpl.add
    offreEmpl.remove

    val offrePack = builder("Offre", "Pack")
    offrePack.add
    offrePack.remove
  }
}
