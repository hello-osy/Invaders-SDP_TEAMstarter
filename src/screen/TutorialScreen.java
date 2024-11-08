package screen;

import java.awt.event.KeyEvent;
import java.awt.Color;
import engine.Core;
import engine.DrawManager;
import engine.InputManager;
import Sound_Operator.SoundManager;

public class TutorialScreen extends Screen {
    private static final int SELECTION_TIME = 200;
    private int currentPage;
    private static final int TOTAL_PAGES = 4;
    private boolean spacePressed;
    private boolean isInitialized;

    public TutorialScreen(final int width, final int height, final int fps) {
        super(width, height, fps);
        this.currentPage = 1;
        this.spacePressed = false;
        this.isInitialized = false;
    }

    public final int run() {
        super.run();
        return this.returnCode;
    }

    protected final void update() {
        super.update();

        if (!isInitialized) {
            spacePressed = true;
            isInitialized = true;
            draw();
            return;
        }

        if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) {
            this.isRunning = false;
            this.returnCode = 1;  // return to main menu
        }

        if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
            if (!spacePressed) {
                if (currentPage < TOTAL_PAGES) {
                    currentPage++;
                } else {
                    currentPage = 1;
                }
                SoundManager.getInstance().playES("menuSelect_es");
                spacePressed = true;
            }
        } else {
            spacePressed = false;
        }

        draw();
    }

    private void draw() {
        drawManager.initDrawing(this);

        switch (currentPage) {
            case 1:
                drawBasicControls();
                break;
            case 2:
                drawEnemyTypes();
                break;
            case 3:
                drawPowerUpsAndItems();
                break;
            case 4:
                drawScoringAndTips();
                break;
        }

        drawManager.drawCenteredRegularString(this,
                "Press SPACE to next page (" + currentPage + "/" + TOTAL_PAGES + ")",
                getHeight() - 50);
        drawManager.drawCenteredRegularString(this,
                "Press ESC to return to main menu",
                getHeight() - 25);

        drawManager.completeDrawing(this);
    }

    private void drawBasicControls() {
        drawManager.drawCenteredBigString(this, "Basic Controls", 50);

        String[] controls = {
                "MOVEMENT",
                "",
                "Move Left : A, left arrow, 4(keypad)",
                "Move Right : D, right arrow, 6(keypad)",
                "",
                "GAME CONTROLS",
                "",
                "Attack : Enter",
                "Quit Game : ESC"
        };

        int startY = 120;
        for (String control : controls) {
            if (control.startsWith("MOVEMENT") || control.startsWith("GAME CONTROLS")) {
                drawManager.backBufferGraphics.setColor(Color.GREEN);
            } else {
                drawManager.backBufferGraphics.setColor(Color.WHITE);
            }
            drawManager.drawCenteredRegularString(this, control, startY);
            startY += 30;
        }
    }

    private void drawEnemyTypes() {
        drawManager.drawCenteredBigString(this, "Enemy Types", 50);

        String[] enemies = {
                "REGULAR ENEMIES",
                "Small ships that move in formation",
                "Watch out for their bullet patterns!",
                "",
                "SPECIAL ENEMIES",
                "Bonus UFO appears at the top",
                "Destroy it quickly for extra points!",
                "",
                "BOSS BATTLES",
                "Powerful enemies with unique attacks",
                "Look for patterns to defeat them"
        };

        int startY = 120;
        for (String enemy : enemies) {
            if (enemy.startsWith("REGULAR") || enemy.startsWith("SPECIAL") || enemy.startsWith("BOSS")) {
                drawManager.backBufferGraphics.setColor(Color.GREEN);
            } else {
                drawManager.backBufferGraphics.setColor(Color.WHITE);
            }
            drawManager.drawCenteredRegularString(this, enemy, startY);
            startY += 30;
        }
    }

    private void drawPowerUpsAndItems() {
        drawManager.drawCenteredBigString(this, "Power-ups & Items", 50);

        String[] items = {
                "POWER-UPS",
                "bullet count up - increase the number of bullets",
                "ship speed up - increase the speed of ship movement",
                "attack speed up - reduce attack delay",
                "coin gain up - increase acquisition in treatment",
                "",
                "COLLECTIBLES",
                "Coins - Collect to upgrade your ship",
                "Gems - Special currency for rare upgrades",
                "",
                "Use power-ups wisely to survive longer!"
        };

        int startY = 120;
        for (String item : items) {
            if (item.startsWith("POWER-UPS") || item.startsWith("COLLECTIBLES")) {
                drawManager.backBufferGraphics.setColor(Color.GREEN);
            } else {
                drawManager.backBufferGraphics.setColor(Color.WHITE);
            }
            drawManager.drawCenteredRegularString(this, item, startY);
            startY += 30;
        }
    }

    private void drawScoringAndTips() {
        drawManager.drawCenteredBigString(this, "Scoring & Tips", 50);

        String[] tips = {
                "SCORING SYSTEM",
                "Regular Enemy: 000 points",
                "Special UFO: 000 points",
                "Boss Defeat: 000 points",
                "",
                "SURVIVAL TIPS",
                "Stay mobile to avoid enemy fire",
                "Clear bottom rows first",
                "Save power-ups for tough situations",
                "Watch out for enemy formation changes!"
        };

        int startY = 120;
        for (String tip : tips) {
            if (tip.startsWith("SCORING") || tip.startsWith("SURVIVAL")) {
                drawManager.backBufferGraphics.setColor(Color.GREEN);
            } else {
                drawManager.backBufferGraphics.setColor(Color.WHITE);
            }
            drawManager.drawCenteredRegularString(this, tip, startY);
            startY += 30;
        }
    }
}