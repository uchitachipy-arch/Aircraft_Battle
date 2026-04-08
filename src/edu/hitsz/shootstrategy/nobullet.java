package edu.hitsz.shootstrategy;
import edu.hitsz.bullet.*;

import java.util.LinkedList;
import java.util.List;
public class nobullet implements Shootstrategy{
    @Override
    public List<BaseBullet> createbullet(int x,int y,int speedX,int speedY,int direction){
        return new LinkedList<>();
    }
}
