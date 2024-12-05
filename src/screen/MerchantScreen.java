package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;

import engine.Cooldown;
import engine.Core;
// Sound Operator
import Sound_Operator.SoundManager;
import inventory_develop.ShipStatus;

/**
 * Implements the title screen.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class MerchantScreen extends Screen {

    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;

    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;

    private int coin;
    private int gem;

    public int getMerchantState() {
        return merchantState;
    }

    public void setMerchantState(int merchantState) {
        this.merchantState = merchantState;
    }

    private int merchantState;
    private ShipStatus shipStatus;


    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width
     *            Screen width.
     * @param height
     *            Screen height.
     * @param fps
     *            Frames per second, frame rate at which the game is run.
     */
    public MerchantScreen(final int width, final int height, final int fps) {
        super(width, height, fps);

        // Defaults
        this.merchantState = 1;
        this.returnCode = 4;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();

        // CtrlS: Set user's coin, gem
        try {
            this.coin = Core.getCurrencyManager().getCoin();
            this.gem = Core.getCurrencyManager().getGem();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Sound Operator
        SoundManager.getInstance().playBGM("mainMenu_bgm");

        // inventory load upgrade price
        shipStatus = new ShipStatus();
        shipStatus.loadPrice();
    }

    /**
     * Starts the action.
     *
     * @return Next screen code.
     */
    public final int run() {
        super.run();

        return this.returnCode;
    }

    /**
     * Updates the elements on screen and checks for events.
     */
    protected final void update() {
        super.update();

        draw();
        if (this.selectionCooldown.checkFinished()
                && this.inputDelay.checkFinished()) {
            if (inputManager.isKeyDown(KeyEvent.VK_UP)
                    || inputManager.isKeyDown(KeyEvent.VK_W)) {
                previousMerchantState();
                this.selectionCooldown.reset();
                // Sound Operator
                SoundManager.getInstance().playES("menuSelect_es");
            }
            if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
                    || inputManager.isKeyDown(KeyEvent.VK_S)) {
                nextMerchantState();
                this.selectionCooldown.reset();
                // Sound Operator
                SoundManager.getInstance().playES("menuSelect_es");
            }

            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                if (merchantState != 0) {
                    testStatUpgrade();
                    this.selectionCooldown.reset();
                } else {
                    this.returnCode = 1;
                    this.isRunning = false;
                }
            }
        }
    }
    // Use later if needed. -Starter
    // public int getPnumSelectionCode() {return this.pnumSelectionCode;}

    /**
     * runs when player do buying things
     * when store system is ready -- unwrap annotated code and rename this method
     */
//	private void testCoinDiscounter(){
//		if(this.currentCoin > 0){
//			this.currentCoin -= 50;
//		}

