package screen;

import Sound_Operator.SoundManager;
import engine.Cooldown;
import engine.Core;
import inventory_develop.ShipStatus;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class SettingScreen extends Screen {
    private static final int SELECTION_TIME = 200;

    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;
    private int settingCode;
    public SettingScreen(final int width, final int height, final int fps) {
        super(width, height, fps);

        // Defaults
        this.settingCode = 1;
        this.returnCode = 7;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
        // Sound Operator
        SoundManager.getInstance().playBGM("mainMenu_bgm");

    }
    public final int run() {
        super.run();

        return this.returnCode;
    }
    protected final void update() {
        super.update();

        draw();
        if (this.selectionCooldown.checkFinished()
                && this.inputDelay.checkFinished()) {
            if (inputManager.isKeyDown(KeyEvent.VK_UP)
                    || inputManager.isKeyDown(KeyEvent.VK_W)) {
                previousSettingMenu();
                this.selectionCooldown.reset();
                // Sound Operator
                SoundManager.getInstance().playES("menuSelect_es");
            }
            if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
                    || inputManager.isKeyDown(KeyEvent.VK_S)) {
                nextSettingMenu();
                this.selectionCooldown.reset();
                // Sound Operator
                SoundManager.getInstance().playES("menuSelect_es");
            }

            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                if (settingCode == 0) {
                    this.returnCode = 1;
                    this.isRunning = false;
                }
            }
        }
    }
    private void nextSettingMenu() {
        if (this.settingCode == 2)
            this.settingCode = 0; // from 'Menu2' to 'Go main'
        else
            this.settingCode++; // go next
    }

    /**
     * Shifts the focus to the previous menu item.
     */
    private void previousSettingMenu() {
        if (this.settingCode == 0)
            this.settingCode = 2; //from 'Go main' to 'Menu2'
        else
            this.settingCode--; // go previous
    }

    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawSettingTitle(this);
        drawManager.drawSettingMenu(this, this.settingCode);

        super.drawPost();
        drawManager.completeDrawing(this);
    }
}
