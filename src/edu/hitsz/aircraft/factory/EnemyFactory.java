package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;

/**
 * 敌机工厂接口（工厂方法模式）
 */
public interface EnemyFactory {

    /**
     * 创建敌机
     * @param locationX 敌机初始x坐标
     * @param locationY 敌机初始y坐标
     * @return 敌机对象
     */
    AbstractAircraft createEnemy(int locationX, int locationY);
}
