package br.com.products.service
import br.com.products.domain.Product
import br.com.products.dto.ProductRequest
import br.com.products.repository.ProductRepository
import br.com.products.service.impl.ProductServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

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
}
