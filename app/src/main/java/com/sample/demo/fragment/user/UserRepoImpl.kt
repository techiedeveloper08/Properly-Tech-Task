package com.sample.demo.fragment.user

import com.sample.demo.api.NetworkApi
import com.sample.demo.database.AppDatabase
import com.sample.demo.model.BaseResponse
import com.sample.demo.model.User
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class UserRepoImpl(private val database: AppDatabase, private val service: NetworkApi) : UserRepo {

    private val compositeDisposable = CompositeDisposable()

    override fun getUsersData(onSuccess: (countryRes: List<User>, numberOfAttempt: Int) -> Unit, onError:(error: Throwable, numberOfAttempt: Int) -> Unit) {
        var numberOfAttempt = 0
        compositeDisposable.add(
                getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    numberOfAttempt++
                    onSuccess(it, numberOfAttempt)
                }, {
                    onError(it, numberOfAttempt)
                }))
    }

    private fun getCountries(): Observable<List<User>> {
        return Observable.concatArrayEager(
                getCountriesFromDB(),
                getCountriesFromServer()
                        .doOnNext { response ->
                            response.body()?.let {
                                //database.userDao().deleteTable()
                                database.userDao().insertAll(it.data)
                            }
                        }
                        .flatMap {
                            getCountriesFromDB()
                        }
                        .subscribeOn(Schedulers.io()))
    }

    private fun getCountriesFromServer(): Observable<Response<BaseResponse<List<User>>>> {
        return service.getUsers()
    }

    private fun getCountriesFromDB(): Observable<List<User>> {
        val countryList = database.userDao().loadCountries()
        return Observable.fromCallable { countryList }
    }

    override fun onCreateUser(user: User, onSuccess: (user: User) -> Unit, onError:(error: Throwable) -> Unit) {
        compositeDisposable.add(
            service.onCreateUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    if(it.body()?.code == 201) {
                        it.body()?.data?.let { resUser ->
                            database.userDao().insert(resUser)
                            onSuccess(resUser)
                        }
                    } else if (it.body()?.code == 422) {
                        onError(Throwable("Data validation failed"))
                    }
                }, {
                    onError(it)
                })
        )
    }

    override fun isDisposed(): Boolean {
        return compositeDisposable.isDisposed
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }
}