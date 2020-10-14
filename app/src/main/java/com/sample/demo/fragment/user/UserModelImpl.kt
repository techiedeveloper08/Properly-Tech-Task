package com.sample.demo.fragment.user

import com.sample.demo.model.User

class UserModelImpl(private val userRepo: UserRepo) : Home.Model {

    override fun getUsersData(
        onSuccess: (usersRes: List<User>, numberOfAttempt: Int) -> Unit,
        onFail: (errorMsg: String, numberOfAttempt: Int) -> Unit
    ) {
        userRepo.getUsersData(onSuccess = { usersRes, numberOfAttempt ->
            onSuccess(usersRes, numberOfAttempt)
        }, onError = { error, numberOfAttempt ->
            onFail(error.toString(), numberOfAttempt)
        })
    }
}