package com.jetbrains.kmpapp

import android.annotation.SuppressLint
import android.content.Context
import com.liftric.kvault.KVault
import org.koin.core.module.Module
import org.koin.dsl.module

@SuppressLint("StaticFieldLeak")
lateinit var androidContext: Context

actual val platformModule: Module
    get() = module {
        single<Context> { androidContext }
        single<KVault> { KVault(get()) }
    }