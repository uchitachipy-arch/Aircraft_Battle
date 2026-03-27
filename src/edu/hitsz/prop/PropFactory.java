package edu.hitsz.prop;

/**
 * 简单工厂：根据随机数创建不同道具
 */
public class PropFactory {

    public static baseprop createProp(int locationX, int locationY, int speedX, int speedY, String type) {
        
        if (type == "FireProp") {
            return new FireProp(locationX, locationY, speedX, speedY);
        } else if (type == "FireSuperProp") {
            return new FireSuperProp(locationX, locationY, speedX, speedY);
        } else if (type == "BombProp") {
            return new BombProp(locationX, locationY, speedX, speedY);
        } else if (type == "BloodProp") {
            return new BloodProp(locationX, locationY, speedX, speedY);
        } else {
            return new FreezeProp(locationX, locationY, speedX, speedY);
        }
    }

}
