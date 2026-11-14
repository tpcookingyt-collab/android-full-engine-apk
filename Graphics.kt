package com.freavity.androidengine.engine.graphics

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.freavity.androidengine.engine.physics.Vec2

class Camera2D(var position: Vec2 = Vec2(), var zoom: Float = 1f) {
    fun worldToScreen(p: Vec2, screenW: Int, screenH: Int): Vec2 =
        Vec2((p.x - position.x) * zoom + screenW / 2f, (p.y - position.y) * zoom + screenH / 2f)

    fun screenToWorld(p: Vec2, screenW: Int, screenH: Int): Vec2 =
        Vec2((p.x - screenW / 2f) / zoom + position.x, (p.y - screenH / 2f) / zoom + position.y)

    fun follow(target: Vec2, lerp: Float = 0.1f) {
        position.x += (target.x - position.x) * lerp
        position.y += (target.y - position.y) * lerp
    }
}

class Sprite(var bitmap: Bitmap? = null, var color: Int = -1, var radius: Float = 24f) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    fun draw(canvas: Canvas, pos: Vec2) {
        val bmp = bitmap
        if (bmp != null) {
            canvas.drawBitmap(bmp, pos.x - bmp.width / 2f, pos.y - bmp.height / 2f, paint)
        } else {
            paint.color = color
            canvas.drawCircle(pos.x, pos.y, radius, paint)
        }
    }
}

class Animation(private val frameCount: Int, private val frameDuration: Float) {
    private var timer = 0f
    var currentFrame: Int = 0
        private set

    fun update(dt: Float) {
        timer += dt
        if (timer >= frameDuration) {
            timer = 0f
            currentFrame = (currentFrame + 1) % frameCount
        }
    }
    fun reset() { timer = 0f; currentFrame = 0 }
}
