package principal
import modelos.Operaciones
import modelos.Persona

object ejemploppal { // se hizo el objeto para tenerlo como un contenedor
  def main(args: Array[String]): Unit = { //def main protocolo para indicar que es la clase ppal
    println("HTe vas a morir hoy") // puedo pasar argumentos para ser usados en la ejecución.

    val oe = new Persona("Juan","Oe")
    oe.saludarPersona()
  }
}



object Operaciones {

  def main(args: Array[String]): Unit = {
    val operacionllamada = new Operaciones(12, 6)
    operacionllamada.suma()
  }

}