package aoqia.campfires_enhanced.access;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public interface CECampfireBlockEntity {
    void campfires_enhanced$dirtSpread(World world, BlockPos pos);
    void campfires_enhanced$fireSpread(World world, BlockPos pos);
}
