package br.com.products.service.impl

import br.com.products.domain.Product
import br.com.products.domain.toProductResponse
import br.com.products.dto.ProductRequest
import br.com.products.dto.ProductResponse
import br.com.products.exception.AlreadyExistsException
import br.com.products.exception.ProductNotFoundException
import br.com.products.repository.ProductRepository
import br.com.products.service.ProductService
import jakarta.inject.Singleton

@Singleton
class ProductServiceImpl(
    private val productRepository: ProductRepository
) : ProductService {
    override fun create(request: ProductRequest): ProductResponse {
        verifyByName(request.name)

        return productRepository.save(
            Product(
                id = null,
                name = request.name,
                price = request.price,
                quantityInStock = request.quantityInStock
            )
        ).let { product ->
            ProductResponse(
                id = product.id,
                name = product.name,
                price = product.price,
                quantityInStock = product.quantityInStock
            )
        }
    }

    override fun findById(id: Long): ProductResponse {
        return productRepository.findById(id)
            .orElseThrow {
                ProductNotFoundException(id)
            }.toProductResponse()
    }

    private fun verifyByName(name: String) {
        productRepository.findByNameIgnoreCase(name)?.let {
            throw AlreadyExistsException(name)
        }
    }
}