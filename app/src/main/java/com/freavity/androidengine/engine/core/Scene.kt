package com.freavity.androidengine.engine.core

import android.graphics.Canvas
import com.freavity.androidengine.engine.input.InputManager

abstract class Scene {
    open fun onEnter() {}
    open fun onExit() {}
    abstract fun update(dt: Float)
    abstract fun render(canvas: Canvas)
    open fun onTouch(input: InputManager) {}
}
