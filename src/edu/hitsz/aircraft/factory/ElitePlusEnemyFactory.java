package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.ElitePlusEnemy;

/**
 * 强化精英敌机工厂
 */
public class ElitePlusEnemyFactory implements EnemyFactory {

    @Override
    public AbstractAircraft createEnemy(int locationX, int locationY) {
        int horizontalSpeed = Math.random() < 0.5 ? -2 : 2;
        return new ElitePlusEnemy(locationX, locationY, horizontalSpeed, 5, 40);
    }
}
