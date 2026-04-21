package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {
    private HeroAircraft Hero;
    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
        Hero=HeroAircraft.getInstance(512,512,0,0,1000);
    }

    @AfterEach
    void tearDown() {
        System.out.println("**--- Executed after each test method in this class ---**");
    }

    @Test
    void shoot() {
        System.out.println("**--- Test shoot method executed ---**");
        List<BaseBullet> bullet=Hero.shoot();
        assert(bullet.size()<=1);
    }

    @Test
    void addHp() {
        System.out.println("**--- Test addHp method executed ---**");
        Hero.addHp(1000);
        assertEquals(1000,Hero.getHp());
    }

    @Test
    void getHp() {
        System.out.println("**--- Test getHp method executed ---**");
        assertEquals(1000,Hero.getHp());
    }
}