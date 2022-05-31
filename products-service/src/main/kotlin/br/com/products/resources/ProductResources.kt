package br.com.products.resources

import br.com.products.ProductsServiceReply
import br.com.products.ProductsServiceRequest
import br.com.products.ProductsServiceServiceGrpc
import io.grpc.stub.StreamObserver
import io.micronaut.grpc.annotation.GrpcService

@GrpcService
class ProductResources : ProductsServiceServiceGrpc.ProductsServiceServiceImplBase() {
    override fun send(
        request: ProductsServiceRequest?,
        responseObserver: StreamObserver<ProductsServiceReply>?
    ) {
        val toSend = "Hello, ${request?.name}"

        val reply = ProductsServiceReply.newBuilder()
            .setMessage(toSend)
            .build()

        responseObserver?.onNext(reply)
        responseObserver?.onCompleted()
    }

}