package de.zevolution;

import com.badlogic.ashley.core.PooledEngine;

import de.zevolution.physics.systems.PhysicsSystem;

/**
 * Empty class just for holding the most important systems and the engine
 * @author David
 *
 */
public abstract class EntityCreator {
    public static PooledEngine engine;
    public static PhysicsSystem physicsSystem; 
}
