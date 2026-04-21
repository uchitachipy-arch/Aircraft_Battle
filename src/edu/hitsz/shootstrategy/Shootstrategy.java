package edu.hitsz.shootstrategy;
import edu.hitsz.bullet.*;
import java.util.List;

public interface Shootstrategy {
    public List<BaseBullet> createbullet(int x,int y,int speedX,int speedY,int direction);
}
