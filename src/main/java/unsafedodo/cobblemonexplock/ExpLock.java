package unsafedodo.cobblemonexplock;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unsafedodo.cobblemonexplock.common.RegisterCommands;

public class ExpLock implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("cobblemon-explock");

	@Override
	public void onInitialize() {
		LOGGER.info("cobblemon-explock loaded!");

		RegisterCommands.register();
	}
}