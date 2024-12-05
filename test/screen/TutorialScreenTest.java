package screen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for TutorialScreen functionality
 */
public class TutorialScreenTest {

    // Constants for expected screen dimensions and FPS
    private static final int EXPECTED_WIDTH = 800;
    private static final int EXPECTED_HEIGHT = 600;
    private static final int EXPECTED_FPS = 60;

    private TutorialScreen tutorialScreen;

    /**
     * Sets up a new TutorialScreen instance before each test
     */
    @BeforeEach
    void setUp() {
        tutorialScreen = new TutorialScreen(EXPECTED_WIDTH, EXPECTED_HEIGHT, EXPECTED_FPS);
    }

    /**
     * Tests if the initial page is 1 and total pages is correct
     */
    @Test
    void testInitialState() {
        assertEquals(1, tutorialScreen.getCurrentPage());
        assertEquals(4, TutorialScreen.getTotalPages());
    }

    /**
     * Verifies if screen dimensions match expected values
     */
    @Test
    void testScreenDimensions() {
        assertEquals(EXPECTED_WIDTH, tutorialScreen.getWidth());
        assertEquals(EXPECTED_HEIGHT, tutorialScreen.getHeight());
    }

    /**
     * Tests if page numbers are within valid range
     * @param pageNum page number to test from ValueSource
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void testValidPageNumbers(int pageNum) {
        assertTrue(pageNum >= 1 && pageNum <= TutorialScreen.getTotalPages(),
                "Page number " + pageNum + " should be valid");
    }

    /**
     * Verifies constant values and validates screen dimensions are positive
     */
    @Test
    void testConstantValues() {
        assertEquals(4, TutorialScreen.getTotalPages(), "Total pages should be 4");
        assertTrue(tutorialScreen.getWidth() > 0, "Width should be positive");
        assertTrue(tutorialScreen.getHeight() > 0, "Height should be positive");
    }
}