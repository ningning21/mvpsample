package com.android.mvp.example.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseUiActivity<VB : ViewBinding> : AppCompatActivity() {

    protected val binding: VB by lazy {
        injectViewBinding()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }

    protected open fun connectTenjin() = true

    abstract fun injectViewBinding(): VB

    abstract fun init()

    protected fun showUiLoading() {
    }

    protected fun dismissUiLoading() {
    }
}