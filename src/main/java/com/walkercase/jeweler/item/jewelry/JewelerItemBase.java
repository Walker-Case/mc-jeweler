package com.walkercase.jeweler.item.jewelry;

import com.google.common.collect.Multimap;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * All mod jewelery must extend this class.
 */
public class JewelerItemBase extends Item implements ICurioItem {

    /**
     * The gem texture position to use for this item.
     */
    public TexturePosition texturePosition;

    public enum TexturePosition {
        NORTH("north"),
        EAST("east"),
        SOUTH("south"),
        WEST("west"),
        CENTER("center");

        public String id;

        TexturePosition(String id) {
            this.id = id;
        }
    }

    /**
     * The item used to repair this jewelry.
     */
    public final Item repairItem;

    /**
     * Create new jewelry!
     * @param rarity The item rarity.
     * @param repairItem The item used to repair this jewelry.
     * @param durability The max durability of this jewelry.
     * @param texturePosition The gem texture position to use.
     */
    public JewelerItemBase(Rarity rarity, Item repairItem, int durability, TexturePosition texturePosition) {
        super(new Item.Properties().rarity(rarity).stacksTo(1).defaultDurability(durability));
        this.texturePosition = texturePosition;
        this.repairItem = repairItem;
    }

    @Override
    public void appendHoverText(ItemStack is, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        if (EffectAPI.effectsNBTExists(is)) {
            EffectAPI.addEffectTooltip(is, list);
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if(!slotContext.entity().getLevel().isClientSide){
            if (!EffectAPI.Events.onCurioTick(slotContext, stack)) {
                IJewelryEffect.damageStack((Player)slotContext.entity(), stack, IJewelryEffect.RANDOM, 1);
            }
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return EffectAPI.getAttributeModifiers(slotContext, uuid, stack, this);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        EffectAPI.Events.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        EffectAPI.Events.onUnequip(slotContext, newStack, stack);
    }
}