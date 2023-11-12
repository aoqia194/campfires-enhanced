package aoqia.campfires_enhanced;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CampfiresEnhancedClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("campfires_enhanced");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Client initialised.");
    }
}
