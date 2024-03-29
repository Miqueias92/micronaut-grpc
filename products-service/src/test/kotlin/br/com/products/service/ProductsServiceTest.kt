package br.com.products.service
import br.com.products.domain.Product
import br.com.products.dto.ProductRequest
import br.com.products.dto.ProductResponse
import br.com.products.dto.ProductUpdateRequest
import br.com.products.exception.AlreadyExistsException
import br.com.products.exception.BaseBusinessException
import br.com.products.exception.ProductNotFoundException
import br.com.products.repository.ProductRepository
import br.com.products.service.impl.ProductServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrowsExactly
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.Optional

internal class ProductsServiceTest {
    private val productRepository = Mockito.mock(ProductRepository::class.java)
    private val productService = ProductServiceImpl(productRepository)

    @Test
    fun `when create method is call with valid data a ProductResponse is returned` () {
        val productInput = Product(
            id = null,
            name = "product name",
            price = 10.0,
            quantityInStock = 5
        )

        val productOutput = Product(
            id = 1,
            name = "product name",
            price = 10.0,
            quantityInStock = 5
        )

        val request = ProductRequest(
            name = "product name",
            price = 10.0,
            quantityInStock = 5
        )

        `when`(productRepository.save(productInput))
            .thenReturn(productOutput)

        val response = productService.create(request)

        assertEquals(request.name, response.name)
    }

    @Test
    fun `when create method is call with duplicated product-name throws AlreadyExistsException` () {
        val productInput = Product(
            id = null,
            name = "product name",
            price = 10.0,
            quantityInStock = 5
        )

        val productOutput = Product(
            id = 1,
            name = "product name",
            price = 10.0,
            quantityInStock = 5
        )

        val request = ProductRequest(
            name = "product name",
            price = 10.0,
            quantityInStock = 5
        )

        `when`(productRepository.findByNameIgnoreCase(productInput.name))
            .thenReturn(productOutput)

        assertThrows<AlreadyExistsException> {
            productService.create(request)
        }

        // quando queremos testar exatamente o tipo de exception que debe retornar
        // assertThrowsExactly(BaseBusinessException::class.java) {
        //     productService.create(request)
        // }
    }

    @Test
    fun `when update method is call with duplicated product-name throws AlreadyExistsException` () {
        val productInput = Product(
            id = 1,
            name = "product name",
            price = 10.0,
            quantityInStock = 5
        )

        val productOutput = Product(
            id = 1,
            name = "product name",
            price = 10.0,
            quantityInStock = 5
        )

        val request = ProductUpdateRequest(
            id = 1,
            name = "product name",
            price = 10.0,
            quantityInStock = 5
        )

        `when`(productRepository.findByNameIgnoreCase(productInput.name))
            .thenReturn(productOutput)

        assertThrows<AlreadyExistsException> {
            productService.update(request)
        }
    }

    @Test
    fun `when findById method is call with valid id a ProductResponse is returned` () {
        val productInput = 1L

        val productOutput = Product(
            id = 1,
            name = "product name",
            price = 10.0,
            quantityInStock = 5
        )

        `when`(productRepository.findById(productInput))
            .thenReturn(Optional.of(productOutput))

        val response = productService.findById(productInput)

        assertEquals(productInput, response.id)
        assertEquals(productOutput.name, response.name)
    }

    @Test
    fun `when update method is call with invalid id throws ProductNotFoundException` () {
        val request = ProductUpdateRequest(
            id = 1,
            name = "product name",
            price = 10.0,
            quantityInStock = 5
        )

        `when`(productRepository.findByNameIgnoreCase(request.name))
            .thenReturn(null)

        assertThrows<ProductNotFoundException> {
            productService.update(request)
        }
    }

    @Test
    fun `when update method is call with valid data a ProductResponse is returned` () {
        val productInput = Product(
            id = 1,
            name = "update product",
            price = 20.0,
            quantityInStock = 15
        )

        val productOutput = Product(
            id = 1,
            name = "product name",
            price = 10.0,
            quantityInStock = 5
        )

        val request = ProductUpdateRequest(
            id = 1,
            name = "update product",
            price = 20.0,
            quantityInStock = 15
        )

        `when`(productRepository.findById(productInput.id!!))
            .thenReturn(Optional.of(productOutput))

        `when`(productRepository.update(productInput))
            .thenReturn(productInput)

        val response = productService.update(request)

        assertEquals(request.name, response.name)
        assertEquals(request.price, response.price)
    }

    @Test
    fun `when findById method is call with invalid id throws ProductNotFoundException` () {
        val productInput = 1L
        assertThrows<ProductNotFoundException> {
            productService.findById(productInput)
        }
    }

    @Test
    fun `when delete method is call with valid id the Product is deleted` () {
        val id = 1L

        val productOutput = Product(
            id = 1,
            name = "product name",
            price = 10.0,
            quantityInStock = 5
        )

        `when`(productRepository.findById(id))
            .thenReturn(Optional.of(productOutput))

        assertDoesNotThrow {
            productService.delete(productOutput.id!!)
        }
    }

    @Test
    fun `when delete method is call with invalid id throws ProductNotFoundException` () {
        val id = 1L

        `when`(productRepository.findById(id))
            .thenReturn(Optional.empty())

        assertThrows<ProductNotFoundException> {
            productService.delete(id)
        }
    }

    @Test
    fun `when findAll method is call a list of ProductResponse is returned` () {
        val productsList = listOf(
            Product(
                id = 1,
                name = "update product",
                price = 20.0,
                quantityInStock = 15
            )
        )

        `when`(productRepository.findAll())
            .thenReturn(productsList)

        val response = productService.findAll()

        assertEquals(1, response.size)
        assertEquals(productsList.first().name, response.first().name)
        assertEquals(productsList.first().price, response.first().price)
    }

    @Test
    fun `when findAll method is call without Products a emptyList is returned` () {
        val productListSize = 0

        `when`(productRepository.findAll())
            .thenReturn(emptyList())

        val response = productService.findAll()

        assertEquals(productListSize, response.size)
    }
}
