package ru.rosherh.flickerimagegallery.core.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.rosherh.flickerimagegallery.core.interfaces.view.IBaseView

open class BaseFragment: Fragment(), IBaseView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(getLayoutId()!!, container, false)!!

}