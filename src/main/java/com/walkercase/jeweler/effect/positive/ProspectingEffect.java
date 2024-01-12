package com.walkercase.jeweler.effect.positive;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.block.GeodeBlock;
import com.walkercase.jeweler.effect.IBreakSpeedEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Increases prospecting speed.
 */
public class ProspectingEffect implements IBreakSpeedEffect {
    @Override
    public boolean isTarget(BlockState state) {
        return state.getBlock() instanceof GeodeBlock;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "prospecting");
    }

    @Override
    public EffectRarity getEffectRarity(){
        return EffectRarity.UNCOMMON;
    }
}
