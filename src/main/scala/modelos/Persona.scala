package modelos

class Persona (nombre:String, apellido:String) {
  val mensaje = "Hola " + nombre + " bienvenido"

  def saludarPersona() = { // puse un método que es saludar persona que imprime el mensaje
    println(mensaje)

  }
}