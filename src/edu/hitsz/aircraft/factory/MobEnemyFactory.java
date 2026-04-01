package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;

/**
 * 普通敌机工厂
 */
public class MobEnemyFactory implements EnemyFactory {

    @Override
    public AbstractAircraft createEnemy(int locationX, int locationY) {
        return new MobEnemy(locationX, locationY, 0, 8, 30);
    }
}
