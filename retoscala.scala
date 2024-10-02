/*Reto
  Sistema de recomendaciones de productos basado en perfiles de usuario
    Simular un sistema básico de recomendación de productos en el que se analicen las compras previas de unos usuarios
    para generar recomendaciones personalizadas.
    Utilizar estructuras de datos avanzadas y patrones funcionales para filtrar y ordenar productos.

*/
object Main {
  case class Producto(id: Int, nombre: String, categoria: String)
  case class Usuario(nombre: String, comprasPrevias: List[Producto])

  // Lista de productos
  val productos = List(
    Producto(7, "Laptop", "Tecnología"),
    Producto(8, "Smartphone", "Tecnología"),
    Producto(14, "Teclado", "Accesorios"),
    Producto(9, "Ratón", "Accesorios"),
    Producto(2, "Monitor", "Tecnología"),
    Producto(3, "Silla Gamer", "Muebles"),
    Producto(4, "Escritorio", "Muebles"),
    Producto(15, "Cafetera", "Electrodomésticos"),
    Producto(1, "Aspiradora", "Electrodomésticos"),
    Producto(13, "Auriculares", "Accesorios"),
    Producto(12, "Impresora", "Oficina"),
    Producto(11, "Tablet", "Tecnología"),
    Producto(5, "Cámara", "Fotografía"),
    Producto(6, "Reloj Inteligente", "Tecnología"),
    Producto(10, "Lámpara", "Decoración")
  )

  // Usuarios
  val carlos = Usuario("Carlos", List(productos(0), productos(1), productos(9)))
  val ana = Usuario("Ana", List(productos(2), productos(4), productos(10)))
  val luis = Usuario("Luis", List(productos(5), productos(6), productos(12)))
  val maria = Usuario("María", List(productos(7), productos(8), productos(14)))
  val sofia = Usuario("Sofía", List(productos(3), productos(11), productos(13)))

  // Lista de usuarios
  val usuarios = List(carlos, ana, luis, maria, sofia)

  // Funcion para pedir recomendaciones
  def recomendarProductos(usuario: Usuario, limit: Int): List[Producto] = {
    val categorias = usuario.comprasPrevias.map(producto => producto.categoria)
    val recomendaciones = productos.filter(producto => categorias.contains(producto.categoria))
    ordenarProductos(recomendaciones.take(limit))
  }

  def ordenarProductos(lista: List[Producto]): List[Producto] = {
    if(lista.length <= 1) lista
    else {
      val pivot = lista(lista.length / 2).id
      ordenarProductos(lista.filter(_.id < pivot)) ++ lista.filter(_.id == pivot) ++ ordenarProductos(lista.filter(_.id > pivot))
    }
  }


  def main(args: Array[String]): Unit = {
    recomendarProductos(carlos, 5).foreach(println)
  }
}
