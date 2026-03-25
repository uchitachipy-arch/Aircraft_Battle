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
public class BossEnemy extends AbstractAircraft {

    private int shootCycle = 12;
    private int shootCounter = 0;
    private int bulletPower = 35;

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
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
            // 向下密集射击（7发）
            int x = this.getLocationX();
            int y = this.getLocationY();
            
            for (int i = -3; i <= 3; i++) {
                res.add(new EnemyBullet(x + i * 8, y, i / 2.0, 5, bulletPower));
            }
        }
        return res;
    }

}
