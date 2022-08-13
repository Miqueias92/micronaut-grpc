package br.com.products.util

import br.com.products.ProductServiceRequest

class ValidationUtil {
    companion object {
        fun validatePayload(payload: ProductServiceRequest?) : ProductServiceRequest {
            payload?.let {
                if (it.name.isNullOrBlank())
                    throw IllegalArgumentException("O nome não pode ser nulo ou vazio")

                if (it.price.isNaN() || it.price < 0)
                    throw IllegalArgumentException("Preco precisa ser um valor valido")

                return it
            }
            throw IllegalArgumentException()
        }
    }
}