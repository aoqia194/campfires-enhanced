package aoqia.campfires_enhanced.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

import static aoqia.campfires_enhanced.CampfiresEnhanced.MOD_ID;

@Modmenu(modId = MOD_ID)
@Config(name = MOD_ID, wrapperName = "CEConfig")
public class CEConfigModel {
    public boolean isEnabled = true;

    public boolean doUnlitCampfires = true;

    public boolean doFuelSystem = true;

    public boolean doDirtSpread = true;
    public long dirtSpreadTicks = 10 * 20L;
}
