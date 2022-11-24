package com.android.mvp.example.presenter


import android.util.Log
import com.android.mvp.example.contract.BaseContract
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.ref.WeakReference


abstract class BasePresenter<T : BaseContract.View>(private val iView: T) :
    BaseContract.Presenter {

    private val jobs = ArrayList<Job>()

    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    private val weakReference by lazy {
        WeakReference(iView)
    }

    override fun attachView() {
    }

    override fun detachView() {
        jobs.forEach {
            if (it.isActive) {
                it.cancel()
            }
        }
        jobs.clear()
        if (scope.isActive) scope.cancel()
        weakReference.clear()
    }

    protected val view: T
        get() = weakReference.get()!!

    protected fun <T> request(
        request: suspend CoroutineScope.() -> T,
        resp: (T?) -> Unit,
        error: ((Throwable) -> Unit)? = null
    ): Job {
        Log.d("CoroutineScope", "Dispatchers start")
        val job = scope.launch {
            flow {
                emit(request())
            }.flowOn(Dispatchers.IO)
                .catch { e ->
                    error?.invoke(e) ?: view?.onError(e)
                    Log.d("CoroutineScope", "Dispatchers catch ==> error $e")
                }
                .collect {
                    resp(it)
                    Log.d("CoroutineScope", "Dispatchers collect ==> result $it")
                }
        }
        jobs.add(job)
        return job
    }

    protected fun <T> requestWithLoading(
        request: suspend CoroutineScope.() -> T,
        resp: (T?) -> Unit,
        error: ((Throwable) -> Unit)? = null
    ): Job {
        Log.d("CoroutineScope", "Dispatchers start")
        val job = scope.launch {
            withContext(Dispatchers.Main) {
                view?.showLoading()
                delay(200)
            }
            flow {
                emit(request())
            }.flowOn(Dispatchers.IO)
                .catch { e ->
                    withContext(Dispatchers.Main) {
                        view?.dismissLoading()
                    }
                    error?.invoke(e) ?: view?.onError(e)
                    Log.d("CoroutineScope", "Dispatchers catch ==> error $e")
                }
                .collect {
                    resp(it)
                    withContext(Dispatchers.Main) {
                        view?.dismissLoading()
                    }
                    Log.d("CoroutineScope", "Dispatchers collect ==> result $it")
                }
        }
        jobs.add(job)
        return job
    }
}