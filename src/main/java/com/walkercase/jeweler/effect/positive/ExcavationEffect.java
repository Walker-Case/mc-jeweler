package com.walkercase.jeweler.effect.positive;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.effect.IBreakSpeedEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

/**
 * Increases excavation speed.
 */
public class ExcavationEffect implements IBreakSpeedEffect {
    @Override
    public boolean isTarget(BlockState state) {
        return state.getMaterial() == Material.DIRT
                || state.getMaterial() == Material.GRASS
                || state.getMaterial() == Material.POWDER_SNOW
                || state.getMaterial() == Material.CLAY
                || state.getMaterial() == Material.SAND
                || state.getMaterial() == Material.TOP_SNOW;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "excavation");
    }

    @Override
    public EffectRarity getEffectRarity(){
        return EffectRarity.UNCOMMON;
    }
}
