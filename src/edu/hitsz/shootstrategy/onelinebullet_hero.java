package edu.hitsz.shootstrategy;
import edu.hitsz.bullet.*;

import java.util.LinkedList;
import java.util.List;
public class onelinebullet_hero implements Shootstrategy{
    private int shootNum = 1;
    
    //子弹威力
    private int power = 30;
    protected double shootCycle = 20;
    private int shootCounter = 0;
    @Override
    public List<BaseBullet> createbullet(int x,int y,int speedX,int speedY,int direction){
        List<BaseBullet> res = new LinkedList<>();
        shootCounter++;
        if(shootCounter >= shootCycle){
            shootCounter = 0;
            y = y + direction*2;
            speedX = 0;
            speedY += direction*5;
            HeroBullet bullet;
            for(int i=0; i<shootNum; i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                bullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
                res.add(bullet);
            }
        }
        return res;
    }
}
