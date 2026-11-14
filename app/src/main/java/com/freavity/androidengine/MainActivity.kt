package com.freavity.androidengine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.freavity.androidengine.engine.core.GameEngine
import com.freavity.androidengine.engine.core.GameView
import com.freavity.androidengine.game.DemoScene

class MainActivity : AppCompatActivity() {

    private lateinit var gameView: GameView
    private lateinit var engine: GameEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameView = GameView(this)
        engine = GameEngine(gameView)
        engine.sceneManager.setScene(DemoScene(this))
        setContentView(gameView)
    }

    override fun onResume() {
        super.onResume()
        engine.start()
    }

    override fun onPause() {
        engine.stop()
        super.onPause()
    }
}
