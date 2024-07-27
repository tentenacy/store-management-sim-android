package com.tenutz.storemngsim.di.module

import com.tenutz.storemngsim.data.repository.category.CategoryRepository
import com.tenutz.storemngsim.data.repository.sharing.SharedRepository
import com.tenutz.storemngsim.data.repository.store.StoreRepository
import com.tenutz.storemngsim.data.repository.user.UserRepository
import com.tenutz.storemngsim.di.qualifier.NavigationGraphReference
import com.tenutz.storemngsim.di.qualifier.NavigationGraphs
import com.tenutz.storemngsim.usecase.GetMiddleCategoryUseCase
import com.tenutz.storemngsim.usecase.GetProfileUseCase
import com.tenutz.storemngsim.usecase.GetSharedDataUseCase
import com.tenutz.storemngsim.usecase.GetStoreMainUseCase
import com.tenutz.storemngsim.usecase.PutSharedDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    @Provides
    @ActivityRetainedScoped
    fun provideGetMiddleCategoryUseCase(
        categoryRepository: CategoryRepository
    ) = GetMiddleCategoryUseCase(categoryRepository)

    @Provides
    @ActivityRetainedScoped
    fun provideGetStoreMainUseCase(
        storeRepository: StoreRepository
    ) = GetStoreMainUseCase(storeRepository)

    @Provides
    @ActivityRetainedScoped
    fun provideGetProfileUseCase(
        userRepository: UserRepository
    ) = GetProfileUseCase(userRepository)

    @Provides
    @ActivityRetainedScoped
    @NavigationGraphReference(NavigationGraphs.MAIN)
    fun provideGetMainNavSharedDataUseCase(
        @NavigationGraphReference(NavigationGraphs.MAIN) sharedRepository: SharedRepository
    ) = GetSharedDataUseCase(
        sharedRepository = sharedRepository
    )

    @Provides
    @ActivityRetainedScoped
    @NavigationGraphReference(NavigationGraphs.MAIN)
    fun providePutMainNavSharedDataUseCase(
        @NavigationGraphReference(NavigationGraphs.MAIN) sharedRepository: SharedRepository
    ) = PutSharedDataUseCase(
        sharedRepository = sharedRepository
    )

    /*@Provides
    @ActivityRetainedScoped
    @NavigationGraphReference(NavigationGraphs.SUB_CATEGORY)
    fun provideGetSubCategoryNavSharedDataUseCase(
        @NavigationGraphReference(NavigationGraphs.SUB_CATEGORY) sharedRepository: SharedRepository
    ) = GetSharedDataUseCase(
        sharedRepository = sharedRepository
    )*/
}