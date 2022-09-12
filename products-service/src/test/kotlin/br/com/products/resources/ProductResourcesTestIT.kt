package br.com.products.resources

import br.com.products.FindByIdServiceRequest
import br.com.products.ProductServiceRequest
import br.com.products.ProductsServiceGrpc.ProductsServiceBlockingStub
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@MicronautTest
internal class ProductResourcesTestIT(
    private val productsServiceBlockingStub: ProductsServiceBlockingStub
) {
    @Test
    fun `when ProductsServiceGrpc create method is call with valid should be return success`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("product name")
            .setPrice(20.10)
            .setQuantityInStock(10)
            .build()

        val response = productsServiceBlockingStub.create(request)

        assertEquals(2, response.id)
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

    @Test
    fun `when ProductsServiceGrpc create method is call with invalid id should be return ProductNotFoundException`() {
        val request = FindByIdServiceRequest.newBuilder()
            .setId(10)
            .build()

        val description = "Produto com id [ ${request.id} ] não cadastrado no sistema"

        val response = assertThrows<StatusRuntimeException> {
            productsServiceBlockingStub.findById(request)
        }

        assertEquals(Status.NOT_FOUND.code, response.status.code)
        assertEquals(description, response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc create method is call with invalid data a AlreadyExistsException is returned`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("Product A")
            .setQuantityInStock(10)
            .build()

        val description = "Produto [ ${request.name} ] já cadastrado no sistema"

        val response = assertThrows<StatusRuntimeException> {
            productsServiceBlockingStub.create(request)
        }

        assertEquals(Status.ALREADY_EXISTS.code, response.status.code)
        assertEquals(description, response.status.description)
    }
}