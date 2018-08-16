package ru.rosherh.flickerimagegallery.core.interfaces.view

import android.support.annotation.LayoutRes

interface IBaseView {

    @LayoutRes
    fun getLayoutId(): Int? = null

}