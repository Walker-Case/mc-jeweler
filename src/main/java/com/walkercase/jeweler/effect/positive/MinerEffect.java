package com.walkercase.jeweler.effect.positive;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.effect.IBreakSpeedEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

/**
 * Increases mining speed.
 */
public class MinerEffect implements IBreakSpeedEffect {
    @Override
    public boolean isTarget(BlockState state) {
        return state.getMaterial() == Material.STONE
                || state.getMaterial() == Material.AMETHYST
                || state.getMaterial() == Material.DIRT
                || state.getMaterial() == Material.METAL;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "miner");
    }

    @Override
    public EffectRarity getEffectRarity(){
        return EffectRarity.UNCOMMON;
    }
}
