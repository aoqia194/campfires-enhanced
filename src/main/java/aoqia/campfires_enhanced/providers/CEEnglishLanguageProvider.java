package aoqia.campfires_enhanced.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import static aoqia.campfires_enhanced.CampfiresEnhanced.MATCHBOX_ITEM;
import static aoqia.campfires_enhanced.CampfiresEnhanced.MATCHSTICK_ITEM;

public class CEEnglishLanguageProvider extends FabricLanguageProvider {
    public CEEnglishLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        builder.add(MATCHBOX_ITEM, "Matchbox");
        builder.add(MATCHSTICK_ITEM, "Matchstick");
    }
}
