package edu.hitsz.shootstrategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 二十发环射策略：同时发射 20 颗子弹，角度均匀分布。
 */
public class ring20_bullet implements Shootstrategy {

    private int bulletCount = 20;
    private int power = 20;
    private int speed = 8;
    private int bulletCycle = 25;
    private int bulletCounter = 0;

    @Override
    public List<BaseBullet> createbullet(int x, int y, int speedX, int speedY, int direction) {
        List<BaseBullet> res = new LinkedList<>();
        bulletCounter++;
        if (bulletCounter >= bulletCycle) {
            bulletCounter = 0;
            int spawnY = y + direction * 2;

            for (int i = 0; i < bulletCount; i++) {
                double angle = 2.0 * Math.PI * i / bulletCount;
                int sx = (int) Math.round(speed * Math.cos(angle));
                int sy = (int) Math.round(speed * Math.sin(angle));
                res.add(createByDirection(x, spawnY, sx, sy, direction));
            }
        }
        

        

        return res;
    }

    private BaseBullet createByDirection(int x, int y, int sx, int sy, int direction) {
        if (direction < 0) {
            return new HeroBullet(x, y, sx, sy, power);
        }
        return new EnemyBullet(x, y, sx, sy, power);
    }
}
