package br.com.products.service.impl

import br.com.products.domain.Product
import br.com.products.dto.ProductRequest
import br.com.products.dto.ProductResponse
import br.com.products.exception.AlreadyExistsException
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

    private fun verifyByName(name: String) {
        productRepository.findByNameIgnoreCase(name)?.let {
            throw AlreadyExistsException(name)
        }
    }
}