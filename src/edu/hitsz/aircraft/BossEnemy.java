package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.PropFactory;
import edu.hitsz.prop.baseprop;
import edu.hitsz.shootstrategy.ring20_bullet;

import java.util.LinkedList;
import java.util.List;

/**
 * Boss敌机：仅左右移动，采用20发环射，击毁后随机掉落3个道具。
 */
public class BossEnemy extends AbstractAircraft {

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.setShootStrategy(new ring20_bullet());
    }

    @Override
    public void forward() {
        // Boss仅左右移动，不执行向下移动
        locationX += speedX;
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            speedX = -speedX;
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return shootStrategy.createbullet(this.getLocationX(), this.getLocationY(), this.speedX, this.speedY, 1);
    }

    @Override
    public List<baseprop> dropProp() {
        List<baseprop> res = new LinkedList<>();
        int[] xOffsets = {-30, 0, 30};
        for (int i = 0; i < 3; i++) {
            int x = this.getLocationX() + xOffsets[i];
            int y = this.getLocationY();
            String type;
            double p = Math.random();
            if (p <= 0.2) type = "BloodProp";
            else if (p <= 0.4) type = "FireSuperProp";
            else if (p <= 0.6) type = "FireProp";
            else if (p <= 0.8) type = "BombProp";
            else type = "FreezeProp";
            baseprop prop = PropFactory.createProp(x, y, 0, 8, type);
            res.add(prop);
        }
        return res;
    }
}
