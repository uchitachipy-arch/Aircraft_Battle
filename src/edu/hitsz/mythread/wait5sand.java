package edu.hitsz.mythread;
import edu.hitsz.aircraft.HeroAircraft;
import java.util.concurrent.TimeUnit;
public class wait5sand extends Thread{
    @Override
    public void run(){
        try{
            TimeUnit.SECONDS.sleep(5);
            HeroAircraft hero = HeroAircraft.getInstance(0,0,0,0,0);
            hero.normalshoot();
        }catch(InterruptedException e){
            
        }
    }
}
