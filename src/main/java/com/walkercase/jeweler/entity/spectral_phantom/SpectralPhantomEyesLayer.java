package com.walkercase.jeweler.entity.spectral_phantom;

import net.minecraft.client.model.PhantomModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpectralPhantomEyesLayer<T extends SpectralPhantom> extends EyesLayer<T, SpectralPhantomModel<T>> {
   private static final RenderType PHANTOM_EYES = RenderType.eyes(new ResourceLocation("textures/entity/phantom_eyes.png"));

   public SpectralPhantomEyesLayer(RenderLayerParent<T, SpectralPhantomModel<T>> p_117342_) {
      super(p_117342_);
   }

   public RenderType renderType() {
      return PHANTOM_EYES;
   }
}