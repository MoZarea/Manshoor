package com.example.gemipost.app.di

import org.koin.dsl.module


val appModule = module {
    includes(remoteDataSourceModuleK,screenModelModuleK,repositoryModuleK)
}
