package com.jetbrains.kmpapp

import AuthServiceClient
import GetMeRequest
import LoginRequest
import RegisterRequest
import RegisterResponse
import User
import com.liftric.kvault.KVault
import com.squareup.wire.GrpcCall
import com.squareup.wire.GrpcClient
import com.squareup.wire.GrpcMethod
import com.squareup.wire.GrpcStreamingCall
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<KVault> {
            KVault()
        }

        single<GrpcClient>{
            object : GrpcClient() {
                override fun <S : Any, R : Any> newCall(method: GrpcMethod<S, R>): GrpcCall<S, R> {
                    TODO("Not yet implemented")
                }

                override fun <S : Any, R : Any> newStreamingCall(method: GrpcMethod<S, R>): GrpcStreamingCall<S, R> {
                    TODO("Not yet implemented")
                }
            }
        }

        single<AuthServiceClient> {
            object : AuthServiceClient {
                override fun Register(): GrpcCall<RegisterRequest, RegisterResponse> {
                    TODO("Not yet implemented")
                }

                override fun Login(): GrpcCall<LoginRequest, RegisterResponse> {
                    TODO("Not yet implemented")
                }

                override fun GetMe(): GrpcCall<GetMeRequest, User> {
                    TODO("Not yet implemented")
                }
            }
        }
    }