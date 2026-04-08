package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.PropFactory;
import edu.hitsz.prop.baseprop;
import edu.hitsz.shootstrategy.scatter_enemy;

import java.util.LinkedList;
import java.util.List;

/**
 * 王牌敌机
 * 按周期向下多排射击
 * @author hitsz
 */
// to do 
public class AceEnemy extends AbstractAircraft {

    public AceEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.setShootStrategy(new scatter_enemy());
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
            if(p <= 0.2) type = "BloodProp";
            else if( p <= 0.4) type = "FireSuperProp";
            else if( p <= 0.6 ) type = "FireProp" ;
            else if( p <= 0.8 ) type = "BombProp" ;
            else type = "FreezeProp" ; 
            baseprop prop= PropFactory.createProp(x, y, 0,8,type);
            res.add(prop);
        }
        return res;
    }
}
