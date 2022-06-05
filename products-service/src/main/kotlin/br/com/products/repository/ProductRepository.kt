package br.com.products.repository

import br.com.products.domain.Product
import io.micronaut.data.jpa.repository.JpaRepository
import jakarta.inject.Singleton

@Singleton
interface ProductRepository : JpaRepository<Product, Long>