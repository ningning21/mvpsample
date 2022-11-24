package com.android.mvp.example

import com.android.mvp.example.contract.BaseContract

interface MainContract : BaseContract {

    interface Presenter : BaseContract.Presenter {
        fun loadData()
    }

    interface View : BaseContract.View {
        fun onDataLoad(ints: Array<Int>)
    }

}