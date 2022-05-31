package br.com.products.resources

import br.com.products.ProductServiceResponse
import br.com.products.ProductServiceRequest
import br.com.products.ProductsServiceGrpc
import io.grpc.stub.StreamObserver
import io.micronaut.grpc.annotation.GrpcService

@GrpcService
class ProductResources : ProductsServiceGrpc.ProductsServiceImplBase() {
    override fun create(
        request: ProductServiceRequest?,
        responseObserver: StreamObserver<ProductServiceResponse>?
    ) {
        super.create(request, responseObserver)
    }
}