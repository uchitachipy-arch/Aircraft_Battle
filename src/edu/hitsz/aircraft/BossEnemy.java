package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * Boss敌机
 * 生命值高，按周期向下密集射击
 * @author hitsz
 */
// todo
public class BossEnemy extends AbstractAircraft {

    private int shootCycle = 12;
    private int shootCounter = 0;
    private int bulletPower = 35;

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }
    @Override
    public List<edu.hitsz.prop.baseprop> dropProp() {
        return new LinkedList<>();
    }
}
