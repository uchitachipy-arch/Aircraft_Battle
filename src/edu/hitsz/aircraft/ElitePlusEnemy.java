package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.PropFactory;
import edu.hitsz.prop.baseprop;

import java.util.LinkedList;
import java.util.List;

/**
 * 精锐敌机
 * @author hitsz
 */
//todo
public class ElitePlusEnemy extends AbstractAircraft {

    private int shootCycle = 20;
    private int shootCounter = 0;
    private int bulletPower = 25;

    public ElitePlusEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }
    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
        shootCounter++;
    }
   
    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        if (shootCounter >= shootCycle) {
            shootCounter = 0;
            // 向下直射双排子弹
            int x = this.getLocationX();
            int y = this.getLocationY();
            int offset = 15;
            BaseBullet leftBullet = new EnemyBullet(x - offset, y, 0, 10, bulletPower);
            BaseBullet rightBullet = new EnemyBullet(x + offset, y, 0, 10, bulletPower);
            res.add(leftBullet);
            res.add(rightBullet);
        }
        return res;
    }

    @Override
    public List<edu.hitsz.prop.baseprop> dropProp() {
        // 掉落概率 100%
        double dropRate = 1;
        List<baseprop> res = new LinkedList<>();
        if (Math.random() <= dropRate) {
            int x = this.getLocationX();
            int y = this.getLocationY();
            String type;
            double p= Math.random() ;
            if(p <= 0.25) type = "BloodProp";
            else if( p <= 0.5) type = "FireSuperProp";
            else if( p <= 0.75 )type = "FireProp" ;
            else type = "BombProp" ;
            baseprop prop= PropFactory.createProp(x, y, 0,8,type);
            res.add(prop);
        }
        return res;
    }

}
