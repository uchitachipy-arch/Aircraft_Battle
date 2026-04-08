package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.shootstrategy.onelinebullet_hero;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author uchpy
 */
public class HeroAircraft extends AbstractAircraft {

    private static HeroAircraft instance;

    private int Hpmax = 1000;

    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.setShootStrategy(new onelinebullet_hero());
    }
    public static HeroAircraft getInstance(int locationX, int locationY, int speedX, int speedY, int hp) {
        if (instance == null) {
            synchronized (HeroAircraft.class){
                if(instance == null) {
                    instance = new HeroAircraft(locationX, locationY, speedX, speedY, hp);
                }          
            }  
        }
        return instance;
    }
    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        return shootStrategy.createbullet(this.getLocationX(), this.getLocationY(), this.speedX, this.speedY, -1);
    }

    @Override
    public List<edu.hitsz.prop.baseprop> dropProp() {
        // 英雄机不会掉落道具
        return new LinkedList<>();
    }
    public void addHp(int add){
        hp += add;
        if(hp > Hpmax ){
            hp= Hpmax;
        }
    }
}
