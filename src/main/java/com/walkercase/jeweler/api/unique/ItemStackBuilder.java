package com.walkercase.jeweler.api.unique;

import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import com.walkercase.jeweler.item.GemItemBase;
import com.walkercase.jeweler.item.ItemStackHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

/**
 * Used to easily create new ItemStacks.
 */
public class ItemStackBuilder{
    private static final Random RANDOM = new Random();
    private ItemStack itemStack;

    private int minDamage = 0;
    private int maxDamage = 0;

    /**
     * Create a new ItemStack builder with the provided ItemStack.
     * @param is
     */
    public ItemStackBuilder(ItemStack is){
        this.itemStack = is;
    }

    /**
     * Create a new ItemStack builder with the provided item.
     * @param item
     */
    public ItemStackBuilder(Item item){
        this(new ItemStack(item, 1));
    }

    /**
     * Sets the ItemStack to foil or to have an "enchanted" visual.
     * @param b
     * @return
     */
    public ItemStackBuilder setFoil(boolean b){
        EffectAPI.setFoil(this.itemStack, true);
        return this;
    }

    /**
     * Sets the socketed gem for the ItemStack.
     * @param gem
     * @return
     */
    public ItemStackBuilder setGem(GemItemBase gem){
        EffectAPI.getEffectsNBT(this.itemStack).putBoolean("socketed", true);
        ItemStackHelper.getModNBT(this.itemStack).putInt("textureIndex", gem.index);
        return this;
    }

    /**
     * Sets the ItemStack damage randomly.
     * @param min
     * @param max
     * @return
     */
    public ItemStackBuilder setRandomDamage(int min, int max){
        this.minDamage = min;
        this.maxDamage = max;
        return this;
    }

    /**
     * Sets the ItemStack display text with the provided formatting.
     * @param i18n - Translation Key
     * @param formatting
     * @return
     */
    public ItemStackBuilder displayText(String i18n, ChatFormatting... formatting){
        MutableComponent isName = new TranslatableComponent(i18n);
        isName.withStyle(ChatFormatting.ITALIC);
        isName.withStyle(ChatFormatting.GOLD);
        this.itemStack.setHoverName(isName);
        return this;
    }

    /**
     * Adds the provided IJeweleryEffect to the ItemStack.
     * @param effect
     * @param level
     * @return
     */
    public ItemStackBuilder addEffect(IJewelryEffect effect, int level){
        EffectAPI.addEffect(this.itemStack, effect, level);
        return this;
    }

    /**
     * Returns a copy of the ItemStack.
     * @return
     */
    public ItemStack build(){
        ItemStack is = this.itemStack.copy();
        if(this.maxDamage > 0)
            is.setDamageValue(RANDOM.nextInt(this.maxDamage-this.minDamage) + this.minDamage);
        return is;
    }

    /**
     * Returns a copy of the ItemStack with no visual modifiers such as damage.
     * @return
     */
    public ItemStack buildForDisplay(){
        return this.itemStack.copy();
    }
}
