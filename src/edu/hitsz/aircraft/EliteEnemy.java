package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.PropFactory;
import edu.hitsz.prop.baseprop;
import edu.hitsz.shootstrategy.oneline_enemy;

import java.util.LinkedList;
import java.util.List;

/**
 * 精英敌机
 * 按周期向下直射单排子弹
 * @author uchpy
 */
public class EliteEnemy extends AbstractAircraft {

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.setShootStrategy(new oneline_enemy());
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return shootStrategy.createbullet(this.getLocationX(), this.getLocationY(), this.speedX, this.speedY, 1);
    }

    @Override
    public List<baseprop> dropProp() {
        // 掉落概率 100%
        double dropRate = 1;
        List<baseprop> res = new LinkedList<>();
        if (Math.random() <= dropRate) {
            int x = this.getLocationX();
            int y = this.getLocationY();
            String type;
            double p= Math.random() ;
            if(p <= 0.33) type = "BloodProp";
            else if( p <= 0.66) type = "FireSuperProp";
            else type = "FireProp" ;
            baseprop prop= PropFactory.createProp(x, y, 0,8,type);
            res.add(prop);
        }
        return res;
    }

}
