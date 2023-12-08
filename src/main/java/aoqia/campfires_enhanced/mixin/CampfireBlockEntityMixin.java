package aoqia.campfires_enhanced.mixin;

import aoqia.campfires_enhanced.access.CampfireBlockEntityAccess;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static aoqia.campfires_enhanced.CampfiresEnhanced.LOGGER;

@Mixin(CampfireBlockEntity.class)
public class CampfireBlockEntityMixin implements CampfireBlockEntityAccess {
    @Unique
    private long ticksElapsed = 0L;

    private static long DIRT_SPREAD_SECONDS = 10;

    @Override
    public long getElapsedTicks() {
        return ticksElapsed;
    }

    @Override
    public void setElapsedTicks(long ticks) {
        ticksElapsed = ticks;
    }

    @Override
    public void increaseElapsedTicks() {
        ++ticksElapsed;
    }

    @Override
    public void decreaseElapsedTicks() {
        --ticksElapsed;
    }

    @Override
    public void resetElapsedTicks() {
        ticksElapsed = 0L;
    }

    // This will work for now, but if we want to mark as dirty, then we have to call it again?
    @Inject(method = "litServerTick", at = @At("TAIL"))
    private static void litServerTick(World world, BlockPos pos, BlockState state, CampfireBlockEntity campfire, CallbackInfo ci) {
        CampfireBlockEntityAccess access = (CampfireBlockEntityAccess) campfire;
        access.increaseElapsedTicks();

        if (access.getElapsedTicks() % (10 * 20L) == 0L) {
            BlockPos bottomPos = pos.down(1);

            BlockState bottomState = world.getBlockState(bottomPos);
            if (bottomState.isOf(Blocks.GRASS_BLOCK)) {
                LOGGER.info("Bottom block is grass. Setting to dirt.");
                world.setBlockState(bottomPos, Blocks.DIRT.getDefaultState());
            } else if (bottomState.isOf(Blocks.DIRT)) {
                LOGGER.info("Bottom block is dirt. Setting surrounding to dirt.");
                BlockPos.iterateOutwards(bottomPos, 1, 0, 1).forEach(blockPos -> {
                    if (world.getBlockState(blockPos).isOf(Blocks.GRASS_BLOCK)) {
                        world.setBlockState(blockPos, Blocks.DIRT.getDefaultState());
                    }
                });
            }
        }
    }
}
