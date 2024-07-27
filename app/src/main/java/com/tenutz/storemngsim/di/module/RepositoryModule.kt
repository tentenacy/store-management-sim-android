package com.tenutz.storemngsim.di.module

import com.tenutz.storemngsim.data.datasource.api.SMSApi
import com.tenutz.storemngsim.data.datasource.paging.repository.PushAlarmPagingRepository
import com.tenutz.storemngsim.data.datasource.paging.repository.PushAlarmPagingRepositoryImpl
import com.tenutz.storemngsim.data.datasource.paging.repository.ReviewPagingRepository
import com.tenutz.storemngsim.data.datasource.paging.repository.ReviewPagingRepositoryImpl
import com.tenutz.storemngsim.data.datasource.paging.repository.SalesPagingRepository
import com.tenutz.storemngsim.data.datasource.paging.repository.SalesPagingRepositoryImpl
import com.tenutz.storemngsim.data.datasource.paging.source.mapper.PushAlarmsMapper
import com.tenutz.storemngsim.data.datasource.paging.source.mapper.ReviewsMapper
import com.tenutz.storemngsim.data.datasource.paging.source.mapper.SalesMapper
import com.tenutz.storemngsim.data.repository.category.CategoryRepository
import com.tenutz.storemngsim.data.repository.category.CategoryRepositoryImpl
import com.tenutz.storemngsim.data.repository.help.HelpRepository
import com.tenutz.storemngsim.data.repository.help.HelpRepositoryImpl
import com.tenutz.storemngsim.data.repository.menu.MenuRepository
import com.tenutz.storemngsim.data.repository.menu.MenuRepositoryImpl
import com.tenutz.storemngsim.data.repository.option.OptionRepository
import com.tenutz.storemngsim.data.repository.option.OptionRepositoryImpl
import com.tenutz.storemngsim.data.repository.optiongroup.OptionGroupRepository
import com.tenutz.storemngsim.data.repository.optiongroup.OptionGroupRepositoryImpl
import com.tenutz.storemngsim.data.repository.sharing.SharedRepository
import com.tenutz.storemngsim.data.repository.sharing.SharedRepositoryImpl
import com.tenutz.storemngsim.data.repository.store.StoreRepository
import com.tenutz.storemngsim.data.repository.store.StoreRepositoryImpl
import com.tenutz.storemngsim.data.repository.terms.TermsRepository
import com.tenutz.storemngsim.data.repository.terms.TermsRepositoryImpl
import com.tenutz.storemngsim.data.repository.user.UserRepository
import com.tenutz.storemngsim.data.repository.user.UserRepositoryImpl
import com.tenutz.storemngsim.di.qualifier.NavigationGraphReference
import com.tenutz.storemngsim.di.qualifier.NavigationGraphs
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
            SMSApi: SMSApi,
            mapper: ReviewsMapper,
        ): ReviewPagingRepository {
            return ReviewPagingRepositoryImpl(SMSApi, mapper)
        }

        @Singleton
        @Provides
        fun provideSalesPagingRepository(
            SMSApi: SMSApi,
            mapper: SalesMapper,
        ): SalesPagingRepository {
            return SalesPagingRepositoryImpl(SMSApi, mapper)
        }

        @Singleton
        @Provides
        fun providePushAlarmPagingRepository(
            SMSApi: SMSApi,
            mapper: PushAlarmsMapper,
        ): PushAlarmPagingRepository {
            return PushAlarmPagingRepositoryImpl(SMSApi, mapper)
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

    @Binds
    abstract fun provideHelpRepository(
        repository: HelpRepositoryImpl
    ): HelpRepository

    @Binds
    abstract fun provideTermsRepository(
        repository: TermsRepositoryImpl
    ): TermsRepository

    @Binds
    @Singleton
    @NavigationGraphReference(NavigationGraphs.MAIN)
    abstract fun provideSharedRepository(
        repository: SharedRepositoryImpl
    ): SharedRepository

    /*@Binds
    @Singleton
    @NavigationGraphReference(NavigationGraphs.SUB_CATEGORY)
    abstract fun provideSubCategoryNavSharedRepository(
        repository: SharedRepositoryImpl
    ): SharedRepository*/
}