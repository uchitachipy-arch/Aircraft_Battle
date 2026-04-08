package edu.hitsz.shootstrategy;
import edu.hitsz.bullet.*;

import java.util.LinkedList;
import java.util.List;
public class scatter_enemy implements Shootstrategy{
    private int shootNum = 2;
    
    //子弹威力
    private int shootCycle = 25;
    private int shootCounter = 0;
    private int power = 30;
    @Override
    public List<BaseBullet> createbullet(int x,int y,int speedX,int speedY,int direction){
        List<BaseBullet> res = new LinkedList<>();
        shootCounter ++ ;
        if (shootCounter >= shootCycle) {
            shootCounter = 0;
            // 扇形散射：左斜、中直、右斜三发
            int offset = 12;
            BaseBullet leftBullet = new EnemyBullet(x - offset, y, -2, 10, power);
            BaseBullet midBullet = new EnemyBullet(x, y, 0, 10, power);
            BaseBullet rightBullet = new EnemyBullet(x + offset, y, 2, 10, power);
            res.add(leftBullet);
            res.add(midBullet);
            res.add(rightBullet);
        }
        return res;
    }
}
