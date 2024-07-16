package com.tenutz.storemngsim.di.module

import com.tenutz.storemngsim.data.datasource.api.SCKApi
import com.tenutz.storemngsim.data.datasource.paging.repository.ReviewPagingRepository
import com.tenutz.storemngsim.data.datasource.paging.repository.ReviewPagingRepositoryImpl
import com.tenutz.storemngsim.data.datasource.paging.source.mapper.ReviewsMapper
import com.tenutz.storemngsim.data.repository.category.CategoryRepository
import com.tenutz.storemngsim.data.repository.category.CategoryRepositoryImpl
import com.tenutz.storemngsim.data.repository.menu.MenuRepository
import com.tenutz.storemngsim.data.repository.menu.MenuRepositoryImpl
import com.tenutz.storemngsim.data.repository.option.OptionRepository
import com.tenutz.storemngsim.data.repository.option.OptionRepositoryImpl
import com.tenutz.storemngsim.data.repository.optiongroup.OptionGroupRepository
import com.tenutz.storemngsim.data.repository.optiongroup.OptionGroupRepositoryImpl
import com.tenutz.storemngsim.data.repository.store.StoreRepository
import com.tenutz.storemngsim.data.repository.store.StoreRepositoryImpl
import com.tenutz.storemngsim.data.repository.user.UserRepository
import com.tenutz.storemngsim.data.repository.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    companion object {

        @Singleton
        @Provides
        fun provideReviewPagingRepository(
            sckApi: SCKApi,
            mapper: ReviewsMapper,
        ): ReviewPagingRepository {
            return ReviewPagingRepositoryImpl(sckApi, mapper)
        }
    }

    @Binds
    abstract fun provideCategoryRepository(
        repository: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    abstract fun provideMenuRepository(
        repository: MenuRepositoryImpl
    ): MenuRepository

    @Binds
    abstract fun provideOptionRepository(
        repository: OptionRepositoryImpl
    ): OptionRepository

    @Binds
    abstract fun provideOptionGroupRepository(
        repository: OptionGroupRepositoryImpl
    ): OptionGroupRepository

    @Binds
    abstract fun provideStoreRepository(
        repository: StoreRepositoryImpl
    ): StoreRepository

    @Binds
    abstract fun provideUserRepository(
        repository: UserRepositoryImpl
    ): UserRepository
}