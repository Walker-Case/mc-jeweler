package com.walkercase.jeweler.effect.negative;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.effect.IBreakSpeedEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

/**
 * Reduces wooductting speed.
 */
public class ReducedWoodcutterEffect implements IBreakSpeedEffect {
    @Override
    public boolean isTarget(BlockState state) {
        return state.getMaterial() == Material.BAMBOO
                || state.getMaterial() == Material.LEAVES
                || state.getMaterial() == Material.WOOD;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.NEGATIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "reduced_woodcutter");
    }

    @Override
    public EffectRarity getEffectRarity(){
        return EffectRarity.UNCOMMON;
    }
}
