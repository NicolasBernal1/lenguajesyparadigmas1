object Main {
  import scala.concurrent._
  import scala.concurrent.duration._
  import scala.concurrent.ExecutionContext.Implicits.global
  def main(args: Array[String]): Unit = {
    //println(suma(10))
    //println(factorial(5))
    //println(filtroPares(List(1,2,3,4,5,6,7)))
    //println(quickSort(List(6,12,5,89,1,4,35)))
    //println(cribaEratostenes(50))

    val transacciones = (1 to 5).toList
    var resultadosFinales = transacciones.map(procesarTransaccion)
    val resultados = Await.result(Future.sequence(resultadosFinales), 5.seconds)
    //println(resultados)

    val usuarios = List(
      Usuario("EL JUANGUI","EL.JUANGUI@EMAIL.COM", Some("+57 222-222-2222")),
      Usuario("maria lopez", "MARIA.LOPEZ@email.com", None),
      Usuario(" juliana ", "juliana.algo@domain.org", Some("898-756-4321"))
    )
    val usuariosNormalizados = normalizarUsuarios(usuarios)
    //usuariosNormalizados.foreach(println)
  }
  def suma(n: Int): Int = {
    (1 to n).sum
  }

  def factorial(n: Int): Int = {
    if(n <= 1) 1
    else n * factorial(n - 1)
  }

  def filtroPares(lista: List[Int]) = {
    lista.filter(_ % 2 == 0)
  }

  def quickSort(lista: List[Int]): List[Int] = {
    if(lista.length <= 1) lista
    else {
      val pivot = lista(lista.length / 2)
      quickSort(lista.filter(_ < pivot)) ++ lista.filter(_ == pivot) ++ quickSort(lista.filter(_ > pivot))
    }
  }

  def cribaEratostenes(limite: Int): List[Int] = {
    val primos = Array.fill(limite + 1)(true)
    primos(0) = false
    primos(1) = false
    for(p <- 2 to math.sqrt(limite).toInt if primos(p)){
      for(i <- p * p to limite by p){
        primos(i) = false
      }
    }
    (for(p <- 2 to limite if primos(p)) yield p).toList
  }

  def procesarTransaccion(id: Int): Future[String] = Future {
    Thread.sleep(1000)
    s"Transaccion $id procesada"
  }

  case class Usuario(nombre: String, email:String, telefono:Option[String])

  def normalizarNombre(nombre: String):String = {
    nombre.trim.split(" ").map(_.capitalize).mkString(" ")
  }

  def limpiarEmail(email:String): String = {
    email.trim.toLowerCase()
  }

  def normalizarUsuarios(usuarios:List[Usuario]): List[Usuario] = {
    usuarios.map { usuario =>
      usuario.copy(
        nombre = normalizarNombre(usuario.nombre),
        email = limpiarEmail(usuario.email)
      )
    }
  }

  /*Reto
    Sistema de recomendaciones de productos basado en perfiles de usuario
      Simular un sistema básico de recomendación de productos en el que se analicen las compras previas de unos usuarios
      para generar recomendaciones personalizadas.
      Utilizar estructuras de datos avanzadas y patrones funcionales para filtrar y ordenar productos.

  */
}
