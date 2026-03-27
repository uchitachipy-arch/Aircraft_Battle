package edu.hitsz.prop;

/**
 * 简单工厂：根据随机数创建不同道具
 */
public class PropFactory {

    public static baseprop createRandomProp(int locationX, int locationY, int speedX, int speedY) {
        double r = Math.random();
        if (r < 0.2) {
            return new FireProp(locationX, locationY, speedX, speedY);
        } else if (r < 0.4) {
            return new FireSuperProp(locationX, locationY, speedX, speedY);
        } else if (r < 0.6) {
            return new BombProp(locationX, locationY, speedX, speedY);
        } else if (r < 0.8) {
            return new BloodProp(locationX, locationY, speedX, speedY);
        } else {
            return new FreezeProp(locationX, locationY, speedX, speedY);
        }
    }

}
