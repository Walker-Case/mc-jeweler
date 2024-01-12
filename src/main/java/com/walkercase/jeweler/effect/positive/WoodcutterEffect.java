package com.walkercase.jeweler.effect.positive;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.effect.IBreakSpeedEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

/**
 * Increases wooductting speed.
 */
public class WoodcutterEffect implements IBreakSpeedEffect {
    @Override
    public boolean isTarget(BlockState state) {
        return state.getMaterial() == Material.BAMBOO
                || state.getMaterial() == Material.LEAVES
                || state.getMaterial() == Material.WOOD;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "woodcutter");
    }

    @Override
    public EffectRarity getEffectRarity(){
        return EffectRarity.UNCOMMON;
    }
}
