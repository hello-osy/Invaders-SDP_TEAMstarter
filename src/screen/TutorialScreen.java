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
    private static final int TOTAL_PAGES = 4;
    private static final int LINE_HEIGHT = 30;
    private static final int INITIAL_Y = 120;
    private static final Color TITLE_COLOR = Color.GREEN;
    private static final Color TEXT_COLOR = Color.WHITE;

    private static final String[] CONTROLS = {
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

    private static final String[] ITEMS = {
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

    private static final String[] TIPS = {
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

    private int currentPage;
    private boolean spacePressed;
    private boolean isInitialized;

    private final EnemyShip enemyA;
    private final EnemyShip enemyB;
    private final EnemyShip enemyC;
    private final EnemyShip explosiveEnemy;
    private final EnemyShip ufoEnemy;

    public TutorialScreen(final int width, final int height, final int fps) {
        super(width, height, fps);
        this.currentPage = 1;
        this.spacePressed = false;
        this.isInitialized = false;

        // Initialize enemies once
        enemyA = new EnemyShip(width/3, INITIAL_Y + 40, SpriteType.EnemyShipA1);
        enemyB = new EnemyShip(width/2, INITIAL_Y + 40, SpriteType.EnemyShipB1);
        enemyC = new EnemyShip(2*width/3, INITIAL_Y + 40, SpriteType.EnemyShipC1);
        explosiveEnemy = new EnemyShip(width/3, INITIAL_Y + 230, SpriteType.ExplosiveEnemyShip1);
        ufoEnemy = new EnemyShip();
        ufoEnemy.setPositionX(2*width/3);
        ufoEnemy.setPositionY(INITIAL_Y + 230);
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
            this.returnCode = 1;
        }

        if (inputManager.isKeyDown(KeyEvent.VK_SPACE) && !spacePressed) {
            currentPage = currentPage < TOTAL_PAGES ? currentPage + 1 : 1;
            SoundManager.getInstance().playES("menuSelect_es");
            spacePressed = true;
        } else if (!inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
            spacePressed = false;
        }

        draw();
    }

    private void draw() {
        drawManager.initDrawing(this);
        drawManager.backBufferGraphics.setColor(Color.BLACK);
        drawManager.backBufferGraphics.fillRect(0, 0, this.width, this.height);

        switch (currentPage) {
            case 1 -> drawBasicControls();
            case 2 -> drawEnemyTypes();
            case 3 -> drawPowerUpsAndItems();
            case 4 -> drawScoringAndTips();
        }

        drawNavigationText();
        drawManager.completeDrawing(this);
    }

    private void drawNavigationText() {
        int bottomY = getHeight() - 50;
        drawManager.drawCenteredRegularString(this,
                "Press SPACE to next page (" + currentPage + "/" + TOTAL_PAGES + ")",
                bottomY);
        drawManager.drawCenteredRegularString(this,
                "Press ESC to return to main menu",
                bottomY + 25);
    }

    private void drawTextLines(String[] lines, String title) {
        drawManager.drawCenteredBigString(this, title, 50);
        int y = INITIAL_Y;

        for (String line : lines) {
            drawManager.backBufferGraphics.setColor(
                    line.startsWith("MOVEMENT") ||
                            line.startsWith("GAME CONTROLS") ||
                            line.startsWith("POWER-UPS") ||
                            line.startsWith("COLLECTIBLES") ||
                            line.startsWith("SCORING") ||
                            line.startsWith("TIPS") ? TITLE_COLOR : TEXT_COLOR
            );
            drawManager.drawCenteredRegularString(this, line, y);
            y += LINE_HEIGHT;
        }
    }

    private void drawBasicControls() {
        drawTextLines(CONTROLS, "Basic Controls");
    }

    private void drawEnemyTypes() {
        drawManager.drawCenteredBigString(this, "Enemy Types", 50);
        int y = INITIAL_Y;

        // Basic Enemies section
        drawManager.backBufferGraphics.setColor(TITLE_COLOR);
        drawManager.drawCenteredRegularString(this, "BASIC ENEMIES", y);

        // Draw enemies
        drawManager.drawEntity(enemyA, enemyA.getPositionX(), enemyA.getPositionY());
        drawManager.drawEntity(enemyB, enemyB.getPositionX(), enemyB.getPositionY());
        drawManager.drawEntity(enemyC, enemyC.getPositionX(), enemyC.getPositionY());

        // Basic enemies descriptions
        y += LINE_HEIGHT * 3;
        drawManager.backBufferGraphics.setColor(TEXT_COLOR);
        String[] basicDesc = {"Type A: 1 point", "Type B: 2 points", "Type C: 3 points"};
        for (String desc : basicDesc) {
            drawManager.drawCenteredRegularString(this, desc, y);
            y += LINE_HEIGHT;
        }

        // Special Enemies section
        y += LINE_HEIGHT;
        drawManager.backBufferGraphics.setColor(TITLE_COLOR);
        drawManager.drawCenteredRegularString(this, "SPECIAL ENEMIES", y);

        // Draw special enemies
        drawManager.drawEntity(explosiveEnemy, explosiveEnemy.getPositionX(), explosiveEnemy.getPositionY());
        drawManager.drawEntity(ufoEnemy, ufoEnemy.getPositionX(), ufoEnemy.getPositionY());

        // Special enemies descriptions
        y += LINE_HEIGHT * 3;
        drawManager.backBufferGraphics.setColor(TEXT_COLOR);
        drawManager.drawCenteredRegularString(this, "Explosive: 5 points - Chain reaction", y);
        drawManager.drawCenteredRegularString(this, "UFO: 10 points - Bonus enemy", y + LINE_HEIGHT);
    }

    private void drawPowerUpsAndItems() {
        drawTextLines(ITEMS, "Power-ups & Items");
    }

    private void drawScoringAndTips() {
        drawTextLines(TIPS, "Scoring & Tips");
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public static int getTotalPages() {
        return TOTAL_PAGES;
    }
}
