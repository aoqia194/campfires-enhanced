package aoqia.campfires_enhanced.mixin;

import aoqia.campfires_enhanced.access.CECampfireBlockEntity;
import aoqia.campfires_enhanced.access.CampfireBlockEntityTimer;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static aoqia.campfires_enhanced.CampfiresEnhanced.CONFIG;

@Mixin(CampfireBlockEntity.class)
public class CampfireBlockEntityMixin implements CampfireBlockEntityTimer, CECampfireBlockEntity {
    @Unique
    private static final long DIRTSPREAD_TICKS = CONFIG.dirtSpreadTicks();
//    @Unique
//    private static final long FIRESPREAD_TICKS_MIN = 5 * 20L;
//    @Unique
//    private static final long FIRESPREAD_TICKS_MAX = 25 * 20L;
//    @Unique
//    private static final long FIRESPREAD_DISTANCE = 2;

    @Unique
    private long ticksElapsed = 0L;

    @Override
    public long campfires_enhanced$getElapsedTicks() {
        return ticksElapsed;
    }

    @Override
    public void campfires_enhanced$setElapsedTicks(long ticks) {
        ticksElapsed = ticks;
    }

    @Override
    public void campfires_enhanced$increaseElapsedTicks() {
        ++ticksElapsed;
    }

    @Override
    public void campfires_enhanced$decreaseElapsedTicks() {
        --ticksElapsed;
    }

    @Override
    public void campfires_enhanced$resetElapsedTicks() {
        ticksElapsed = 0L;
    }

    @Override
    public void campfires_enhanced$dirtSpread(World world, BlockPos pos) {
        BlockPos bottomPos = pos.down(1);
        BlockState bottomState = world.getBlockState(bottomPos);

        if (bottomState.isOf(Blocks.GRASS_BLOCK)) {
            world.setBlockState(bottomPos, Blocks.DIRT.getDefaultState());
        } else if (bottomState.isOf(Blocks.DIRT)) {
            for (BlockPos blockPos : BlockPos.iterateOutwards(bottomPos, 1, 0, 1)) {
                if (world.getBlockState(blockPos).isOf(Blocks.GRASS_BLOCK)) {
                    world.setBlockState(blockPos, Blocks.DIRT.getDefaultState());
                }
            }
        }
    }

    @Override
    public void campfires_enhanced$fireSpread(World world, BlockPos pos) {
        Random random = world.getRandom();

        //        if (world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
//            if (access.getElapsedTicks() % worldRandom.nextBetweenExclusive((int) FIRESPREAD_TICKS_MIN, (int)
//            FIRESPREAD_TICKS_MAX) == 0L) {
//                for (BlockPos blockPos : BlockPos.iterateOutwards(pos, (int) FIRESPREAD_DISTANCE, (int)
//                FIRESPREAD_DISTANCE, (int) FIRESPREAD_DISTANCE)) {
//                    if (worldRandom.nextFloat() < 0.1F) {
//                        BlockState blockState = world.getBlockState(blockPos);
//                        if (blockState.isBurnable()) {
//                            world.setBlockState(blockPos.up(), AbstractFireBlock.getState(world, blockPos));
//                        }
//                    }
//                }
//            }
//        }

        // NOTE: Implementation taken from LavaFluid#onRandomTick
        if (world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
            int i = random.nextInt(3);
            if (i > 0) {
                BlockPos blockPos = pos;

                for (int j = 0; j < i; ++j) {
                    blockPos = blockPos.add(random.nextInt(3) - 1, 1, random.nextInt(3) - 1);
                    if (!world.canSetBlock(blockPos)) {
                        return;
                    }

                    BlockState blockState = world.getBlockState(blockPos);
                    if (blockState.isAir()) {
                        if (world.getBlockState(blockPos).isBurnable()) {
                            world.setBlockState(blockPos, AbstractFireBlock.getState(world, blockPos));
                            return;
                        }
                    } else if (blockState.blocksMovement()) { // FIXME: Deprecated
                        return;
                    }
                }
            } else {
                for (int k = 0; k < 3; ++k) {
                    BlockPos blockPos2 = pos.add(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
                    if (!world.canSetBlock(blockPos2)) {
                        return;
                    }

                    if (world.isAir(blockPos2.up()) && world.getBlockState(blockPos2).isBurnable()) {
                        world.setBlockState(blockPos2.up(), AbstractFireBlock.getState(world, blockPos2));
                    }
                }
            }
        }
    }

    @Inject(method = "litServerTick", at = @At("TAIL"))
    private static void litServerTick(World world, BlockPos pos, BlockState state, CampfireBlockEntity campfire,
                                      CallbackInfo ci) {
        CampfireBlockEntityTimer timer = (CampfireBlockEntityTimer) campfire;
        CECampfireBlockEntity customCampfire = (CECampfireBlockEntity) campfire;

        // This only happens when the elapsedTicks is divisible by DIRTSPREAD_TICKS and returns a whole number.
        if (timer.campfires_enhanced$getElapsedTicks() % DIRTSPREAD_TICKS == 0L) {
            customCampfire.campfires_enhanced$dirtSpread(world, pos);
        }

        customCampfire.campfires_enhanced$fireSpread(world, pos);

        timer.campfires_enhanced$increaseElapsedTicks();
    }
}
