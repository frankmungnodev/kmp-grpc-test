package com.jetbrains.kmpapp

import AuthServiceClient
import com.squareup.wire.GrpcClient

expect fun grpcClient(): GrpcClient

expect val authServiceClient: AuthServiceClient