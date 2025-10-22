package com.example.reusaya.data

/**
 * Clase sellada para definir las rutas de navegación.
 * Las rutas se usan en el NavHost para saber a qué Composable ir.
 */
sealed class AppScreen(val route: String) { // Se cambió el nombre de Screen a AppScreen
    object Login : AppScreen("login")
    object Register : AppScreen("register")
    object Home : AppScreen("home")
    // Se añade el argumento para pasar el ID del producto
    object ProductDetail : AppScreen("product_detail/{productId}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
    }
}

/**
 * Clase de datos que representa un producto.
 */
data class Product(
    val id: Int,
    val nombre: String,
    val precio: String,
    val stock: Int,
    val imageUrl: String,
    val descripcion: String = "Producto de segunda mano en excelente estado, ideal para darle una segunda vida."
)

// Lista de productos de ejemplo para la HomeScreen
val SampleProducts = listOf(
    Product(1, "Polera Vintage", "12.990", 10, "https://placehold.co/150x150/555/FFF?text=T-Shirt"),
    Product(2, "Zapatillas Urban", "29.990", 5, "https://placehold.co/150x150/444/FFF?text=Sneakers"),
    Product(3, "Bolso Tote RY", "14.990", 3, "https://placehold.co/150x150/333/FFF?text=Bag"),
    Product(4, "Chaqueta Denim", "35.000", 1, "https://placehold.co/150x150/222/FFF?text=Jacket"),
    Product(5, "Pantalón Cargo", "18.500", 8, "https://placehold.co/150x150/111/FFF?text=Pants"),
)
