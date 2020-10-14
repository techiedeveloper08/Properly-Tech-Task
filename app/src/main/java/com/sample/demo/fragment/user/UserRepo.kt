package com.sample.demo.fragment.user

import com.sample.demo.model.User
import io.reactivex.disposables.Disposable

interface UserRepo: Disposable {
    fun getUsersData(onSuccess: (usersRes: List<User>, numberOfAttempt: Int) -> Unit, onError:(error: Throwable, numberOfAttempt: Int) -> Unit)

    fun onCreateUser(user: User, onSuccess: (user: User) -> Unit, onError:(error: Throwable) -> Unit)
}