package de.zevolution;

import com.badlogic.ashley.core.ComponentMapper;

import de.zevolution.input.InputComponent;
import de.zevolution.menu.MenuButtonComponent;
import de.zevolution.movement.MovementComponent;
import de.zevolution.physics.components.PhysicsBodyComponent;
import de.zevolution.physics.components.PhysicsModifierComponent;
import de.zevolution.physics.components.PositionComponent;

/**
 * Used to map components to their entities. Always use this to retrieve a
 * Component, using the entity.getComponent(Component.class) runs in O(log n), the
 * ComponentMapper in O(1)
 * 
 * @author David
 *
 */
public class CompMappers {
    public static final ComponentMapper<PhysicsModifierComponent> physicsModifier = ComponentMapper
            .getFor(PhysicsModifierComponent.class);
    public static final ComponentMapper<PhysicsBodyComponent> physicsBody = ComponentMapper
            .getFor(PhysicsBodyComponent.class);
    public static final ComponentMapper<PositionComponent> position = ComponentMapper
            .getFor(PositionComponent.class);
    public static final ComponentMapper<MenuButtonComponent> menuButton = ComponentMapper
            .getFor(MenuButtonComponent.class);
    public static final ComponentMapper<InputComponent> input = ComponentMapper
            .getFor(InputComponent.class);
    public static final ComponentMapper<MovementComponent> movement = ComponentMapper
            .getFor(MovementComponent.class);
}
