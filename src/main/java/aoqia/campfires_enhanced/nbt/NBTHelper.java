package aoqia.campfires_enhanced.nbt;

import aoqia.campfires_enhanced.CampfiresEnhanced;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import static aoqia.campfires_enhanced.CampfiresEnhanced.LOGGER;
import static aoqia.campfires_enhanced.CampfiresEnhanced.MOD_ID;

public class NBTHelper {
    private NBTHelper() {
        throw new IllegalStateException("NBTHelper is a utility class with static methods.");
    }

    public static NbtCompound getModSubNbtFromItem(ItemStack stack) {
        if (stack.getNbt() == null) {
            LOGGER.info("[{}] Tried to get item nbt but failed! Creating new nbt data...",
                    stack.getName().getContent());
            stack.setNbt(stack.getItem().getDefaultStack().getNbt());
        }

        NbtCompound nbt = stack.getSubNbt(MOD_ID);
        if (nbt == null) {
            LOGGER.info("Creating new NBT data didn't work! Mod specific NBT data is still blank. Manually inserting mod specific NBT data...");

            if (stack.getItem() instanceof NBTHelperInterface) {
                stack.setSubNbt(MOD_ID, ((NBTHelperInterface) stack.getItem()).getDefaultModSubNbt());
            } else {
                LOGGER.warn("[{}] Tried to get mod-specific sub-nbt from item but failed because the item doesn't implement NBTHelperInterface!!!", stack.getName().getContent());
            }
        }

        assert (nbt != null);
        LOGGER.debug("getModSubNbtFromItem returned nbt data: " + nbt);
        return nbt;
    }
}
