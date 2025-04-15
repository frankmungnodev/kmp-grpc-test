package com.jetbrains.kmpapp

import AuthServiceClient
import com.squareup.wire.GrpcClient
import okhttp3.OkHttpClient
import okhttp3.Protocol

private val httpClient = OkHttpClient.Builder()
    .protocols(listOf(Protocol.H2_PRIOR_KNOWLEDGE))
    .build()

private val grpcClientInstance = GrpcClient.Builder()
    .client(httpClient)
    .minMessageToCompress(Long.MAX_VALUE)
    .baseUrl("http://10.0.2.2:50051")
    .build()

actual fun grpcClient(): GrpcClient = grpcClientInstance

actual val authServiceClient: AuthServiceClient = grpcClientInstance.create(AuthServiceClient::class)