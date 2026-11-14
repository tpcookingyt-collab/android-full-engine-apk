package com.freavity.androidengine.engine.physics

import kotlin.math.sqrt

data class Vec2(var x: Float = 0f, var y: Float = 0f) {
    operator fun plus(o: Vec2) = Vec2(x + o.x, y + o.y)
    operator fun minus(o: Vec2) = Vec2(x - o.x, y - o.y)
    operator fun times(s: Float) = Vec2(x * s, y * s)
    fun length(): Float = sqrt(x * x + y * y)
    fun normalized(): Vec2 {
        val len = length()
        return if (len == 0f) Vec2(0f, 0f) else Vec2(x / len, y / len)
    }
    fun distanceTo(o: Vec2): Float = (this - o).length()
    fun set(nx: Float, ny: Float) { x = nx; y = ny }
}
