package com.walkercase.jeweler.entity.spectral_blaze;

import com.walkercase.jeweler.JewelerMain;
import net.minecraft.client.model.BlazeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpectralBlazeRenderer extends MobRenderer<SpectralBlaze, BlazeModel<SpectralBlaze>> {
    private static final ResourceLocation BLAZE_LOCATION = new ResourceLocation(JewelerMain.MODID, "textures/entity/spectral/blaze.png");

    public SpectralBlazeRenderer(EntityRendererProvider.Context p_173933_) {
        super(p_173933_, new BlazeModel<>(p_173933_.bakeLayer(ModelLayers.BLAZE)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(SpectralBlaze p_114482_) {
        return BLAZE_LOCATION;
    }

    protected int getBlockLightLevel(Blaze p_113910_, BlockPos p_113911_) {
        return 15;
    }

}