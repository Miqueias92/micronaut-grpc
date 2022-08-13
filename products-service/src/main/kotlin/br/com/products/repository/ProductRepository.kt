package br.com.products.repository

import br.com.products.domain.Product
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByNameIgnoreCase(name: String): Product?
}