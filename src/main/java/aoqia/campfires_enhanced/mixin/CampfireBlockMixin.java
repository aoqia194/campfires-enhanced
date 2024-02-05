package aoqia.campfires_enhanced.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static aoqia.campfires_enhanced.CampfiresEnhanced.CONFIG;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {
    // Vanilla block properties
    @Shadow
    @Final
    public static BooleanProperty LIT;

    @Inject(method = "getPlacementState", at = @At("RETURN"), cancellable = true)
    private void getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        if (!CONFIG.isEnabled()) {
            return;
        }

        // NOTE: I don't think this requires a block check
        BlockState state = cir.getReturnValue();
        if (state.isOf(Blocks.CAMPFIRE)) {
            if (CONFIG.doUnlitCampfires()) {
                cir.setReturnValue(state.with(LIT, false));
            }
        }
    }
}
