package com.jetbrains.kmpapp

import AuthServiceClient
import GetUserRequest
import android.annotation.SuppressLint
import android.content.Context
import com.liftric.kvault.KVault
import com.squareup.wire.GrpcClient
import com.squareup.wire.GrpcHeaders
import okhttp3.OkHttpClient
import okhttp3.Protocol
import org.koin.core.module.Module
import org.koin.dsl.module

@SuppressLint("StaticFieldLeak")
lateinit var androidContext: Context

actual val platformModule: Module
    get() = module {
        single<Context> { androidContext }
        single<KVault> { KVault(get()) }

        single<GrpcClient> {
            val httpClient = OkHttpClient.Builder()
                .protocols(listOf(Protocol.H2_PRIOR_KNOWLEDGE))
                .addInterceptor { chain ->
                    val token = get<KVault>().string("token")

                    val request = chain.request().newBuilder()
                        .headers(GrpcHeaders.headersOf("Authorization", token ?: ""))
                        .tag(GetUserRequest::class)
                        .build()
                    chain.proceed(request)
                }
                .build()

            GrpcClient.Builder()
                .client(httpClient)
                .minMessageToCompress(Long.MAX_VALUE)
                .baseUrl("http://10.0.2.2:50051")
                .build()
        }

        single<AuthServiceClient> {
            get<GrpcClient>().create(AuthServiceClient::class)
        }
    }