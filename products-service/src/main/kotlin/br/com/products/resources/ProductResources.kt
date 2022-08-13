package br.com.products.resources

import br.com.products.ProductServiceResponse
import br.com.products.ProductServiceRequest
import br.com.products.ProductsServiceGrpc
import br.com.products.dto.ProductRequest
import br.com.products.exception.BaseBusinessException
import br.com.products.service.ProductService
import br.com.products.util.ValidationUtil
import io.grpc.Status
import io.grpc.stub.StreamObserver
import io.micronaut.grpc.annotation.GrpcService

@GrpcService
class ProductResources(
    private val productService: ProductService
) : ProductsServiceGrpc.ProductsServiceImplBase() {

    override fun create(
        request: ProductServiceRequest?,
        responseObserver: StreamObserver<ProductServiceResponse>?
    ) {
        try {

            val payload = ValidationUtil.validatePayload(request)
            val productRequest = ProductRequest(
                name = payload.name,
                price = payload.price,
                quantityInStock = payload.quantityInStock
            )
            val productResponse = productService.create(productRequest)

            val response = ProductServiceResponse
                .newBuilder()
                .setId(productResponse.id!!)
                .setName(productResponse.name)
                .setPrice(productResponse.price)
                .setQuantityInStock(productResponse.quantityInStock).build()

            responseObserver?.onNext(response)
            responseObserver?.onCompleted()

        } catch (ex: BaseBusinessException) {
            responseObserver?.onError(
                ex.statusCode().toStatus().withDescription(ex.errorMessage())
                    .asRuntimeException()
            )
        }
    }
}