package com.sample.demo.fragment.user

import com.sample.demo.model.User

interface Home {

    interface Model {
        fun getUsersData(onSuccess: (usersRes: List<User>, numberOfAttempt: Int) -> Unit, onFail: (errorMsg: String, numberOfAttempt: Int) -> Unit)
    }

    interface Controller {
        fun onShowLoading(showLoading: Boolean)
        fun onCountriesDataSuccess(usersRes: List<User>,numberOfAttempt: Int)
        fun onCountriesDataFail(errorMsg: String, numberOfAttempt: Int)
    }

    interface Presenter {
        fun initialize()
    }
}