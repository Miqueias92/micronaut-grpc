package br.com.products.resources

import br.com.products.Empty
import br.com.products.RequestById
import br.com.products.ProductServiceRequest
import br.com.products.ProductServiceUpdateRequest
import br.com.products.ProductsServiceGrpc.ProductsServiceBlockingStub
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

@MicronautTest
internal class ProductResourcesTestIT(
    private val flyway: Flyway,
    private val productsServiceBlockingStub: ProductsServiceBlockingStub
) {

    @BeforeEach
    fun setUp() {
        flyway.clean()
        flyway.migrate()
    }

    @Test
    fun `when ProductsServiceGrpc create method is call with valid data should be return success`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("product name")
            .setPrice(20.10)
            .setQuantityInStock(10)
            .build()

        val response = productsServiceBlockingStub.create(request)

        // for any product add in migrate need to plus id
        assertEquals(3, response.id)
        assertEquals("product name", response.name)
        assertEquals(20.10, response.price)
    }

    @Test
    fun `when ProductsServiceGrpc create method is call with valid id should be return success`() {
        val request = RequestById.newBuilder()
            .setId(1)
            .build()

        val response = productsServiceBlockingStub.findById(request)

        assertEquals(1, response.id)
        assertEquals("Product A", response.name)
    }

    @Test
    fun `when ProductsServiceGrpc create method is call with invalid id should be return ProductNotFoundException`() {
        val request = RequestById.newBuilder()
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

    @Test
    fun `when ProductsServiceGrpc update method is call with valid data should be return success`() {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(1L)
            .setName("update name")
            .setPrice(20.10)
            .setQuantityInStock(10)
            .build()

        val response = productsServiceBlockingStub.update(request)

        assertEquals(1, response.id)
        assertEquals("update name", response.name)
        assertEquals(20.10, response.price)
    }

    @Test
    fun `when ProductsServiceGrpc update method is call with invalid data should be return success`() {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(2L)
            .setName("update name")
            .setPrice(20.10)
            .setQuantityInStock(10)
            .build()

        val response = productsServiceBlockingStub.update(request)

        assertEquals(2, response.id)
        assertEquals("update name", response.name)
        assertEquals(20.10, response.price)
    }

    @Test
    fun `when ProductsServiceGrpc update method is call with invalid data a AlreadyExistsException is returned`() {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(1L)
            .setName("Product A")
            .setQuantityInStock(10)
            .build()

        val description = "Produto [ ${request.name} ] já cadastrado no sistema"

        val response = assertThrows<StatusRuntimeException> {
            productsServiceBlockingStub.update(request)
        }

        assertEquals(Status.ALREADY_EXISTS.code, response.status.code)
        assertEquals(description, response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc update method is call with invalid id should be return ProductNotFoundException`() {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(5L)
            .setName("Product C")
            .setQuantityInStock(10)
            .build()

        val description = "Produto com id [ ${request.id} ] não cadastrado no sistema"

        val response = assertThrows<StatusRuntimeException> {
            productsServiceBlockingStub.update(request)
        }

        assertEquals(Status.NOT_FOUND.code, response.status.code)
        assertEquals(description, response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc delete method is call with valid id should be return success`() {
        val request = RequestById.newBuilder()
            .setId(1L)
            .build()

        assertDoesNotThrow {
            productsServiceBlockingStub.delete(request)
        }
    }

    @Test
    fun `when ProductsServiceGrpc delete method is call with invalid id should be return ProductNotFoundException`() {
        val request = RequestById.newBuilder()
            .setId(4L)
            .build()

        val description = "Produto com id [ ${request.id} ] não cadastrado no sistema"

        val response = assertThrows<StatusRuntimeException> {
            productsServiceBlockingStub.delete(request)
        }

        assertEquals(Status.NOT_FOUND.code, response.status.code)
        assertEquals(description, response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc findAll method is call a List of ProductServiceResponse is returned`() {
        val empty = Empty.newBuilder().build()

        val productList =  productsServiceBlockingStub.findAll(empty)

        assertEquals(2, productList.productsList.size)
        assertEquals("Product A", productList.getProducts(0).name)
        assertEquals("Product B", productList.getProducts(1).name)
    }
}