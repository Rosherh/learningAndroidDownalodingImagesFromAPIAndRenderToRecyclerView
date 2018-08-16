package ru.rosherh.flickerimagegallery.core.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import ru.rosherh.flickerimagegallery.R
import ru.rosherh.flickerimagegallery.core.interfaces.view.IBaseView

/**
 * Базовый класс для активности.
 */
abstract class BaseActivity : AppCompatActivity(), IBaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
         * Проверяем наличие разметки, если ее нет, выкидываем исключение.
         */
        if (getLayoutId() == null) throw NullPointerException("Not found layout")

        getLayoutId()?.let {

            /*
             * Надуваем разметку.
             */
            setContentView(it)

            /*
             * Находим FrameLayout, в котором будет вызываться фрагмент.
             */
            val frame = findViewById<FrameLayout>(R.id.frameLayout)
            if (frame != null) {
                createFragment()?.let {
                    val manager = supportFragmentManager
                    manager
                            .beginTransaction()
                            .replace(R.id.frameLayout, it)
                            .commit()
                }
            }
        }
    }

    /**
     * Создаем фрагмент.
     */
    abstract fun createFragment(): Fragment?

}