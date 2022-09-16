package br.com.products.util

import br.com.products.ProductServiceRequest
import br.com.products.ProductServiceUpdateRequest
import br.com.products.exception.InvalidArgumentException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

internal class ValidationUtilTest {

    @Test
    fun `when validatePayload method is call with valid data, should not throw exception` () {
        val request = ProductServiceRequest.newBuilder()
            .setName("product name")
            .setPrice(20.10)
            .setQuantityInStock(10)
            .build()

        assertDoesNotThrow {
            ValidationUtil.validatePayload(request)
        }
    }

    @Test
    fun `when validateUpdatePayload method is call with valid data, should not throw exception` () {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(1L)
            .setName("product name")
            .setPrice(20.10)
            .setQuantityInStock(10)
            .build()

        assertDoesNotThrow {
            ValidationUtil.validateUpdatePayload(request)
        }
    }

    @Test
    fun `when validatePayload method is call with invalid product name, should throw exception` () {
        val request = ProductServiceRequest.newBuilder()
            .setName("")
            .setPrice(20.10)
            .setQuantityInStock(10)
            .build()

        Assertions.assertThrowsExactly(InvalidArgumentException::class.java) {
            ValidationUtil.validatePayload(request)
        }
    }

    @Test
    fun `when validateUpdatePayload method is call with invalid product name, should throw exception` () {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(1L)
            .setName("")
            .setPrice(20.10)
            .setQuantityInStock(10)
            .build()

        Assertions.assertThrowsExactly(InvalidArgumentException::class.java) {
            ValidationUtil.validateUpdatePayload(request)
        }
    }

    @Test
    fun `when validatePayload method is call with invalid product price, should throw exception` () {
        val request = ProductServiceRequest.newBuilder()
            .setName("product name")
            .setPrice(-1.0)
            .setQuantityInStock(10)
            .build()

        Assertions.assertThrowsExactly(InvalidArgumentException::class.java) {
            ValidationUtil.validatePayload(request)
        }
    }

    @Test
    fun `when validateUpdatePayload method is call with invalid product price, should throw exception` () {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(1L)
            .setName("product name")
            .setPrice(-1.0)
            .setQuantityInStock(10)
            .build()

        Assertions.assertThrowsExactly(InvalidArgumentException::class.java) {
            ValidationUtil.validateUpdatePayload(request)
        }
    }

    @Test
    fun `when validatePayload method is call with null payload, should throw exception` () {
        Assertions.assertThrowsExactly(InvalidArgumentException::class.java) {
            ValidationUtil.validatePayload(null)
        }
    }

    @Test
    fun `when validateUpdatePayload method is call with null payload, should throw exception` () {
        Assertions.assertThrowsExactly(InvalidArgumentException::class.java) {
            ValidationUtil.validateUpdatePayload(null)
        }
    }
}