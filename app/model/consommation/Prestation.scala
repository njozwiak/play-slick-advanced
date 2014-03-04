package model.consommation

trait Prestation {
  def add
  def remove
}

class OffreEmplacement extends Prestation with Emplacement {
  override def add() = {
    super.add(println("Ajout Offre Emplacement"))
  }
  override def remove = println("Libere Offre Emplacement")
}

class OffrePack extends Prestation with Pack {
  override def add = {
    super.add(println("Ajout Offre Pack"))
  }
  override def remove = println("Libere Offre Pack")
}

class OptionEmplacement extends Prestation with Emplacement {
  override def add = {
    super.add(println("Ajout Option Emplacement"))
  }
  override def remove = println("Libere Offre Emplacement")
}

class OptionPack extends Prestation with Pack {
  override def add = {
    super.add(println("Ajout Option Pack"))
  }
  override def remove = println("Libere Offre Pack")
}


