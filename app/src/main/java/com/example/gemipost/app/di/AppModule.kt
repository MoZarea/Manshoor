package com.example.gemipost.app.di

import org.kodein.di.DI


val appModuleK = DI {
    import(remoteDataSourceModuleK)
    import(repositoryModuleK)
    import(screenModelModuleK)
}
