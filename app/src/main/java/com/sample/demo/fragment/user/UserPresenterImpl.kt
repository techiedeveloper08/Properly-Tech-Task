package com.sample.demo.fragment.user

import com.sample.demo.model.User


class UserPresenterImpl(
    private val model: Home.Model,
    private val controller: Home.Controller
) : Home.Presenter {

    override fun initialize() {
        model.getUsersData(onSuccess = ::onUsersDataSuccess, onFail = ::onUsersDataFail)
    }

    private fun onUsersDataSuccess(usersRes: List<User>, numberOfAttempt: Int) {
        controller.onCountriesDataSuccess(usersRes, numberOfAttempt)
    }

    private fun onUsersDataFail(errorMsg: String, numberOfAttempt: Int) {
        controller.onCountriesDataFail(errorMsg, numberOfAttempt)
    }
}