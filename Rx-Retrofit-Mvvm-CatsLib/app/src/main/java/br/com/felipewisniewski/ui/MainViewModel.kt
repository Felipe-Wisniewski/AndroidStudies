package br.com.felipewisniewski.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import br.com.felipewisniewski.model.CatsRepository
import br.com.felipewisniewski.model.NetCat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    private val serverUrl = "https://api.thecatapi.com/v1/"
    private val apiKey = "5ce1cfb3-4f0d-4801-80f6-a5060c87aefa"

    private val compositeDisposableOnDestroy = CompositeDisposable()
    private var latestCatCall: Disposable? = null

    val bunchOfCats = MutableLiveData<List<NetCat>>()
    val errorMessage = MutableLiveData<String>()

    fun getSomeCats() {

        val catsRepository = CatsRepository(serverUrl, BuildConfig.DEBUG, apiKey)

        latestCatCall?.dispose()

        latestCatCall = catsRepository.getNumberOfRandomCats(10, null)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                compositeDisposableOnDestroy.add(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when {
                    result.hasError() -> result.errorMessage?.let {
                        errorMessage.postValue("Error getting cats $it")
                    }
                        ?: run {
                            errorMessage.postValue("Null list of cats")
                        }

                    result.hasCats() -> result.netCats?.let {
                        bunchOfCats.postValue(it)
                        errorMessage.postValue("")
                    }
                        ?: run {
                            errorMessage.postValue("Null list of cats")
                        }

                    else -> {
                        errorMessage.postValue("No cats available")
                    }
                }
            }
    }

    override fun onCleared() {
        compositeDisposableOnDestroy.clear()
        Log.d("FLMWG","Clearing ViewModel")
        super.onCleared()
    }
}