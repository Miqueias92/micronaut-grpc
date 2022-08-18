package br.com.products.service

import br.com.products.dto.ProductRequest
import br.com.products.dto.ProductResponse

interface ProductService {
    fun create(request: ProductRequest) : ProductResponse
    fun findById(id: Long) : ProductResponse
}