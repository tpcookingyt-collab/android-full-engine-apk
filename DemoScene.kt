package com.freavity.androidengine.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.freavity.androidengine.engine.core.Scene
import com.freavity.androidengine.engine.input.InputManager
import com.freavity.androidengine.engine.physics.PhysicsBody
import com.freavity.androidengine.engine.physics.PhysicsWorld
import com.freavity.androidengine.engine.physics.Vec2
import com.freavity.androidengine.engine.storage.SaveManager
import com.freavity.androidengine.engine.ui.Hud
import kotlin.random.Random

/**
 * Demo scene: tap to spawn a bouncing ball.
 * Exercises PhysicsWorld, rendering, HUD and SaveManager together.
 */
class DemoScene(context: Context) : Scene() {

    private val save = SaveManager(context)
    private val physics = PhysicsWorld()
    private val hud = Hud()
    private val ballPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bgPaint = Paint().apply { color = Color.rgb(14, 20, 32) }
    private var taps = 0
    private var sized = false

    private val colors = intArrayOf(
        Color.rgb(244, 67, 54), Color.rgb(33, 150, 243), Color.rgb(76, 175, 80),
        Color.rgb(255, 193, 7), Color.rgb(156, 39, 176), Color.rgb(0, 188, 212)
    )
    private val bodyColors = mutableMapOf<PhysicsBody, Int>()

    override fun onEnter() {
        taps = save.getInt("demo_taps", 0)
    }

    override fun onExit() {
        save.putInt("demo_taps", taps)
        save.saveHighScore(physics.bodies.size)
    }

    override fun onTouch(input: InputManager) {
        if (!input.justTouched) return
        input.consumeJustTouched()
        spawnBall(input.touchPosition.x, input.touchPosition.y)
    }

    fun spawnBall(x: Float, y: Float) {
        val body = PhysicsBody(
            position = Vec2(x, y),
            velocity = Vec2(Random.nextFloat() * 300f - 150f, -200f),
            radius = 22f + Random.nextFloat() * 20f
        )
        bodyColors[body] = colors[physics.bodies.size % colors.size]
        physics.bodies.add(body)
        taps++
        if (physics.bodies.size > 40) {
            val removed = physics.bodies.removeAt(0)
            bodyColors.remove(removed)
        }
    }

    override fun update(dt: Float) {
        physics.step(dt)
    }

    override fun render(canvas: Canvas) {
        if (!sized) {
            physics.width = canvas.width.toFloat()
            physics.height = canvas.height.toFloat()
            if (physics.bodies.isEmpty()) {
                repeat(6) { spawnBall(canvas.width / 2f + it * 30f, 120f + it * 10f) }
            }
            sized = true
        }
        canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), bgPaint)
        for (body in physics.bodies) {
            ballPaint.color = bodyColors[body] ?: Color.WHITE
            canvas.drawCircle(body.position.x, body.position.y, body.radius, ballPaint)
        }
        hud.drawText(canvas, "Android Full Engine — Demo", 24f, 96f)
        hud.drawSmall(canvas, "Chạm màn hình để thả thêm bóng • Bóng: ${physics.bodies.size} • Chạm: $taps", 24f, 136f)
        hud.drawScore(canvas, save.highScore(), canvas.width)
    }
}
