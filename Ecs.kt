package com.freavity.androidengine.engine.ecs

import com.freavity.androidengine.engine.physics.Vec2

interface Component

data class Transform(var position: Vec2 = Vec2(), var rotation: Float = 0f, var scale: Float = 1f) : Component
data class Velocity(var value: Vec2 = Vec2()) : Component
data class CircleRenderer(var radius: Float = 24f, var color: Int = -1) : Component
data class Health(var current: Int = 100, var max: Int = 100) : Component

class Entity(val id: Int) {
    val components = mutableMapOf<Class<*>, Component>()
    inline fun <reified T : Component> add(c: T): Entity { components[T::class.java] = c; return this }
    inline fun <reified T : Component> get(): T? = components[T::class.java] as? T
    inline fun <reified T : Component> has(): Boolean = components.containsKey(T::class.java)
}

abstract class System {
    abstract fun update(world: World, dt: Float)
}

class MovementSystem : System() {
    override fun update(world: World, dt: Float) {
        for (e in world.entities) {
            val t = e.get<Transform>() ?: continue
            val v = e.get<Velocity>() ?: continue
            t.position.x += v.value.x * dt
            t.position.y += v.value.y * dt
        }
    }
}

class World {
    val entities = mutableListOf<Entity>()
    private val systems = mutableListOf<System>()
    private var nextId = 1

    fun createEntity(): Entity = Entity(nextId++).also { entities.add(it) }
    fun removeEntity(e: Entity) { entities.remove(e) }
    fun addSystem(s: System): World { systems.add(s); return this }
    fun update(dt: Float) { for (s in systems) s.update(this, dt) }
    inline fun <reified T : Component> query(): List<Entity> = entities.filter { it.has<T>() }
}
