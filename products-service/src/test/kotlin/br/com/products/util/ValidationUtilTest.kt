package br.com.products.util

import br.com.products.ProductServiceRequest
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
    fun `when validatePayload method is call with invalid product name, should throw exception` () {
        val request = ProductServiceRequest.newBuilder()
            .setName("")
            .setPrice(20.10)
            .setQuantityInStock(10)
            .build()

        Assertions.assertThrowsExactly(IllegalArgumentException::class.java) {
            ValidationUtil.validatePayload(request)
        }
    }

    @Test
    fun `when validatePayload method is call with invalid product price, should throw exception` () {
        val request = ProductServiceRequest.newBuilder()
            .setName("product name")
            .setPrice(-1.0)
            .setQuantityInStock(10)
            .build()

        Assertions.assertThrowsExactly(IllegalArgumentException::class.java) {
            ValidationUtil.validatePayload(request)
        }
    }

    @Test
    fun `when validatePayload method is call with null payload, should throw exception` () {
        Assertions.assertThrowsExactly(IllegalArgumentException::class.java) {
            ValidationUtil.validatePayload(null)
        }
    }
}