package com.schibsted.core.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.schibsted.core.presenter.MvpPresenter

abstract class MvpActivity< P : MvpPresenter> : AppCompatActivity() {

    abstract val presenter: P
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onAttach()

    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }
}