package com.walkercase.jeweler.entity.spectral_skeleton;

import com.walkercase.jeweler.JewelerMain;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpectralSkeletonRenderer extends HumanoidMobRenderer<SpectralSkeleton, SkeletonModel<SpectralSkeleton>> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(JewelerMain.MODID, "textures/entity/spectral/skeleton.png");

    public SpectralSkeletonRenderer(EntityRendererProvider.Context p_174452_) {
        super(p_174452_, new SkeletonModel<>(p_174452_.bakeLayer(ModelLayers.SKELETON)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(SpectralSkeleton p_114482_) {
        return TEXTURE_LOCATION;
    }
}