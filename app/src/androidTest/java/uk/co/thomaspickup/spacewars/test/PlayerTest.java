package uk.co.thomaspickup.spacewars.test;

import android.content.Context;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.thomaspickup.spacewars.game.SettingsHandler;
import uk.co.thomaspickup.spacewars.game.SpaceGame;
import uk.co.thomaspickup.spacewars.game.spaceLevel.PlayerSpaceship;
import static android.support.test.InstrumentationRegistry.getContext;

/**
 * Game related tests.
 *
 * Created by Thomas Pickup.
 */
public class PlayerTest {
    private Context testContext;
    private SettingsHandler settingsHandler;
    private PlayerSpaceship playerSpaceship;
    private SpaceGame spaceGame;

    public PlayerTest() {
        super();
    }

    /**
     * Sets up the variables prior to testing
     */
    @Before
    public void preTest() {
        // Creates a test gamescreen
        spaceGame = new SpaceGame();

        // Gets the context for the test runner
        testContext = getContext();

        // Creates a new instance of settingsHandler
        settingsHandler = new SettingsHandler();
    }

    /**
     * Test that the settings handler can save and recover settings stored.
     */
    @Test
    public void testSettingsSetGet() {
        // Sets sound as 1 expects to get 1
        settingsHandler.setSound(testContext, 1);
        int expectedResult = 1;

        // Tests to see if recovery of setting is correct
        Assert.assertEquals(expectedResult, settingsHandler.getSound(testContext));

        // Sets difficulty as 4 expects to get 4
        settingsHandler.setDifficulty(testContext, 2);
        expectedResult = 2;

        // Tests to see if recovery of setting is correct
        Assert.assertEquals(expectedResult, settingsHandler.getDifficulty(testContext));
    }


}
