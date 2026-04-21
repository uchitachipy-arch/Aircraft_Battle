package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.aircraft.factory.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.prop.baseprop;
import edu.hitsz.prop.BombProp;
import edu.hitsz.application.scoreboard.SBDaoimple;
import edu.hitsz.application.scoreboard.ScoreBoardDao;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.Timer;

import edu.hitsz.mythread.*;
/**
 * 游戏主面板，游戏启动
 * @author uchpy
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    //调度器, 用于定时任务调度
    private final Timer timer;
    //时间间隔(ms)，控制刷新频率
    private final int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<baseprop> props;
    private final List<EnemyFactory> enemyFactories;
    private final EnemyFactory bossFactory;

    //屏幕中出现的敌机最大数量
    private final int enemyMaxNumber = 5;

    //敌机生成周期
    protected double enemySpawnCycle  =  20;
    private int enemySpawnCounter = 0;

    //英雄机和敌机射击周期
    protected double shootCycle = 20;
    private int shootCounter = 0;

    //当前玩家分数
    private int score = 0;

    // Boss触发阈值控制
    private final int bossScoreThreshold = 100;
    private int nextBossScore = bossScoreThreshold;

    //游戏结束标志
    private boolean gameOverFlag = false;

    private static final String DEFAULT_DIFFICULTY = "NORMAL";
    private final ScoreBoardDao scoreBoardDao = new SBDaoimple();


    private MusicThread bgmThread;
    private MusicThread bossThread;

    private static final String BGM_FILE = "src/videos/bgm.wav";
    private static final String BOSS_BGM_FILE = "src/videos/bgm_boss.wav";
    private static final String BULLET_HIT_FILE = "src/videos/bullet_hit.wav";
    private static final String BOMB_EXPLOSION_FILE = "src/videos/bomb_explosion.wav";
    private static final String SUPPLY_FILE = "src/videos/get_supply.wav";
    private static final String GAME_OVER_FILE = "src/videos/game_over.wav";
    public Game() {
        heroAircraft = HeroAircraft.getInstance(
                Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                0, 0, 1000);

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();
        enemyFactories = Arrays.asList(
            new MobEnemyFactory(),
            new EliteEnemyFactory(),
            new ElitePlusEnemyFactory(),
            new AceEnemyFactory()
        );
        bossFactory = new BossEnemyFactory();

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

        this.timer = new Timer("game-action-timer", true);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    
    public void action() {
        //设置背景音乐
        bgmThread = new MusicThread(BGM_FILE);
        bossThread = new MusicThread(BOSS_BGM_FILE);
        bgmThread.start();
        // 定时任务：绘制、对象产生、碰撞判定、及结束判定
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                enemySpawnCounter++;
                if (enemySpawnCounter >=enemySpawnCycle) {
                    enemySpawnCounter = 0;
                    // 随机产生敌机
                    if (enemyAircrafts.size() < enemyMaxNumber) {
                        int randomType = (int) (Math.random() * enemyFactories.size());
                        int x = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()));
                        int y = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);

                        enemyAircrafts.add(enemyFactories.get(randomType).createEnemy(x, y));
                    }
                }
                // 分数到达阈值时触发Boss，且同屏仅存在1台Boss
                spawnBossIfNeeded();

                // 飞机发射子弹
                shootAction();
                // 子弹移动
                bulletsMoveAction();
                // 飞机移动
                aircraftsMoveAction();
                // 道具移动
                propsMoveAction();
                // 撞击检测:子弹，道具
                crashCheckAction();
                // 后处理
                postProcessAction();
                // Boss音乐状态维护
                
                // 重绘界面
                repaint();
                // 游戏结束检查
                checkResultAction();
            }
        };
        // 以固定延迟时间进行执行：本次任务执行完成后，延迟 timeInterval 再执行下一次
        timer.schedule(task,0,timeInterval);

    }

    //***********************
    //      Action 各部分
    //***********************

    private void shootAction() {
        //英雄机射击
        heroBullets.addAll(heroAircraft.shoot());
        // 敌机射击
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyBullets.addAll(enemyAircraft.shoot());
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction() {
        for (baseprop prop : props) {
            prop.forward();
        }
    }

    

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // 敌机子弹攻击英雄机
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                // 英雄机被敌机子弹击中
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    playSfx(BULLET_HIT_FILE);
                    if (enemyAircraft.notValid()) {
                        if(enemyAircraft instanceof BossEnemy){
                            bossThread.stopMusic();
                            bgmThread = new MusicThread(BGM_FILE);
                            bgmThread.start();
                        }
                        // 获得分数，产生道具补给
                        score += 10;
                        props.addAll(enemyAircraft.dropProp());
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                    props.addAll(enemyAircraft.dropProp());
                }
            }
        }

        // 我方获得道具，道具生效
        for (baseprop prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash(prop) || prop.crash(heroAircraft)) {
                // 触碰后触发道具效果（当前仅消失）
                prop.effect();

                if (prop instanceof BombProp) {
                    playSfx(BOMB_EXPLOSION_FILE);
                }else playSfx(SUPPLY_FILE);
                prop.vanish();
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除无效的道具
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }

    private void spawnBossIfNeeded() {
        if (score < nextBossScore) {
            return;
        }
        nextBossScore += bossScoreThreshold;
        if (hasBossAlive()) {
            return;
        }
        bgmThread.stopMusic();
        bossThread = new MusicThread(BOSS_BGM_FILE);
        bossThread.start();
        int spawnX = Main.WINDOW_WIDTH / 2;
        int spawnY = (int) (Main.WINDOW_HEIGHT * 0.08);
        enemyAircrafts.add(bossFactory.createEnemy(spawnX, spawnY));
    }

    private boolean hasBossAlive() {
        for (AbstractAircraft enemy : enemyAircrafts) {
            if (enemy instanceof BossEnemy && !enemy.notValid()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查游戏是否结束，若结束：关闭线程池
     */
    private void checkResultAction(){
        // 游戏结束检查英雄机是否存活
        if (heroAircraft.getHp() <= 0 && !gameOverFlag) {
            timer.cancel(); // 取消定时器并终止所有调度任务
            gameOverFlag = true;
            stopAllBgm();
            playSfx(GAME_OVER_FILE);
            System.out.println("Game Over!");
            saveAndPrintRank();
        }
    };

    

    

    private void stopAllBgm() {
        if (bgmThread != null) {
            bgmThread.stopMusic();
        }
        if (bossThread != null) {
            bossThread.stopMusic();
        }
    }

    private void playSfx(String fileName) {
        onetimeMusic effectThread = new onetimeMusic(fileName);
        effectThread.start();
    }

    private void saveAndPrintRank() {
        scoreBoardDao.recordAndPrint(DEFAULT_DIFFICULTY, score);
    }

    //***********************
    //      Paint 各部分
    //***********************
    /**
     * 重写 paint方法
     * 通过重复调用paint方法，实现游戏动画
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);

        // 绘制道具
        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.isEmpty()) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(Color.RED);
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE: " + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE: " + this.heroAircraft.getHp(), x, y);
    }

}
