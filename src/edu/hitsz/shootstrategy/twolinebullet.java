package edu.hitsz.shootstrategy;
import edu.hitsz.bullet.*;

import java.util.LinkedList;
import java.util.List;
public class twolinebullet implements Shootstrategy{
    private int shootNum = 2;
    
    //子弹威力
    private int power = 25;
    private int shootCycle = 20;
    private int shootCounter = 0;
    @Override
    public List<BaseBullet> createbullet(int x,int y,int speedX,int speedY,int direction){
        List<BaseBullet> res = new LinkedList<>();
        shootCounter++;
        if (shootCounter >= shootCycle) {
            shootCounter = 0;
            // 向下直射双排子弹
            int offset = 15;
            BaseBullet leftBullet = new EnemyBullet(x - offset, y, 0, 10, power);
            BaseBullet rightBullet = new EnemyBullet(x + offset, y, 0, 10, power);
            res.add(leftBullet);
            res.add(rightBullet);
        }
        return res;
    }
}
