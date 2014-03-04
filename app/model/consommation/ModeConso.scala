package model.consommation

trait ModeConso {
  def add(method: => Unit) {
    method
  }
  def remove
}

trait Emplacement extends ModeConso {
  override def add(method: => Unit) {
    super.add(method)
    println("Prend un emplacement")
  }
}

trait Pack extends ModeConso {
  override def add(method: => Unit) {
    super.add(method)
    println("Credite")
  }
  def dateFin = println("Calcul date de fin (28 jours)")
}
