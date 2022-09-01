package br.com.products.exception

import io.grpc.Status

class ProductNotFoundException(private val productId: Long) : BaseBusinessException() {

    override fun errorMessage(): String {
        return "Produto com id [ $productId ] já cadastrado no sistema"
    }

    override fun statusCode(): Status.Code {
        return Status.Code.NOT_FOUND
    }
}