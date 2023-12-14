package aoqia.campfires_enhanced.mixin;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.BooleanProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// import static aoqia.campfires_enhanced.CampfiresEnhanced.CONFIG;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {
    // Vanilla block properties
    @Shadow
    @Final
    public static BooleanProperty LIT;

    // Modified block properties
    private static BooleanProperty HAS_FUEL;

    @Inject(method = "getPlacementState", at = @At("RETURN"), cancellable = true)
    private void getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        // if (!CONFIG.unlitCampfires()) {
        //     return;
        // }

        BlockState state = cir.getReturnValue();
        if (state.isOf(Blocks.CAMPFIRE)) {
            cir.setReturnValue(state.with(LIT, false));
        }
    }
}
