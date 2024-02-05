package aoqia.campfires_enhanced.items;

import aoqia.campfires_enhanced.nbt.NBTHelper;
import aoqia.campfires_enhanced.nbt.NBTHelperInterface;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static aoqia.campfires_enhanced.CampfiresEnhanced.MATCHSTICK_ITEM;
import static aoqia.campfires_enhanced.CampfiresEnhanced.MOD_ID;

public class MatchboxItem extends Item implements NBTHelperInterface {
    private static final FabricItemSettings SETTINGS = new FabricItemSettings().maxCount(1);

    private static final int DEFAULT_MATCHSTICK_COUNT = 5;

    private static final String MATCHSTICK_COUNT_ID = "MatchstickCount";

    public MatchboxItem() {
        super(SETTINGS);
    }

    public MatchboxItem(int matchstickCount) {
        super(SETTINGS);

        ItemStack stack = this.getDefaultStack();
        this.setInnerCount(stack, matchstickCount);
    }

    public int getInnerCount(ItemStack stack) {
        NbtCompound nbt = NBTHelper.getModSubNbtFromItem(stack);
        return nbt.getInt(MATCHSTICK_COUNT_ID);
    }

    public void setInnerCount(ItemStack stack, int count) {
        NbtCompound nbt = NBTHelper.getModSubNbtFromItem(stack);
        nbt.putInt(MATCHSTICK_COUNT_ID, count);
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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient()) {
            MatchboxItem item = (MatchboxItem) stack.getItem();

            int innerCount = item.getInnerCount(stack);
            if (innerCount > 0) {
                item.setInnerCount(stack, innerCount - 1);
                user.getInventory().offerOrDrop(new ItemStack(MATCHSTICK_ITEM));
                user.playSound(SoundEvents.BLOCK_BAMBOO_STEP, 1.0F, 1.0F);

                return TypedActionResult.success(stack);
            }
        }

        return TypedActionResult.pass(stack);
    }

    @Override
    public NbtCompound getDefaultModSubNbt() {
        NbtCompound subNbt = new NbtCompound();
        subNbt.putInt(MATCHSTICK_COUNT_ID, DEFAULT_MATCHSTICK_COUNT);
        return subNbt;
    }
}
