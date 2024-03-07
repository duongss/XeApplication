package com.dss.xeapplication.feature.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.SurfaceView

class CustomSurface(context: Context, attrs: AttributeSet) : SurfaceView(context, attrs) {
    private var mScaleDetector: ScaleGestureDetector? = null

    init {
        mScaleDetector = ScaleGestureDetector(context, ScaleListener())
    }

    var surfaceListener: SurfaceCall? = null

    fun setListener(surfaceCall: SurfaceCall) {
        surfaceListener = surfaceCall
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mScaleDetector?.onTouchEvent(event)
        return true
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        private var mScaleFactor = 1.0f
        private var minScale = 0f
        private var maxScale = 100f
        private var currentScale = 0f
        private var speedScale = 10f

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            mScaleFactor *=
                Math.min((detector.scaleFactor).coerceAtLeast(minScale), maxScale)
            if (mScaleFactor <1f){
                mScaleFactor = 1f
            }
            currentScale = mScaleFactor

            if (currentScale > mScaleFactor) {
                surfaceListener?.scaleZoom(mScaleFactor / speedScale)
            } else {
                surfaceListener?.scaleZoom(mScaleFactor * speedScale)
            }

            return true
        }
    }

    interface SurfaceCall {
        fun scaleZoom(z: Float)
    }
}