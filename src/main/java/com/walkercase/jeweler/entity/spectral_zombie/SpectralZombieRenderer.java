package com.walkercase.jeweler.entity.spectral_zombie;

import com.walkercase.jeweler.JewelerMain;
import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpectralZombieRenderer extends HumanoidMobRenderer<SpectralZombie, HumanoidModel<SpectralZombie>> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(JewelerMain.MODID, "textures/entity/spectral/zombie.png");

    public SpectralZombieRenderer(EntityRendererProvider.Context p_174452_) {
        super(p_174452_, new HumanoidModel<>(p_174452_.bakeLayer(ModelLayers.ZOMBIE)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(SpectralZombie p_114482_) {
        return TEXTURE_LOCATION;
    }
}