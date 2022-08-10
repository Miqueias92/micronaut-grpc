package br.com.products.domain

import br.com.products.dto.ProductResponse
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val name: String,
    val price: Double,
    val quantityInStock: Int
)

fun Product.toProductResponse() =
    ProductResponse(
        id = id,
        name = name,
        price = price,
        quantityInStock = quantityInStock
    )