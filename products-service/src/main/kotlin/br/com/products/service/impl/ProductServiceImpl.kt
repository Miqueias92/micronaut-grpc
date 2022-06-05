package br.com.products.service.impl

import br.com.products.dto.ProductRequest
import br.com.products.dto.ProductResponse
import br.com.products.service.ProductService
import jakarta.inject.Singleton

@Singleton
class ProductServiceImpl : ProductService {
    override fun create(request: ProductRequest): ProductResponse {
        TODO("Not yet implemented")
    }
}