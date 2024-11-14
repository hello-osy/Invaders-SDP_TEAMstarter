package Enemy;

import engine.Core;
import entity.EnemyShip;
import entity.Ship;
import inventory_develop.Bomb;
import inventory_develop.FeverTimeItem;
import screen.GameScreen;
import engine.DrawManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

//import inventory_develop.Bomb;
import inventory_develop.ItemBarrierAndHeart;
import inventory_develop.NumberOfBullet;
import inventory_develop.SpeedItem;

import CtrlS.CurrencyManager;

// Sound Operator
import Sound_Operator.SoundManager;


public class ItemManager {

    public Set<Item> items;
    private int screenHeight;
    private DrawManager drawManager;
    private GameScreen gameScreen;
    protected Logger logger = Core.getLogger();
    private Set<Item> recyclableItems = new HashSet<>();
    private Set<EnemyShip> enemyShips;
    private ItemBarrierAndHeart Item2;
    private NumberOfBullet numberOfBullet;
    private SpeedItem speedItem;
    private Ship ship;
    private PlayerGrowth growth;
    private FeverTimeItem feverTimeItem;
    private CurrencyManager currencyManager;
    // Sound Operator
    private static SoundManager sm;

    public ItemManager(int screenHeight, DrawManager drawManager, GameScreen gameScreen) {
        this.items = new HashSet<>();
        this.screenHeight = screenHeight;
        this.drawManager = drawManager;
        this.gameScreen = gameScreen;
        this.ship = gameScreen.getShip();       // Team Inventory
        this.growth = ship.getPlayerGrowth();
        this.Item2 = gameScreen.getItem();
        this.feverTimeItem = gameScreen.getFeverTimeItem();
        this.numberOfBullet = new NumberOfBullet();
        this.speedItem = gameScreen.getSpeedItem();
        this.enemyShips = new HashSet<>();
    }

    public void cleanItems() {
        Set<Item> recyclable = new HashSet<>();
        for (Item item : this.items) {
            item.update();
            if (item.getPositionY() > screenHeight) {
                recyclable.add(item);
            }
        }
        this.items.removeAll(recyclable);
        ItemPool.recycle(recyclable);
    }

    public void initialize() {
        this.items = new HashSet<>();
    }

    public void drawItems() {
        for (Item item : this.items) {
            drawManager.drawEntity(item, item.getPositionX(), item.getPositionY());
        }
    }

    public void dropItem(EnemyShip enemyShip, double probability, int enemyship_type) {
        if(Math.random() < probability) {
            Item item = ItemPool.getItem(enemyShip.getPositionX(), enemyShip.getPositionY(), 3, enemyship_type);
            this.items.add(item);
        }
    }

    public void setEnemyShips(Set<EnemyShip> enemyShips) {
        this.enemyShips = enemyShips;
    }

    private int currentBomb = 0;
    private int currentBarrier = 0;
    private int currentMagnet = 0;
    public void itemSave(Item item){
        if(item!= null){
            DrawManager.SpriteType itemSelected = item.getSpriteType();

            switch (itemSelected){
                case ItemBomb :
                    currentBomb++;
                    break;
                case ItemBarrier:
                    currentBarrier++;
                    break;
                case ItemMagnet:
                    currentMagnet++;
                    break;
                case ItemHeart:
                    Item2.activeheart(gameScreen);
                    //Sound_Operator
                    sm = SoundManager.getInstance();
                    sm.playES("get_item");
                    break;
                case ItemFeverTime:
                    feverTimeItem.activate();
                    break;
                case ItemPierce:
                    numberOfBullet.pierceup();
                    ship.increaseBulletSpeed();
                    //Sound_Operator
                    sm = SoundManager.getInstance();
                    sm.playES("get_item");
                    break;
                case ItemCoin:
                    this.logger.info("You get coin!");
                    break;
                case ItemSpeedUp:
                    speedItem.activate(true, enemyShips);
                    break;
                case ItemSpeedSlow:
                    speedItem.activate(false, enemyShips);
                    break;
            }
            addItemRecycle(item);
        }
    }

    public void activateBomb(){
        if(currentBomb > 0){
            OperateItem(DrawManager.SpriteType.ItemBomb);
            this.currentBomb--;
        }
    }
    public void activateBarrier(){
        if(currentBarrier > 0){
            OperateItem(DrawManager.SpriteType.ItemBarrier);
            this.currentBarrier--;
        }
    }
    public void activateMagnet(){
        if(currentMagnet > 0){
            for(Item item: items){
                itemSave(item);
            }
        }
    }
    public int getCurrentBomb(){ return this.currentBomb; }
    public int getCurrentBarrier() { return currentBarrier; }
    public int getCurrentMagnet() { return currentMagnet; }

    // team Inventory
    public void OperateItem(DrawManager.SpriteType item) {
//        if(item!= null) {

//            DrawManager.SpriteType whatItem = item.getSpriteType();

            switch (item) {     // Operates according to the SpriteType of the item.
                case ItemBomb:
                    Bomb.setIsbomb(true);
                    Bomb.setCanShoot(true);
                    //Sound_Operator
                    sm = SoundManager.getInstance();
                    sm.playES("get_item");
                    break;
                case ItemBarrier:
                    Item2.activatebarrier();
                    //Sound_Operator
                    sm = SoundManager.getInstance();
                    sm.playES("get_item");
                    break;
                case ItemHeart:
                    Item2.activeheart(gameScreen);
                    //Sound_Operator
                    sm = SoundManager.getInstance();
                    sm.playES("get_item");
                    break;
                case ItemFeverTime:
                    feverTimeItem.activate();
                    break;
                case ItemPierce:
                    numberOfBullet.pierceup();
                    ship.increaseBulletSpeed();
                    //Sound_Operator
                    sm = SoundManager.getInstance();
                    sm.playES("get_item");
                    break;
                case ItemCoin:
                    this.logger.info("You get coin!");
                    break;
                case ItemSpeedUp:
                    speedItem.activate(true, enemyShips);
                    break;
                case ItemSpeedSlow:
                    speedItem.activate(false, enemyShips);
                    break;
            }

//            addItemRecycle(item);
//        }
    }

    public void addItemRecycle(Item item) {
        recyclableItems.add(item);
        String itemLog = item.getSpriteType().toString().toLowerCase().substring(4);
        // Sound Operator
        if (itemLog.equals("coin")){
            sm = SoundManager.getInstance();
            sm.playES("item_coin");
        }

        if (!itemLog.equals("coin")) {
            this.logger.info("get " + itemLog + " item");   // Change log for each item
        }
    }

    public void removeAllReItems(){
        this.items.removeAll(recyclableItems);
        ItemPool.recycle(recyclableItems);
    }

}
