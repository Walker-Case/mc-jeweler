package com.walkercase.jeweler.entity;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.entity.spectral_skeleton.SpectralSkeleton;
import com.walkercase.jeweler.entity.spectral_skeleton.SpectralSkeletonRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JewelerEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, JewelerMain.MODID);

    public static final RegistryObject<EntityType<SpectralSkeleton>> SPECTRAL_SKELETON = register("spectral_skeleton", EntityType.Builder.of(SpectralSkeleton::new, MobCategory.CREATURE).sized(0.6F, 1.99F).clientTrackingRange(8));

    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(SPECTRAL_SKELETON.get(), SpectralSkeleton.createAttributes().build());
    }

    public static void unsafeRunMethodExample(Object param1, Object param2) {
        // ...
    }

    public static void registerEntityRenderers(){
        EntityRenderers.register(SPECTRAL_SKELETON.get(), SpectralSkeletonRenderer::new);
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String p_20635_, EntityType.Builder<T> p_20636_) {
        return ENTITY_TYPES.register(p_20635_, ()->p_20636_.build(p_20635_));
    }
}
