package br.com.products.exception

import io.grpc.Status
import java.lang.RuntimeException

abstract class BaseBusinessException : RuntimeException() {
    abstract fun errorMessage(): String
    abstract fun statusCode(): Status.Code
}