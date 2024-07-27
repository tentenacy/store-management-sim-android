package com.tenutz.storemngsim.di.qualifier

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class NavigationGraphReference(val value: NavigationGraphs)
