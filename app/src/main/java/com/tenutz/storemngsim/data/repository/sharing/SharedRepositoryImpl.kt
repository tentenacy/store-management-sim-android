package com.tenutz.storemngsim.data.repository.sharing

import android.os.Parcelable
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.safeCast

class SharedRepositoryImpl @Inject constructor() : SharedRepository {

    private val parcelableMap: HashMap<KClass<*>, Parcelable> = hashMapOf()

    override fun putParcelable(value: Parcelable) {
        parcelableMap[value::class] = value

    }

    override fun <T: Parcelable>getParcelable(key: KClass<T>): T? = key.safeCast(parcelableMap[key])
}