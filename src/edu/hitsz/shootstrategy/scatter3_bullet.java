package edu.hitsz.shootstrategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 三发散射策略：同时发射 3 颗子弹（左、中、右）。
 */
public class scatter3_bullet implements Shootstrategy {

    private int power = 30;
    private int speedYBase = 10;
    private int speedXDelta = 2;
    private int offset = 12;
    private int bulletCycle = 25;
    private int bulletCounter = 0;

    @Override
    public List<BaseBullet> createbullet(int x, int y, int speedX, int speedY, int direction) {
        List<BaseBullet> res = new LinkedList<>();
        bulletCounter++;
        if (bulletCounter >= bulletCycle) {
            bulletCounter = 0;
            int spawnY = y + direction * 2;
            int bulletSpeedY = speedY + direction * speedYBase;

            res.add(createByDirection(x - offset, spawnY, -speedXDelta, bulletSpeedY, direction));
            res.add(createByDirection(x, spawnY, 0, bulletSpeedY, direction));
            res.add(createByDirection(x + offset, spawnY, speedXDelta, bulletSpeedY, direction));
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
