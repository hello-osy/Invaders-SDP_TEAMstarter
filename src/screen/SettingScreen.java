package screen;

import Sound_Operator.SoundManager;
import engine.Cooldown;
import engine.Core;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class SettingScreen extends Screen {
    private static final int SELECTION_TIME = 200;

    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;
    private int settingCode;
    private int BGMCode;
    private int ESCode;

    private static SoundManager sm;
    private String[] BGMNameList;
    private String[] ESNameList;

    public SettingScreen(final int width, final int height, final int fps) {
        super(width, height, fps);

        // Defaults
        this.settingCode = 1;
        this.returnCode = 7;
        this.BGMCode = 0;
        this.ESCode = 0;

        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();

        if (sm == null) {
            sm = SoundManager.getInstance();
        }
        sm.stopAllBGM();
        BGMNameList = sm.getBGMNameList();
        ESNameList = sm.getESNameList();
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
                if (settingCode == 1)
                    sm.stopAllBGM();
                previousSettingMenu();
                this.selectionCooldown.reset();
                // Sound Operator
                SoundManager.getInstance().playES("menuSelect_es");
            }
            if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
                    || inputManager.isKeyDown(KeyEvent.VK_S)) {
                if (settingCode == 1)
                    sm.stopAllBGM();
                nextSettingMenu();
                this.selectionCooldown.reset();
                // Sound Operator
                SoundManager.getInstance().playES("menuSelect_es");
            }
            if (inputManager.isKeyDown(KeyEvent.VK_Z)) {
                if (settingCode == 1)
                    sm.downBGMVolume(BGMNameList,1);
                else if (settingCode == 2)
                    sm.downESVolume(ESNameList, 1);
                this.selectionCooldown.reset();
                // Sound Operator
                SoundManager.getInstance().playES("menuSelect_es");
            }
            if (inputManager.isKeyDown(KeyEvent.VK_C)) {
                if (settingCode == 1)
                    sm.downBGMVolume(BGMNameList, 5);
                else if (settingCode == 2)
                    sm.downESVolume(ESNameList, 5);;
                this.selectionCooldown.reset();
                // Sound Operator
                SoundManager.getInstance().playES("menuSelect_es");
            }
            if (inputManager.isKeyDown(KeyEvent.VK_X)) {
                if (settingCode == 1)
                    sm.upBGMVolume(BGMNameList, 1);
                else if (settingCode == 2)
                    sm.upESVolume(ESNameList, 1);
                this.selectionCooldown.reset();
                // Sound Operator
                SoundManager.getInstance().playES("menuSelect_es");
            }
            if (inputManager.isKeyDown(KeyEvent.VK_V)) {
                if (settingCode == 1)
                    sm.upBGMVolume(BGMNameList, 5);
                else if (settingCode == 2)
                    sm.upESVolume(ESNameList, 5);
                this.selectionCooldown.reset();

                // Sound Operator
                SoundManager.getInstance().playES("menuSelect_es");
            }

            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                if (settingCode == 0) {
                    this.returnCode = 1;
                    this.isRunning = false;
                }
                else if (settingCode == 1) {
                    this.selectionCooldown.reset();
                    sm.playBGM(BGMNameList[BGMCode]);
                }
                else if (settingCode == 2) {
                    this.selectionCooldown.reset();
                    sm.playES(ESNameList[ESCode]);
                }
            }
            if (settingCode == 1 || settingCode == 2) {
                if (inputManager.isKeyDown(KeyEvent.VK_LEFT)
                        || inputManager.isKeyDown(KeyEvent.VK_A)) {
                    moveMenuLeft();
                    this.selectionCooldown.reset();
                    // Sound Operator
                    SoundManager.getInstance().playES("menuSelect_es");
                }
                if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)
                        || inputManager.isKeyDown(KeyEvent.VK_D)) {
                    moveMenuRight();
                    this.selectionCooldown.reset();
                    // Sound Operator
                    SoundManager.getInstance().playES("menuSelect_es");
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

    private void moveMenuRight() {
        if (this.settingCode == 1) {
            if (this.BGMCode == BGMNameList.length - 1)
                this.BGMCode = 0;
            else
                this.BGMCode++;
        }
        else if (this.settingCode == 2) {
            if (this.ESCode == ESNameList.length - 1)
                this.ESCode = 0;
            else
                this.ESCode++;
        }

    }
    private void moveMenuLeft() {
        if (this.settingCode == 1) {
            if (this.BGMCode == 0)
                this.BGMCode = BGMNameList.length - 1;
            else
                this.BGMCode--;
        }
        else if (this.settingCode == 2) {
            if (this.ESCode == 0)
                this.ESCode = ESNameList.length - 1;
            else
                this.ESCode--;
        }

    }

    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawSettingTitle(this, this.settingCode);
        drawManager.drawSettingMenu(this, this.settingCode, this.BGMCode, this.ESCode);

        super.drawPost();
        drawManager.completeDrawing(this);
    }
}
