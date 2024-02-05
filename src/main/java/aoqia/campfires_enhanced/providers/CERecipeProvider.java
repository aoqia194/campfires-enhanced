package aoqia.campfires_enhanced.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import static aoqia.campfires_enhanced.CampfiresEnhanced.MATCHBOX_ITEM;
import static aoqia.campfires_enhanced.CampfiresEnhanced.MATCHSTICK_ITEM;

public class CERecipeProvider extends FabricRecipeProvider {
    public CERecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, MATCHBOX_ITEM)
                .input(MATCHBOX_ITEM)
                .input(MATCHSTICK_ITEM)
                .criterion(FabricRecipeProvider.hasItem(MATCHBOX_ITEM),
                        FabricRecipeProvider.conditionsFromItem(MATCHBOX_ITEM))
                .criterion(FabricRecipeProvider.hasItem(MATCHSTICK_ITEM),
                        FabricRecipeProvider.conditionsFromItem(MATCHSTICK_ITEM))
                .offerTo(exporter, new Identifier(getRecipeName(MATCHBOX_ITEM)));
    }
}
