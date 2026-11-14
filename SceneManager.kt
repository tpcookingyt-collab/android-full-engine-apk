package com.freavity.androidengine.engine.core

class SceneManager {
    var current: Scene? = null
        private set

    private val stack = ArrayDeque<Scene>()

    fun setScene(scene: Scene) {
        current?.onExit()
        stack.clear()
        current = scene
        scene.onEnter()
    }

    fun pushScene(scene: Scene) {
        current?.let { stack.addLast(it) }
        current = scene
        scene.onEnter()
    }

    fun popScene() {
        current?.onExit()
        current = if (stack.isEmpty()) null else stack.removeLast()
        current?.onEnter()
    }
}
