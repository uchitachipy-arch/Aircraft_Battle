package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 精英敌机
 * 按周期向下直射单排子弹
 * @author hitsz
 */
public class EliteEnemy extends AbstractAircraft {

    private int shootCycle = 25;
    private int shootCounter = 0;
    private int bulletPower = 20;

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
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
            // 向下直射单排子弹
            int x = this.getLocationX();
            int y = this.getLocationY();
            BaseBullet bullet = new EnemyBullet(x, y, 0, 10, bulletPower);
            res.add(bullet);
        }
        return res;
    }

}
