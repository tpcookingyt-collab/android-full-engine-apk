package com.freavity.androidengine.engine.core

import android.content.Context
import android.view.MotionEvent
import android.view.SurfaceView
import com.freavity.androidengine.engine.input.InputManager

/** Surface the engine draws on; forwards touch input to InputManager. */
class GameView(context: Context) : SurfaceView(context) {

    var engine: GameEngine? = null
    val input = InputManager()

    init {
        isFocusable = true
        isFocusableInTouchMode = true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        input.onTouch(event, width.coerceAtLeast(1), height.coerceAtLeast(1))
        engine?.sceneManager?.current?.onTouch(input)
        performClick()
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}
