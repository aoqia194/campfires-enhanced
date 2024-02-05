package aoqia.campfires_enhanced.items;

import aoqia.campfires_enhanced.CampfiresEnhanced;
import aoqia.campfires_enhanced.nbt.NBTHelper;
import aoqia.campfires_enhanced.nbt.NBTHelperInterface;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static aoqia.campfires_enhanced.CampfiresEnhanced.LOGGER;
import static aoqia.campfires_enhanced.CampfiresEnhanced.MOD_ID;

public class MatchstickItem extends Item implements NBTHelperInterface, ServerTickEvents.EndWorldTick {
    private static final long DEFAULT_BURNING_TICKS = 15 * 20L;
    private static final String BURNING_TICKS_ID = "BurningTicks";

    private static final FabricItemSettings SETTINGS = new FabricItemSettings().maxDamage((int) DEFAULT_BURNING_TICKS);

    private ItemStack lastUsedStack = null;

    public MatchstickItem() {
        super(SETTINGS);
    }

    public ItemStack getLastUsedStack() {
        return this.lastUsedStack;
    }

    public void setLastUsedStack(ItemStack stack) {
        this.lastUsedStack = stack;
    }

    public long getBurningTicks(ItemStack stack) {
        NbtCompound nbt = NBTHelper.getModSubNbtFromItem(stack);
        return nbt.getInt(BURNING_TICKS_ID);
    }

    public void setBurningTicks(ItemStack stack, int ticks) {
        NbtCompound nbt = NBTHelper.getModSubNbtFromItem(stack);
        nbt.putInt(BURNING_TICKS_ID, ticks);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = super.getDefaultStack();

        NbtCompound subNbt = stack.getSubNbt(MOD_ID);
        if (subNbt == null) {
            stack.setSubNbt(MOD_ID, this.getDefaultModSubNbt());
        }

        assert (subNbt != null);
        return stack;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient()) {
            BlockPos pos = context.getBlockPos();
            BlockState state = world.getBlockState(pos);

            if (state.isOf(Blocks.CAMPFIRE)) {
                BlockState newState = Blocks.CAMPFIRE.getStateWithProperties(state).with(CampfireBlock.LIT, true);
                world.setBlockState(pos, newState);
                this.setLastUsedStack(context.getStack());
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }

    @Override
    public NbtCompound getDefaultModSubNbt() {
        NbtCompound subNbt = new NbtCompound();
        subNbt.putLong(BURNING_TICKS_ID, DEFAULT_BURNING_TICKS);
        return subNbt;
    }

    @Override
    public void onEndTick(ServerWorld world) {
        final ItemStack stack = this.getLastUsedStack();
        if (stack == null) {
            return;
        }
        NBTHelper.getModSubNbtFromItem(stack);

        // FIXME: Understand how the fuck this works.
        //  We are getting burning ticks (starts at 300 in NBT) then we are setting the damage to burning ticks + 1.
        //  THEN we are setting the burning ticks to burning ticks - 1.
        //  What. The. Fuck.
        final long burningTicks = this.getBurningTicks(stack);
        if (burningTicks > 0) {
            stack.setDamage((int) (burningTicks + 1L));
            this.setBurningTicks(stack, (int) (burningTicks - 1L));
        }
    }
}
