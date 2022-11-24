package com.android.mvp.example.contract

import android.content.Context

interface BaseContract {

    interface View {

        fun showLoading()

        fun dismissLoading()

        fun onFail(ex: Throwable?, code: String?, msg: String?)

        fun onError(ex: Throwable?)

        fun getContext() : Context
    }

    interface Presenter {

        fun attachView()

        fun detachView()

    }
}