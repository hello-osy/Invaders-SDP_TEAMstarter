package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import HUDTeam.DrawAchievementHud;
import HUDTeam.DrawManagerImpl;
import clove.Achievement;
import engine.Core;
import engine.Score;
import clove.AchievementConditions;
import clove.AchievementManager;
// Sound Operator
import Sound_Operator.SoundManager;




public class AchievementScreen extends Screen {

    /** List of past high scores. */
    private ArrayList<Achievement> achievements;
    private ArrayList<String> unlockedachivements;

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
    public AchievementScreen(final int width, final int height, final int fps, AchievementManager achievementManager) {
        super(width, height, fps);

        this.returnCode = 1;

        // Sound Operator
        SoundManager.getInstance().playBGM("highScore_bgm");

        this.achievements = (ArrayList<Achievement>) achievementManager.getAllAchievement();
        this.unlockedachivements = (ArrayList<String>) achievementManager.getUnlockedAchievement();
//        try {
//            this.achievements =
//        } catch (NumberFormatException | IOException e) {
//            logger.warning("Couldn't load high scores!");
//        }
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
        if (inputManager.isKeyDown(KeyEvent.VK_SPACE)
                && this.inputDelay.checkFinished())
            this.isRunning = false;
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);


        drawManager.drawAchievementMenu(this);
        drawManager.drawAchievement(this, this.achievements, this.unlockedachivements);

        super.drawPost();
        drawManager.completeDrawing(this);
    }
}
