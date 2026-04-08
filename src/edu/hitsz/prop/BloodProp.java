package edu.hitsz.prop;
import edu.hitsz.aircraft.HeroAircraft;
public class BloodProp extends baseprop {

    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    // 加血道具增加的血量
    private int add = 200;
    @Override
    public void effect() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance(0, 0, 0, 0, 0);
        heroAircraft.addHp(add);
    }
}
