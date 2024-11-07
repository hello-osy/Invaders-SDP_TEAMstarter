package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import HUDTeam.DrawAchievementHud;
import HUDTeam.DrawManagerImpl;
import clove.Achievement;
import engine.Core;
import engine.Score;
// Sound Operator
import Sound_Operator.SoundManager;


public class AchievementScreen extends Screen {

    /** List of past high scores. */
    private List<Achievement> achievements;

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
    public AchievementScreen(final int width, final int height, final int fps) {
        super(width, height, fps);

        this.returnCode = 1;

        // Sound Operator
        SoundManager.getInstance().playBGM("highScore_bgm");

//        try {
//            this.highScores = Core.getFileManager().loadHighScores();
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
//        drawManager.drawHighScores(this, this.highScores);

        super.drawPost();
        drawManager.completeDrawing(this);
    }
}
