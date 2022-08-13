package br.com.products.util

import br.com.products.ProductServiceRequest

class ValidationUtil {
    companion object {
        fun validatePayload(payload: ProductServiceRequest?) : ProductServiceRequest {
            payload?.let {
                if (payload.name.isNullOrBlank())
                    throw IllegalArgumentException("O nome não pode ser nulo ou vazio")

                if (payload.price.isNaN())
                    throw IllegalArgumentException("Preco precisa ser um valor valido")

                return it
            }
            throw IllegalArgumentException()
        }
    }
}