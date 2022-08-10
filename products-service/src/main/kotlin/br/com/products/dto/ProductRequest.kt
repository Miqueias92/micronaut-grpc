package br.com.products.dto

import br.com.products.domain.Product

data class ProductRequest(
    val name: String,
    val price: Double,
    val quantityInStock: Int
)

fun ProductRequest.toDomain() =
    Product(
        id = null,
        name = name,
        price = price,
        quantityInStock =quantityInStock
    )