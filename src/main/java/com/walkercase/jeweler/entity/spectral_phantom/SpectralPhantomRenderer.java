package com.walkercase.jeweler.entity.spectral_phantom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.walkercase.jeweler.JewelerMain;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.PhantomRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpectralPhantomRenderer extends MobRenderer<SpectralPhantom, SpectralPhantomModel<SpectralPhantom>> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(JewelerMain.MODID, "textures/entity/spectral/phantom.png");

    public SpectralPhantomRenderer(EntityRendererProvider.Context p_174338_) {
        super(p_174338_, new SpectralPhantomModel<>(p_174338_.bakeLayer(ModelLayers.PHANTOM)), 0.75F);
        this.addLayer(new SpectralPhantomEyesLayer<>(this));
    }

    public ResourceLocation getTextureLocation(SpectralPhantom p_115679_) {
        return TEXTURE_LOCATION;
    }

    protected void scale(Phantom p_115681_, PoseStack p_115682_, float p_115683_) {
        int i = p_115681_.getPhantomSize();
        float f = 1.0F + 0.15F * (float)i;
        p_115682_.scale(f, f, f);
        p_115682_.translate(0.0F, 1.3125F, 0.1875F);
    }

    protected void setupRotations(SpectralPhantom p_115685_, PoseStack p_115686_, float p_115687_, float p_115688_, float p_115689_) {
        super.setupRotations(p_115685_, p_115686_, p_115687_, p_115688_, p_115689_);
        p_115686_.mulPose(Vector3f.XP.rotationDegrees(p_115685_.getXRot()));
    }
}