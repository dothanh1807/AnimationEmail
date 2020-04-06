package com.vllenin.animationemail

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TIME_LEFT_TO_RIGHT = 1000
        private const val TIME_FLIP = 200
        private const val TIME_FADE_IN_BOTTOM = 500
        private const val TIME_SCALE = 400
        private const val TIME_PUSH_MAIL = 100
        private const val TIME_DELAY = 100
        private const val TIME_PUSH_FLIP = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email.visibility = View.INVISIBLE

        button.setOnClickListener {
            email.visibility = View.VISIBLE

            startAnimationImg()

        }
    }

    private fun startAnimationImg() {
        animationLeftToRight(email).setAnimationListener(object : Animation.AnimationListener {/******** 1 ****************/
        override fun onAnimationStart(animation: Animation) {

        }

            override fun onAnimationEnd(animation: Animation) {/******** 2 ****************/
            val animatorSet = AnimatorSet()

                val animFlipX = ObjectAnimator.ofFloat(flip, "rotationX", 0f, 180f)
                animFlipX.duration = TIME_FLIP.toLong()
                animFlipX.interpolator = AccelerateDecelerateInterpolator()

                val animFlipY = ObjectAnimator.ofFloat(flip, "translationY", 0.0f, -flip.height + 40f)
                animFlipY.duration = TIME_FLIP.toLong()
                animFlipY.interpolator = AccelerateDecelerateInterpolator()

                animatorSet.playTogether(animFlipX, animFlipY)
                animatorSet.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {

                    }

                    override fun onAnimationEnd(animation: Animator) {/******** 3 ******************/
                    val flipY = ObjectAnimator.ofFloat(flip, "translationY",
                            -flip.height + 40f, -flip.height + 40f)
                        flipY.duration = TIME_PUSH_FLIP.toLong()
                        flipY.start()
                        val hideFilp = ObjectAnimator.ofFloat(flip, "alpha", 0.25f, 0f)
                        hideFilp.interpolator = AccelerateDecelerateInterpolator()
                        hideFilp.duration = TIME_PUSH_FLIP.toLong()
                        hideFilp.start()

                        val animSet = AnimatorSet()

                        backface.visibility = View.VISIBLE
                        val animMailFront = ObjectAnimator.ofFloat(mailFront, "translationY",
                            -flip.height / 2f + 50f, -flip.height / 2f + 50f)
                        val animMailBack = ObjectAnimator.ofFloat(mailBack, "translationY",
                            -flip.height / 2f - 10f, -flip.height / 2f - 20f)
                        animMailBack.duration = TIME_PUSH_MAIL.toLong()
                        animSet.playTogether(animMailFront, animMailBack)
                        animSet.start()
                        animSet.addListener(object : Animator.AnimatorListener {
                            override fun onAnimationStart(animation: Animator) {

                            }

                            override fun onAnimationEnd(animation: Animator) {
                                Handler().postDelayed({
                                    animationFadeIntoBottom(backface)
                                    animationFadeIntoBottom(flip_back)
                                    animationFadeIntoBottom(front)
                                    animationFadeIntoBottom(bottom)
                                    animationFadeIntoBottom(flip).setAnimationListener(object : Animation.AnimationListener {
                                        override fun onAnimationStart(animation: Animation) {

                                        }

                                        override fun onAnimationEnd(animation: Animation) {/******** 4 *****************/
                                            animationScaleBack(mailBack)
                                            animationScaleFront(mailFront)
                                            Handler().postDelayed({
                                                contentBackMail.visibility = View.VISIBLE
                                                contentFrontMail.visibility = View.VISIBLE
                                                animationContent(contentBackMail)
                                                animationContent(contentFrontMail)
                                            }, (TIME_SCALE - 100).toLong())
                                        }

                                        override fun onAnimationRepeat(animation: Animation) {

                                        }
                                    })
                                }, TIME_DELAY.toLong())
                            }

                            override fun onAnimationCancel(animation: Animator) {

                            }

                            override fun onAnimationRepeat(animation: Animator) {

                            }
                        })
                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }
                })
                animatorSet.start()
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
    }

    ////////////////////////// Animations ////////////////////////////

    private fun animationLeftToRight(view: View): AnimationSet {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        val animSet = AnimationSet(false)
        animSet.fillAfter = true

        val rotateAnim = RotateAnimation(-30f, 0f,
            Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f)
        rotateAnim.duration = TIME_LEFT_TO_RIGHT.toLong()
        rotateAnim.interpolator = AccelerateDecelerateInterpolator()
        animSet.addAnimation(rotateAnim)

        val scaleAnim = ScaleAnimation(0.4f, 1f, 0.4f, 1f,
            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f)
        scaleAnim.duration = TIME_LEFT_TO_RIGHT.toLong()
        scaleAnim.interpolator = DecelerateInterpolator()
        animSet.addAnimation(scaleAnim)

        val translateAnim = TranslateAnimation(-width / 1.75f, 0f, height / 10f, -5f)
        translateAnim.duration = TIME_LEFT_TO_RIGHT.toLong()
        translateAnim.interpolator = AccelerateDecelerateInterpolator()
        animSet.addAnimation(translateAnim)

        view.startAnimation(animSet)

        val animFlipX = ObjectAnimator.ofFloat(view, "rotationX", 40f, 0f)
        animFlipX.duration = (TIME_LEFT_TO_RIGHT / 2).toLong()
        animFlipX.start()

        return animSet
    }

    fun animationFadeIntoBottom(view: View): AnimationSet {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels

        val animSet = AnimationSet(false)
        animSet.fillAfter = true

        val translateAnim = TranslateAnimation(0f, 0f, 0f, height / 3f)
        translateAnim.duration = TIME_FADE_IN_BOTTOM.toLong()
        animSet.addAnimation(translateAnim)

        val alphaAnimation = AlphaAnimation(1f, 0f)
        alphaAnimation.duration = TIME_FADE_IN_BOTTOM.toLong()
        alphaAnimation.interpolator = AccelerateDecelerateInterpolator()
        animSet.addAnimation(alphaAnimation)

        view.startAnimation(animSet)

        return animSet
    }

    fun animationScaleBack(view: View) {
        val scaleAnimation = ScaleAnimation(1f, 2.5f, 1f, 2.0f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scaleAnimation.fillAfter = true
        scaleAnimation.duration = TIME_SCALE.toLong()
        scaleAnimation.interpolator = AccelerateDecelerateInterpolator()
        view.startAnimation(scaleAnimation)
    }

    fun animationScaleFront(view: View) {
        val scaleAnimation = ScaleAnimation(1f, 2.5f, 1f, 1.25f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scaleAnimation.fillAfter = true
        scaleAnimation.duration = TIME_SCALE.toLong()
        scaleAnimation.interpolator = AccelerateDecelerateInterpolator()
        view.startAnimation(scaleAnimation)
    }

    fun animationContent(view: View) {
        val animationSet = AnimationSet(false)
        animationSet.fillAfter = true

        val scaleAnimation = ScaleAnimation(0.8f, 1f, 0.8f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scaleAnimation.duration = TIME_SCALE.toLong()
        animationSet.addAnimation(scaleAnimation)

        val alpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        alpha.duration = TIME_SCALE.toLong()
        alpha.start()

        animationSet.start()
    }

}
