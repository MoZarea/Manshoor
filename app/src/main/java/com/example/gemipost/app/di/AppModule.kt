package com.example.gemipost.app.di

import org.kodein.di.DI
import org.koin.dsl.module


val appModule = module {
    includes(remoteDataSourceModuleK,screenModelModuleK,repositoryModuleK)
}
