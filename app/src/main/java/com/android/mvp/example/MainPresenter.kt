package com.android.mvp.example

import android.util.Log
import com.android.mvp.example.presenter.BasePresenter
import kotlinx.coroutines.delay

class MainPresenter(view: MainContract.View) : BasePresenter<MainContract.View>(view),
    MainContract.Presenter {

    override fun loadData() {
        requestWithLoading(
            request = {
                delay(3000L)
                arrayOf(1, 2, 3, 4)
            },
            resp = {
                it?.let { res -> view.onDataLoad(res) }
                Log.i("resp", "result=> $it")
            },
            error = {
                Log.i("error", "throwable=> $it")
            }
        )
    }
}