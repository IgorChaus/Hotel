package com.example.hotel.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import javax.inject.Inject
import javax.inject.Provider

/**
 * ViewModelFactory class for creating ViewModel instances with dependency injection support.
 * @property creators The map of ViewModel classes and their corresponding providers.
 */
class ViewModelFactory @Inject constructor(
    private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            error("Unknown model class: $modelClass")
        }
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            error(e)
        }
    }
}

@Module
interface ViewModelBuilderModule {
    @Binds
    @ApplicationScope
    fun bindViewModelFactory(
        factory: ViewModelFactory,
    ): ViewModelProvider.Factory
}