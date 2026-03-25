package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 精锐敌机
 * 按周期向下扇形射击
 * @author hitsz
 */
public class EliteExcellentEnemy extends AbstractAircraft {

    private int shootCycle = 20;
    private int shootCounter = 0;
    private int bulletPower = 25;

    public EliteExcellentEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
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
            // 向下扇形射击（3发 左中右）
            int x = this.getLocationX();
            int y = this.getLocationY();
            
            // 左斜
            res.add(new EnemyBullet(x - 10, y, -2, 5, bulletPower));
            // 直下
            res.add(new EnemyBullet(x, y, 0, 5, bulletPower));
            // 右斜
            res.add(new EnemyBullet(x + 10, y, 2, 5, bulletPower));
        }
        return res;
    }

}
