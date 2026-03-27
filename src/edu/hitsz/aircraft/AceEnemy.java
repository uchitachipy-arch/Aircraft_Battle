package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 王牌敌机
 * 按周期向下多排射击
 * @author hitsz
 */
// to do 
public class AceEnemy extends AbstractAircraft {

    private int shootCycle = 15;
    private int shootCounter = 0;
    private int bulletPower = 30;

    public AceEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
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
