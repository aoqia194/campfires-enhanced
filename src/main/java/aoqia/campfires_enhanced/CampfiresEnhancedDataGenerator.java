package aoqia.campfires_enhanced;

import aoqia.campfires_enhanced.providers.CEEnglishLanguageProvider;
import aoqia.campfires_enhanced.providers.CERecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class CampfiresEnhancedDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(CEEnglishLanguageProvider::new);
        pack.addProvider(CERecipeProvider::new);
    }
}
