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
public class AceEnemy extends AbstractAircraft {

    private int shootCycle = 15;
    private int shootCounter = 0;
    private int bulletPower = 30;

    public AceEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
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
            // 向下多排射击（5发）
            int x = this.getLocationX();
            int y = this.getLocationY();
            
            for (int i = -2; i <= 2; i++) {
                res.add(new EnemyBullet(x + i * 10, y, i, 5, bulletPower));
            }
        }
        return res;
    }

}
