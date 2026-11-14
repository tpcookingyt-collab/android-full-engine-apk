package com.freavity.androidengine.engine.physics

import kotlin.math.abs

data class AABB(var center: Vec2, var halfWidth: Float, var halfHeight: Float) {
    fun intersects(o: AABB): Boolean =
        abs(center.x - o.center.x) <= halfWidth + o.halfWidth &&
            abs(center.y - o.center.y) <= halfHeight + o.halfHeight
}

data class Circle(var center: Vec2, var radius: Float) {
    fun intersects(o: Circle): Boolean =
        center.distanceTo(o.center) <= radius + o.radius

    fun intersects(box: AABB): Boolean {
        val closestX = center.x.coerceIn(box.center.x - box.halfWidth, box.center.x + box.halfWidth)
        val closestY = center.y.coerceIn(box.center.y - box.halfHeight, box.center.y + box.halfHeight)
        return center.distanceTo(Vec2(closestX, closestY)) <= radius
    }
}

class PhysicsBody(
    var position: Vec2,
    var velocity: Vec2 = Vec2(),
    var radius: Float = 24f,
    var restitution: Float = 0.9f,
    var useGravity: Boolean = true
)

class PhysicsWorld(
    var gravity: Float = 900f,
    var width: Float = 1000f,
    var height: Float = 600f
) {
    val bodies = mutableListOf<PhysicsBody>()

    fun step(dt: Float) {
        for (b in bodies) {
            if (b.useGravity) b.velocity.y += gravity * dt
            b.position.x += b.velocity.x * dt
            b.position.y += b.velocity.y * dt

            // Bounce off world bounds
            if (b.position.x < b.radius) { b.position.x = b.radius; b.velocity.x = -b.velocity.x * b.restitution }
            if (b.position.x > width - b.radius) { b.position.x = width - b.radius; b.velocity.x = -b.velocity.x * b.restitution }
            if (b.position.y < b.radius) { b.position.y = b.radius; b.velocity.y = -b.velocity.y * b.restitution }
            if (b.position.y > height - b.radius) {
                b.position.y = height - b.radius
                b.velocity.y = -b.velocity.y * b.restitution
                b.velocity.x *= 0.99f // ground friction
            }
        }
        // Circle-circle elastic-ish collision
        for (i in bodies.indices) {
            for (j in i + 1 until bodies.size) {
                val a = bodies[i]; val b = bodies[j]
                val dist = a.position.distanceTo(b.position)
                val minDist = a.radius + b.radius
                if (dist > 0f && dist < minDist) {
                    val n = Vec2((b.position.x - a.position.x) / dist, (b.position.y - a.position.y) / dist)
                    val overlap = (minDist - dist) / 2f
                    a.position.x -= n.x * overlap; a.position.y -= n.y * overlap
                    b.position.x += n.x * overlap; b.position.y += n.y * overlap
                    val va = a.velocity; val vb = b.velocity
                    a.velocity = vb; b.velocity = va
                }
            }
        }
    }
}
