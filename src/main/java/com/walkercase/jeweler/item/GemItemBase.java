package com.walkercase.jeweler.item;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;

/**
 * All gems must extend this.
 */
public class GemItemBase extends Item {

    /**
     * The texture index to use for this gem.
     */
    public final int index;

    private static int lastIndex = 1;

    private static synchronized int getNextIndex(){
        return lastIndex ++;
    }

    /**
     * The uncut gem used to make this gem.
     */
    public final RegistryObject<Item> rawGem;

    public final int positiveRolls, neutralRolls, negativeRolls;


    /**
     * Create a new gem!
     * @param rarity The item rarity.
     * @param rawGem The uncut gem that can be used to make this.
     */
    public GemItemBase(Rarity rarity, RegistryObject<Item> rawGem, int positiveRolls, int neutralRolls, int negativeRolls) {
        super(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().rarity(rarity).stacksTo(1));
        this.index = getNextIndex();
        this.rawGem = rawGem;
        this.positiveRolls = positiveRolls;
        this.neutralRolls = neutralRolls;
        this.negativeRolls = negativeRolls;
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return EffectAPI.isFoil(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack is, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        EffectAPI.addEffectTooltip(is, list);
    }

}
