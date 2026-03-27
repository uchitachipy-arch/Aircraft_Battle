package edu.hitsz.prop;

public class FireProp extends baseprop {

    public FireProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect() {
        System.out.println("FireSupply active!");
    }
}
