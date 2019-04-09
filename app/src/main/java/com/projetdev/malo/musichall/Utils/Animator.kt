package com.projetdev.malo.musichall.Utils

import android.animation.ValueAnimator
import android.support.v7.widget.SearchView
import android.view.animation.LinearInterpolator
import android.widget.ImageView

class Animator {

    companion object {
        fun hideSearchBar(searchBar: SearchView, switchSearchButton: ImageView, speed: Long = 250): Boolean {
            val valueAnimator = ValueAnimator.ofFloat(0f, -200f)
            valueAnimator.addUpdateListener {
                val value = it.animatedValue as Float
                searchBar.translationY = value
                switchSearchButton.translationY = value
            }

            valueAnimator.interpolator = LinearInterpolator()
            valueAnimator.duration = speed

            valueAnimator.start()

            return false
        }

        fun showSearchBar(searchBar: SearchView, switchSearchButton: ImageView, speed: Long = 250): Boolean {
            val valueAnimator = ValueAnimator.ofFloat(-200f, 0f)
            valueAnimator.addUpdateListener {
                val value = it.animatedValue as Float
                searchBar.translationY = value
                switchSearchButton.translationY = value
            }

            valueAnimator.interpolator = LinearInterpolator()
            valueAnimator.duration = speed

            valueAnimator.start()

            return true
        }
    }
}