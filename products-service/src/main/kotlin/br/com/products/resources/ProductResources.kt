package br.com.products.resources

import br.com.products.ProductServiceResponse
import br.com.products.ProductServiceRequest
import br.com.products.ProductsServiceGrpc
import br.com.products.dto.ProductRequest
import br.com.products.service.ProductService
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
        val productRequest = ProductRequest(
            name = request!!.name,
            price = request.price,
            quantityInStock = request.quantityInStock
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
    }
}