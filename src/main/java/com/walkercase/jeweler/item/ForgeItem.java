package com.walkercase.jeweler.item;

import com.google.gson.JsonObject;
import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.AssetAPI;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Used in an anvil to create different items.
 */
public class ForgeItem extends Item {

    /**
     * Create a new ForgeItem.
     * By nature only one of these should ever be created.
     */
    public ForgeItem() {
        super(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(10));
    }

    @Override
    public boolean isValidRepairItem(ItemStack is1, ItemStack is2) {
        return true;
    }

    /**
     * Set the ForgeItem current damage.
     *
     * @param is
     * @param damage
     */
    public static void setItemDamage(ItemStack is, int damage) {
        getForgeItemTag(is).putInt("damage", damage);
    }

    /**
     * Returns the ForgeItem current damage.
     *
     * @param is
     * @return
     */
    public static int getItemDamage(ItemStack is) {
        return getForgeItemTag(is).getInt("damage");
    }


    /**
     * Set the ForgeItem current damage.
     *
     * @param is
     * @param count
     */
    public static void setItemMaxDamage(ItemStack is, int count) {
        getForgeItemTag(is).putInt("max_damage", count);
    }

    /**
     * Returns the ForgeItem max damage.
     *
     * @param is
     * @return
     */
    public static int getItemMaxDamage(ItemStack is) {
        return getForgeItemTag(is).getInt("max_damage");
    }

    /**
     * Returns the forge_item tag for the given itemstack.
     *
     * @param is
     * @return
     */
    public static CompoundTag getForgeItemTag(ItemStack is) {
        CompoundTag tag = ItemStackHelper.getModNBT(is);
        if (!tag.contains("forge_item"))
            tag.put("forge_item", new CompoundTag());
        return tag.getCompound("forge_item");
    }

    /**
     * Returns the cost or 1 if not found.
     *
     * @param is
     * @return
     */
    public static int getCost(ItemStack is) {
        JsonObject obj = getForgeItemData(is);
        if (obj != null && obj.has("cost")) {
            return obj.get("cost").getAsInt();
        }
        return 1;
    }

    /**
     * Returns the ForgeItem data from assets/
     *
     * @param is
     * @return
     */
    public static JsonObject getForgeItemData(ItemStack is) {
        String path =
                (is.getItem() == JewelerItems.FORGE_ITEM.get()) ?
                        Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(ForgeItem.getResult(is).getItem())).getPath()
                        : (is.getItem() instanceof JewelerItemBase) ?
                        Objects.requireNonNull(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(is.getItem()).getPath())) : "";
        return AssetAPI.readForgeItem(new ResourceLocation(JewelerMain.MODID, path)).getAsJsonObject();
    }

    /**
     * Adds the effects for the given 'reagent'(right) to the output stack.
     *
     * @param right
     * @param output
     * @throws UnsupportedEncodingException
     */
    static void addBaseEffects(ItemStack right, ItemStack output) throws UnsupportedEncodingException {
        if (right == null || right.getItem() == Items.AIR)
            return;
        JsonObject obj = getForgeItemData(output);
        if (obj != null) {
            if (obj.has("entries")) {
                JsonObject entries = obj.getAsJsonObject("entries");

                ResourceLocation rl = ForgeRegistries.ITEMS.getKey(right.getItem());
                if (entries.has(Objects.requireNonNull(rl).toString())) {
                    JsonObject entry = entries.get(rl.toString()).getAsJsonObject();

                    Optional<IJewelryEffect> effect = EffectAPI.getEffect(new ResourceLocation(entry.get("effect").getAsString()));

                    effect.ifPresent(iJewelryEffect -> EffectAPI.addEffect(output, iJewelryEffect, entry.get("value").getAsInt()));
                }
            }
        }
    }

    /**
     * Returns the result of the ForgeItem.
     *
     * @param is
     * @return
     */
    public static ItemStack getResult(ItemStack is) {
        CompoundTag tag = getForgeItemTag(is);
        return ItemStack.of(tag.getCompound("result"));
    }

    public void appendHoverText(ItemStack is, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        ItemStack result = getResult(is);
        if (result != null) {
            ResourceLocation rl = ForgeRegistries.ITEMS.getKey(result.getItem());
            MutableComponent mutablecomponent = JewelerMain.PLATFORM_UTIL.getTranslatedComponent("item." + rl.getNamespace() + "." + rl.getPath());
            list.add(mutablecomponent);
        }
        EffectAPI.addEffectTooltip(is, list);
        list.add(JewelerMain.PLATFORM_UTIL.getTranslatedComponent(getItemDamage(is) + " / " + getItemMaxDamage(is)));
    }

}
