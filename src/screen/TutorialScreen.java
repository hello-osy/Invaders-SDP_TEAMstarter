package screen;

import java.awt.event.KeyEvent;
import java.awt.Color;
import engine.Core;
import engine.DrawManager;
import engine.DrawManager.SpriteType;
import engine.InputManager;
import entity.Entity;
import Sound_Operator.SoundManager;
import entity.EnemyShip;

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

        drawManager.backBufferGraphics.setColor(Color.BLACK);
        drawManager.backBufferGraphics.fillRect(0, 0, this.width, this.height);

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
        int startY = 120;

        // Basic Enemies
        drawManager.backBufferGraphics.setColor(Color.GREEN);
        drawManager.drawCenteredRegularString(this, "BASIC ENEMIES", startY);
        startY += 40;

        // Create and draw basic enemies
        EnemyShip enemyA = new EnemyShip(width/3, startY, SpriteType.EnemyShipA1);
        EnemyShip enemyB = new EnemyShip(width/2, startY, SpriteType.EnemyShipB1);
        EnemyShip enemyC = new EnemyShip(2*width/3, startY, SpriteType.EnemyShipC1);

        // Draw basic enemies
        drawManager.drawEntity(enemyA, enemyA.getPositionX(), enemyA.getPositionY());
        drawManager.drawEntity(enemyB, enemyB.getPositionX(), enemyB.getPositionY());
        drawManager.drawEntity(enemyC, enemyC.getPositionX(), enemyC.getPositionY());

        // Draw descriptions
        startY += 50;
        drawManager.backBufferGraphics.setColor(Color.WHITE);
        drawManager.drawCenteredRegularString(this, "Type A: 1 point", startY);
        startY += 30;
        drawManager.drawCenteredRegularString(this, "Type B: 2 points", startY);
        startY += 30;
        drawManager.drawCenteredRegularString(this, "Type C: 3 points", startY);

        // Special Enemies
        startY += 50;
        drawManager.backBufferGraphics.setColor(Color.GREEN);
        drawManager.drawCenteredRegularString(this, "SPECIAL ENEMIES", startY);
        startY += 40;

        // Create and draw explosive enemy
        EnemyShip explosive = new EnemyShip(width/3, startY, SpriteType.ExplosiveEnemyShip1);
        drawManager.drawEntity(explosive, explosive.getPositionX(), explosive.getPositionY());

        // Create and draw UFO
        EnemyShip ufo = new EnemyShip();  // Special UFO constructor
        ufo.setPositionX(2*width/3);
        ufo.setPositionY(startY);
        drawManager.drawEntity(ufo, ufo.getPositionX(), ufo.getPositionY());

        // Draw special enemies descriptions
        startY += 50;
        drawManager.backBufferGraphics.setColor(Color.WHITE);
        drawManager.drawCenteredRegularString(this, "Explosive: 5 points - Chain reaction", startY);
        startY += 30;
        drawManager.drawCenteredRegularString(this, "UFO: 10 points - Bonus enemy", startY);
    }

    private void drawPowerUpsAndItems() {
        drawManager.drawCenteredBigString(this, "Power-ups & Items", 50);

        int startY = 120;
        String[] items = {
                "POWER-UPS",
                "",
                "Shield (Barrier) - Protects from attacks",
                "Bullet Pierce - Penetrates enemies",
                "Speed Up - Increases movement speed",
                "Bomb - Area explosion damage",
                "",
                "COLLECTIBLES",
                "",
                "Coins - Currency for upgrades",
                "Gems - Special upgrade currency",
                "Hearts - Restore lives"
        };

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

        int startY = 120;
        String[] tips = {
                "SCORING",
                "",
                "Regular Enemy: 1-3 points",
                "Explosive Enemy: 5 points",
                "Bonus UFO: 10 points",
                "",
                "TIPS",
                "",
                "- Watch enemy patterns",
                "- Clear bottom enemies first",
                "- Save power-ups for tough situations",
                "- Aim for high accuracy bonus"
        };

        for (String tip : tips) {
            if (tip.startsWith("SCORING") || tip.startsWith("TIPS")) {
                drawManager.backBufferGraphics.setColor(Color.GREEN);
            } else {
                drawManager.backBufferGraphics.setColor(Color.WHITE);
            }
            drawManager.drawCenteredRegularString(this, tip, startY);
            startY += 30;
        }
    }
}