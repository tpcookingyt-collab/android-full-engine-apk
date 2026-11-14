package com.freavity.androidengine.engine.input

import android.view.MotionEvent
import com.freavity.androidengine.engine.physics.Vec2

class InputManager {
    var isTouching: Boolean = false
        private set
    var touchPosition = Vec2()
        private set
    var lastTapTime: Long = 0L
        private set
    var justTouched: Boolean = false
        private set

    fun consumeJustTouched() { justTouched = false }

    private val activePointers = mutableMapOf<Int, Vec2>()
    fun pointers(): Map<Int, Vec2> = activePointers.toMap()

    fun onTouch(event: MotionEvent, viewWidth: Int, viewHeight: Int) {
        val idx = event.actionIndex
        val id = event.getPointerId(idx)
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                isTouching = true
                justTouched = true
                lastTapTime = System.currentTimeMillis()
                val p = Vec2(event.getX(idx), event.getY(idx))
                activePointers[id] = p
                touchPosition = p
            }
            MotionEvent.ACTION_MOVE -> {
                for (i in 0 until event.pointerCount) {
                    val pid = event.getPointerId(i)
                    activePointers[pid] = Vec2(event.getX(i), event.getY(i))
                }
                if (event.pointerCount > 0) touchPosition = Vec2(event.getX(0), event.getY(0))
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> {
                activePointers.remove(id)
                isTouching = activePointers.isNotEmpty()
            }
        }
    }
}
