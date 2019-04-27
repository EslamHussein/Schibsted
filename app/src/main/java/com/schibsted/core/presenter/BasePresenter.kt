package com.schibsted.core.presenter

import com.schibsted.core.exception.ErrorHandler
import com.schibsted.core.view.MvpView
import com.schibsted.core.view.ShowErrorView
import java.lang.ref.WeakReference


/**
 * Created Eslam Hussein .
 */
abstract class BasePresenter<View>(private val view: View, private val errorHandler: ErrorHandler) :
    MvpPresenter where View : MvpView, View : ShowErrorView {


    private var viewRef: WeakReference<View>? = null

    fun getView(): View? = viewRef?.get()
    fun getErrorHandler(): ErrorHandler? = errorHandler

    override fun onAttach() {
        viewRef = WeakReference(view)
        errorHandler.attachView(view)

    }


    override fun onResume() {
        // Not mandatory for all views, if views are interested in receiving this event, they should
        // override this method
    }

    override fun onDetach() {
        if (viewRef != null) {
            viewRef?.clear()
            viewRef = null
        }
        errorHandler.detachView()
    }

}