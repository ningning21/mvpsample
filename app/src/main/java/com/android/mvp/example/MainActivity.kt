package com.android.mvp.example

import android.util.Log
import com.android.mvp.example.databinding.ActivityMainBinding
import com.android.mvp.example.view.BaseActivity

class MainActivity : BaseActivity<MainPresenter, ActivityMainBinding>(), MainContract.View {

    override fun init() {
        super.init()
    }

    override fun onDataLoad(ints: Array<Int>) {
        Log.i("MainActivity onDataLoad", "res = > $ints")
    }

    override fun createPresenter(): MainPresenter = MainPresenter(this)

    override fun injectViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)
}