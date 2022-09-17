package br.com.products.service.impl

import br.com.products.domain.Product
import br.com.products.domain.toProductResponse
import br.com.products.dto.ProductRequest
import br.com.products.dto.ProductResponse
import br.com.products.dto.ProductUpdateRequest
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

    override fun update(request: ProductUpdateRequest): ProductResponse {
        verifyByName(request.name)

        val product = productRepository.findById(request.id)
            .orElseThrow {
                ProductNotFoundException(request.id)
            }

        val copy = product.copy(
            name = request.name,
            price = request.price,
            quantityInStock = request.quantityInStock
        )
        return productRepository.update(copy).toProductResponse()
    }

    override fun delete(id: Long) {
        val product = productRepository.findById(id)
            .orElseThrow {
                ProductNotFoundException(id)
            }
        productRepository.delete(product)
    }

    private fun verifyByName(name: String) {
        productRepository.findByNameIgnoreCase(name)?.let {
            throw AlreadyExistsException(name)
        }
    }
}