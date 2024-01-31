package com.walkercase.jeweler.entity.spectral_spider;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.entity.spectral_skeleton.SpectralSkeleton;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpectralSpiderRenderer extends MobRenderer<SpectralSpider, SpiderModel<SpectralSpider>> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(JewelerMain.MODID, "textures/entity/spectral/spider.png");

    public SpectralSpiderRenderer(EntityRendererProvider.Context p_174452_) {
        super(p_174452_, new SpiderModel<>(p_174452_.bakeLayer(ModelLayers.SPIDER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(SpectralSpider p_114482_) {
        return TEXTURE_LOCATION;
    }
}