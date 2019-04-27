package com.schibsted.core.presenter


interface MvpPresenter {

    /**
     * Called when an `View` is attached to this presenter.
     *
     */
    fun onAttach()

    /**
     * Called when the view is resumed according to Android components
     * NOTE: this method will only be called for presenters that override it.
     */
    fun onResume()

    /**
     * Called when an `MvpView` is detached from this presenter.
     */
    fun onDetach()

}