//		try{
//			Core.getFileManager().saveCurrentCoin();
//		} catch (IOException e) {
//			logger.warning("Couldn't save current coin!");
//		}
//	}

    private void testStatUpgrade() {
        // CtrlS: testStatUpgrade should only be called after coins are spent
        if (this.merchantState == 1) { // bulletCount
            try {
                if (Core.getUpgradeManager().LevelCalculation(Core.getUpgradeManager().getBulletCount()) > 3){
                    Core.getLogger().info("The level is already Max!");
                }

                else if (!(Core.getUpgradeManager().getBulletCount() % 2 == 0)
                        && Core.getCurrencyManager().spendCoin(Core.getUpgradeManager().Price(1))) {

                    Core.getUpgradeManager().addBulletNum();
                    Core.getLogger().info("Bullet Number: " + Core.getUpgradeManager().getBulletNum());

                    Core.getUpgradeManager().addBulletCount();

                } else if ((Core.getUpgradeManager().getBulletCount() % 2 == 0)
                        && Core.getCurrencyManager().spendGem((Core.getUpgradeManager().getBulletCount() + 1) * 10)) {

                    Core.getUpgradeManager().addBulletCount();
                    Core.getLogger().info("Upgrade has been unlocked");

                } else {
                    Core.getLogger().info("you don't have enough");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else if (this.merchantState == 2) { // shipSpeed
            try {
                if (Core.getUpgradeManager().LevelCalculation(Core.getUpgradeManager().getSpeedCount()) > 10){
                    Core.getLogger().info("The level is already Max!");
                }

                else if (!(Core.getUpgradeManager().getSpeedCount() % 4 == 0)
                        && Core.getCurrencyManager().spendCoin(Core.getUpgradeManager().Price(2))) {

                    Core.getUpgradeManager().addMovementSpeed();
                    Core.getLogger().info("Movement Speed: " + Core.getUpgradeManager().getMovementSpeed());

                    Core.getUpgradeManager().addSpeedCount();

                } else if ((Core.getUpgradeManager().getSpeedCount() % 4 == 0)
                        && Core.getCurrencyManager().spendGem(Core.getUpgradeManager().getSpeedCount() / 4 * 5)) {

                    Core.getUpgradeManager().addSpeedCount();
                    Core.getLogger().info("Upgrade has been unlocked");

                } else {
                    Core.getLogger().info("you don't have enough");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else if (this.merchantState == 3) { // attackSpeed
            try {
                if (Core.getUpgradeManager().LevelCalculation(Core.getUpgradeManager().getAttackCount()) > 10){
                    Core.getLogger().info("The level is already Max!");
                }

                else if (!(Core.getUpgradeManager().getAttackCount() % 4 == 0)
                        && Core.getCurrencyManager().spendCoin(Core.getUpgradeManager().Price(3))) {

                    Core.getUpgradeManager().addAttackSpeed();
                    Core.getLogger().info("Attack Speed: " + Core.getUpgradeManager().getAttackSpeed());

                    Core.getUpgradeManager().addAttackCount();

                } else if ((Core.getUpgradeManager().getAttackCount() % 4 == 0)
                        && Core.getCurrencyManager().spendGem(Core.getUpgradeManager().getAttackCount() / 4 * 5)) {

                    Core.getUpgradeManager().addAttackCount();
                    Core.getLogger().info("Upgrade has been unlocked");

                } else {
                    Core.getLogger().info("you don't have enough");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else if (this.merchantState == 4) { // coinGain
            try {
                if (Core.getUpgradeManager().LevelCalculation(Core.getUpgradeManager().getCoinCount()) > 10){
                    Core.getLogger().info("The level is already Max!");
                }

                else if (!(Core.getUpgradeManager().getCoinCount() % 4 == 0)
                        && Core.getCurrencyManager().spendCoin(Core.getUpgradeManager().Price(4))) {

                    Core.getUpgradeManager().addCoinAcquisitionMultiplier();
                    Core.getLogger().info("CoinBonus: " + Core.getUpgradeManager().getCoinAcquisitionMultiplier());

                    Core.getUpgradeManager().addCoinCount();

                } else if ((Core.getUpgradeManager().getCoinCount() % 4 == 0)
                        && Core.getCurrencyManager().spendGem(Core.getUpgradeManager().getCoinCount() / 4 * 5)) {

                    Core.getUpgradeManager().addCoinCount();
                    Core.getLogger().info("Upgrade has been unlocked");

                } else {
                    Core.getLogger().info("you don't have enough");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        try{
            this.coin = Core.getCurrencyManager().getCoin();
            this.gem = Core.getCurrencyManager().getGem();

        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    protected void nextMerchantState() {
        if (this.merchantState == 4)
            this.merchantState = 0; // from 'Coin Gain Up' to 'Go main'
        else
            this.merchantState++; // go next
    }

    /**
     * Shifts the focus to the previous menu item.
     */
    protected void previousMerchantState() {
        if (this.merchantState == 0)
            this.merchantState = 4; //from 'Go main' to 'Coin Gain Up'
        else
            this.merchantState--; // go previous
    }


    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawMerchantTitle(this, this.merchantState);
        drawManager.drawMerchantMenu(this, this.merchantState);
        // CtrlS
        drawManager.drawCurrentCoin(this, coin);
        drawManager.drawCurrentGem(this, gem);

        super.drawPost();
        drawManager.completeDrawing(this);
    }

}

