package aoqia.campfires_enhanced;

// Disabled because no 1.20.4 for owolib
// import aoqia.campfires_enhanced.config.CEConfig;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CampfiresEnhanced implements ModInitializer {
    public static final String MOD_ID = "campfires_enhanced";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    // public static final CEConfig CONFIG = CEConfig.createAndLoad();

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric world!");
    }
}
