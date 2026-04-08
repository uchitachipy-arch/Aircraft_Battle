package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;

/**
 * Boss敌机工厂
 */
public class BossEnemyFactory implements EnemyFactory {

    @Override
    public AbstractAircraft createEnemy(int locationX, int locationY) {
        int horizontalSpeed = Math.random() < 0.5 ? -2 : 2;
        return new BossEnemy(locationX, locationY, horizontalSpeed, 0, 300);
    }
}
