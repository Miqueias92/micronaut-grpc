package br.com.products.dto

data class ProductRequest(
    val name: String,
    val price: Double,
    val quantityInStock: Int
)