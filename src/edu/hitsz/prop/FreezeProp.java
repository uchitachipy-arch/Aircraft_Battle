package edu.hitsz.prop;

public class FreezeProp extends baseprop {

    public FreezeProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effect() {
        System.out.println("Anemys are frozen!");
    }
}
