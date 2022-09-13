package br.com.products.util

import br.com.products.ProductServiceRequest
import br.com.products.exception.InvalidArgumentException

class ValidationUtil {
    companion object {
        fun validatePayload(payload: ProductServiceRequest?) : ProductServiceRequest {
            payload?.let {
                if (it.name.isNullOrBlank())
                    throw InvalidArgumentException("name")

                if (it.price.isNaN() || it.price < 0)
                    throw InvalidArgumentException("preço")

                return it
            }
            throw InvalidArgumentException("payload")
        }
    }
}