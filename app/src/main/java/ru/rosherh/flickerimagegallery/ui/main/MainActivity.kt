package ru.rosherh.flickerimagegallery.ui.main

import ru.rosherh.flickerimagegallery.R
import ru.rosherh.flickerimagegallery.core.ui.BaseActivity
import ru.rosherh.flickerimagegallery.ui.main.fragments.GalleryFragments

class MainActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_main

    override fun createFragment() = GalleryFragments.getInstance()

}
