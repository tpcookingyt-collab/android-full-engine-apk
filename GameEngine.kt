package com.freavity.androidengine.engine.core

import android.graphics.Canvas
import android.view.SurfaceHolder

/** Game loop: update + render on a dedicated thread, target 60 FPS. */
class GameEngine(private val view: GameView) : Runnable {

    val sceneManager = SceneManager()
    private var thread: Thread? = null

    @Volatile
    var running: Boolean = false
        private set

    var fps: Int = 0
        private set

    init {
        view.engine = this
    }

    fun start() {
        if (running) return
        running = true
        thread = Thread(this, "GameEngine").also { it.start() }
    }

    fun stop() {
        running = false
        try {
            thread?.join()
        } catch (_: InterruptedException) {
        }
        thread = null
    }

    override fun run() {
        val holder: SurfaceHolder = view.holder
        var last = System.nanoTime()
        var frames = 0
        var fpsTimer = 0L

        while (running) {
            val now = System.nanoTime()
            var dt = (now - last) / 1_000_000_000f
            last = now
            if (dt > 0.05f) dt = 0.05f // clamp tab-switch spikes

            frames++
            fpsTimer += (dt * 1000).toLong()
            if (fpsTimer >= 1000) {
                fps = frames
                frames = 0
                fpsTimer = 0
            }

            sceneManager.current?.update(dt)

            if (holder.surface.isValid) {
                var canvas: Canvas? = null
                try {
                    canvas = holder.lockCanvas()
                    if (canvas != null) {
                        synchronized(holder) {
                            sceneManager.current?.render(canvas)
                        }
                    }
                } finally {
                    if (canvas != null) {
                        holder.unlockCanvasAndPost(canvas)
                    }
                }
            }
        }
    }
}
