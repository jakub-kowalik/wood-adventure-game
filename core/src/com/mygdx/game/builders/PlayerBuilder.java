package com.mygdx.game.builders;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.mygdx.game.entitites.Entity;
import com.mygdx.game.entitites.Player;

import static com.mygdx.game.handlers.Box2DVariables.*;
import static com.mygdx.game.handlers.Box2DVariables.CATEGORY_BIT_GROUND;

public class PlayerBuilder extends EntityBuilder {
    PolygonShape shape;
    RevoluteJoint revoluteJoint;
    Body body2;

    public PlayerBuilder(World world) {
        super(world);
        shape = new PolygonShape();

    }

    @Override
    public Entity createEntity(Vector2 positionVector) {
        return new Player( createPlayerBody(positionVector), revoluteJoint, body2, 50 );
    }

    private Body createPlayerBody(Vector2 positionVector) {


        // create player
        bodyDef.position.set(positionVector.x / pixelPerMeter, positionVector.y / pixelPerMeter);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);


        shape.setAsBox(4 / pixelPerMeter, 16 / pixelPerMeter);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;
        fixtureDef.density = 0;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0f;

        //fixtureDef.filter.categoryBits = CATEGORY_BIT_PLAYER;
        // fixtureDef.filter.maskBits = CATEGORY_BIT_GROUND | CATEGORY_BIT_COLLECTABLE;
        body.createFixture(fixtureDef);
        body.setFixedRotation(true);

        // collision box

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        shape.setAsBox(4 / pixelPerMeter, 15.5f / pixelPerMeter, new Vector2(0, 0 / pixelPerMeter), 0);
        fixtureDef.shape = shape;
        fixtureDef.density = 100;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0f;

        fixtureDef.filter.categoryBits = CATEGORY_BIT_PLAYER;
        fixtureDef.filter.maskBits = CATEGORY_BIT_GROUND | CATEGORY_BIT_ENEMY;
        body.createFixture(fixtureDef).setUserData("player");

/*        //wheel

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(4f/pixelPerMeter);

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(positionVector.x / pixelPerMeter, positionVector.y / pixelPerMeter);
        Body body2 = world.createBody(bodyDef);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 100;
        fixtureDef.restitution = 0f;
        fixtureDef.friction = 20f;
        fixtureDef.filter.categoryBits = CATEGORY_BIT_PLAYER;
        fixtureDef.filter.maskBits = CATEGORY_BIT_GROUND | CATEGORY_BIT_COLLECTABLE;
        body2.createFixture(fixtureDef);
        body2.setUserData("player");
        circleShape.dispose();*/

/*        RevoluteJointDef motor = new RevoluteJointDef();
        motor.enableMotor = false;
//        motor.motorSpeed = 360 * MathUtils.degreesToRadians;
        motor.maxMotorTorque = 500;
        motor.bodyA = body;
        motor.bodyB = body2;
        motor.collideConnected = false;

        motor.localAnchorA.set(0,(-16+4f) / pixelPerMeter);
        motor.localAnchorB.set(0,0);

        playerMotor = (RevoluteJoint) world.createJoint(motor);*/


        //create foot left sensor

        shape.setAsBox(0.5f / pixelPerMeter, 0.01f / pixelPerMeter, new Vector2(-3.25f / pixelPerMeter, -15.99f / pixelPerMeter), 0);
        fixtureDef.shape = shape;
        fixtureDef.density = 0;
        fixtureDef.filter.categoryBits = CATEGORY_BIT_PLAYER;
        fixtureDef.filter.maskBits = CATEGORY_BIT_GROUND;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData("leftfoot");

        //create foot right sensor

        shape.setAsBox(0.5f / pixelPerMeter, 0.01f / pixelPerMeter, new Vector2(3.25f / pixelPerMeter, -15.99f / pixelPerMeter), 0);
        fixtureDef.shape = shape;
        fixtureDef.density = 0;
        fixtureDef.filter.categoryBits = CATEGORY_BIT_PLAYER;
        fixtureDef.filter.maskBits = CATEGORY_BIT_GROUND;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData("rightfoot");

        //create head sensor

        shape.setAsBox(3.5f / pixelPerMeter, 0.01f / pixelPerMeter, new Vector2(0, 15.99f / pixelPerMeter), 0);
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = CATEGORY_BIT_PLAYER;
        fixtureDef.filter.maskBits = CATEGORY_BIT_GROUND;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData("head");

        //create left sensor

        shape.setAsBox(0.5f / pixelPerMeter, 15 / pixelPerMeter, new Vector2(-4f / pixelPerMeter, 0 ), 0);
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = CATEGORY_BIT_PLAYER;
        fixtureDef.filter.maskBits = CATEGORY_BIT_GROUND;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData("left");

        //create right sensor

        shape.setAsBox(0.5f / pixelPerMeter, 15 / pixelPerMeter, new Vector2(4f / pixelPerMeter, 0), 0);
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = CATEGORY_BIT_PLAYER;
        fixtureDef.filter.maskBits = CATEGORY_BIT_GROUND;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData("right");

        //create attack sensor

        shape.setAsBox(1f / pixelPerMeter, 15 / pixelPerMeter, new Vector2(0f / pixelPerMeter, 8f / pixelPerMeter), 0);
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(positionVector.x / pixelPerMeter, positionVector.y / pixelPerMeter);
        body2 = world.createBody(bodyDef);
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0f;
        fixtureDef.friction = 0f;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = CATEGORY_BIT_PLAYER;
        fixtureDef.filter.maskBits = CATEGORY_BIT_ENEMY;
        body2.createFixture(fixtureDef).setUserData("sword");

        RevoluteJointDef motor = new RevoluteJointDef();
        motor.enableMotor = true;
//        motor.motorSpeed = 360 * MathUtils.degreesToRadians;
        motor.maxMotorTorque = 50f;
        motor.referenceAngle = 0;
        motor.enableLimit = true;
        motor.lowerAngle = (float) Math.toRadians(-135);
        motor.upperAngle = (float) Math.toRadians(45);
        motor.bodyA = body;
        motor.bodyB = body2;
        motor.collideConnected = false;

        motor.localAnchorA.set(0,5 / pixelPerMeter);
        motor.localAnchorB.set(0,-10 / pixelPerMeter);

        revoluteJoint = (RevoluteJoint) world.createJoint(motor);
        revoluteJoint.setMotorSpeed(100);


        return body;
    }
}
