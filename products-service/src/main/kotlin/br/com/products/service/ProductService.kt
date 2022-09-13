package br.com.products.service

import br.com.products.dto.ProductRequest
import br.com.products.dto.ProductResponse
import br.com.products.dto.ProductUpdateRequest

interface ProductService {
    fun create(request: ProductRequest) : ProductResponse
    fun findById(id: Long) : ProductResponse
    fun update(request: ProductUpdateRequest) : ProductResponse
}