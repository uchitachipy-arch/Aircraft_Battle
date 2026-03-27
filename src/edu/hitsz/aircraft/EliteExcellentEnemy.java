package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 精锐敌机
 * @author hitsz
 */
//todo
public class EliteExcellentEnemy extends AbstractAircraft {

    private int shootCycle = 20;
    private int shootCounter = 0;
    private int bulletPower = 25;

    public EliteExcellentEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
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
