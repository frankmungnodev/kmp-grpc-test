package com.jetbrains.kmpapp

import AuthServiceClient
import com.squareup.wire.GrpcClient

actual fun grpcClient(): GrpcClient {
    TODO("Not yet implemented")
}

actual val authServiceClient: AuthServiceClient
    get() = TODO("Not yet implemented")