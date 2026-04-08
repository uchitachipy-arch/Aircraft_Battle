package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.PropFactory;
import edu.hitsz.prop.baseprop;
import edu.hitsz.shootstrategy.twolinebullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 精锐敌机
 * @author hitsz
 */
//todo
public class ElitePlusEnemy extends AbstractAircraft {

    public ElitePlusEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.setShootStrategy(new twolinebullet());
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
