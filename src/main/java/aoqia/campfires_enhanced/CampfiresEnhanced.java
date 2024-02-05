package aoqia.campfires_enhanced;

import aoqia.campfires_enhanced.config.CEConfig;
import aoqia.campfires_enhanced.events.ServerTickEventListener;
import aoqia.campfires_enhanced.items.MatchboxItem;
import aoqia.campfires_enhanced.items.MatchstickItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CampfiresEnhanced implements ModInitializer {
    public static final String MOD_ID = "campfires_enhanced";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final CEConfig CONFIG = CEConfig.createAndLoad();

    private static final ServerTickEventListener SERVER_TICK_EVENT_LISTENER = new ServerTickEventListener();

    public static final MatchboxItem MATCHBOX_ITEM = Registry.register(Registries.ITEM, new Identifier(MOD_ID,
            "matchbox"), new MatchboxItem());
    public static final MatchstickItem MATCHSTICK_ITEM = Registry.register(Registries.ITEM, new Identifier(MOD_ID,
            "matchstick"), new MatchstickItem());

    @Override
    public void onInitialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(MATCHBOX_ITEM);
            content.add(MATCHSTICK_ITEM);
        });

        ServerTickEvents.END_WORLD_TICK.register(SERVER_TICK_EVENT_LISTENER);
    }
}
