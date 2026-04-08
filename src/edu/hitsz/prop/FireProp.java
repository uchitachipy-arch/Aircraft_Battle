package edu.hitsz.prop;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shootstrategy.*;
public class FireProp extends baseprop {

    public FireProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance(0, 0, 0, 0, 0);
        heroAircraft.setShootStrategy(new scatter3_bullet());
    }
}
