package edu.hitsz.shootstrategy;
import edu.hitsz.bullet.*;

import java.util.LinkedList;
import java.util.List;
public class oneline_enemy implements Shootstrategy{
    private int shootNum = 1;
    
    //子弹威力
    private int power = 30;
    protected double shootCycle = 20;
    private int shootCounter = 0;
    @Override
    public List<BaseBullet> createbullet(int x,int y,int speedX,int speedY,int direction){
        List<BaseBullet> res = new LinkedList<>();
        shootCounter++ ;
        if (shootCounter >= shootCycle) {
            shootCounter = 0;
            // 向下直射单排子弹
            BaseBullet bullet = new EnemyBullet(x, y, 0, 10, power);
            res.add(bullet);
        }
        return res;
    }
}
