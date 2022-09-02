package br.com.products.resources

import br.com.products.FindByIdServiceRequest
import br.com.products.ProductServiceRequest
import br.com.products.ProductsServiceGrpc.ProductsServiceBlockingStub
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@MicronautTest
internal class ProductResourcesTestIT(
    private val productsServiceBlockingStub: ProductsServiceBlockingStub
) {
    @Test
    fun `when ProductsServiceGrpc create method is call with valid should be return success`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("product name")
            .setQuantityInStock(10)
            .build()

        val response = productsServiceBlockingStub.create(request)

        assertEquals(1, response.id)
        assertEquals("product name", response.name)
        assertEquals(20.10, response.price)
    }

    @Test
    fun `when ProductsServiceGrpc create method is call with valid id should be return success`() {
        val request = FindByIdServiceRequest.newBuilder()
            .setId(1)
            .build()

        val response = productsServiceBlockingStub.findById(request)

        assertEquals(1, response.id)
        assertEquals("Product A", response.name)
    }
}