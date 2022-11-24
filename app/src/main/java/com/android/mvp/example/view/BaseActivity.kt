package com.android.mvp.example.view

import android.content.Context
import androidx.viewbinding.ViewBinding
import com.android.mvp.example.contract.BaseContract
import com.android.mvp.example.presenter.BasePresenter


abstract class BaseActivity<P : BasePresenter<*>, VB : ViewBinding> : BaseUiActivity<VB>(),
    BaseContract.View {

    protected val presenter by lazy { createPresenter() }

    override fun init() {
        presenter.attachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showLoading() {
        showUiLoading()
    }

    override fun dismissLoading() {
        dismissUiLoading()
    }

    override fun onFail(ex: Throwable?, code: String?, msg: String?) {
        dismissLoading()
    }

    override fun onError(ex: Throwable?) {
        dismissLoading()
    }

    override fun getContext(): Context = this

    protected abstract fun createPresenter(): P


}