package aoqia.campfires_enhanced.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;

import static aoqia.campfires_enhanced.CampfiresEnhanced.MATCHSTICK_ITEM;

public class ServerTickEventListener implements ServerTickEvents.EndWorldTick {
    @Override
    public void onEndTick(ServerWorld world) {
        MATCHSTICK_ITEM.onEndTick(world);
    }
}
