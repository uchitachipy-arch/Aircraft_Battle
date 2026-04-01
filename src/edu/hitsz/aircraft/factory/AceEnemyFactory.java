package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AceEnemy;

/**
 * 王牌敌机工厂
 */
public class AceEnemyFactory implements EnemyFactory {

    @Override
    public AbstractAircraft createEnemy(int locationX, int locationY) {
        int horizontalSpeed = Math.random() < 0.5 ? -2 : 2;
        return new AceEnemy(locationX, locationY, horizontalSpeed, 5, 40);
    }
}
