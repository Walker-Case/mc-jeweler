package com.walkercase.jeweler.entity;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.entity.spectral_blaze.SpectralBlaze;
import com.walkercase.jeweler.entity.spectral_blaze.SpectralBlazeRenderer;
import com.walkercase.jeweler.entity.spectral_creeper.SpectralCreeper;
import com.walkercase.jeweler.entity.spectral_creeper.SpectralCreeperRenderer;
import com.walkercase.jeweler.entity.spectral_phantom.SpectralPhantom;
import com.walkercase.jeweler.entity.spectral_phantom.SpectralPhantomRenderer;
import com.walkercase.jeweler.entity.spectral_skeleton.SpectralSkeleton;
import com.walkercase.jeweler.entity.spectral_skeleton.SpectralSkeletonRenderer;
import com.walkercase.jeweler.entity.spectral_spider.SpectralSpider;
import com.walkercase.jeweler.entity.spectral_spider.SpectralSpiderRenderer;
import com.walkercase.jeweler.entity.spectral_zombie.SpectralZombie;
import com.walkercase.jeweler.entity.spectral_zombie.SpectralZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JewelerEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, JewelerMain.MODID);

    public static final RegistryObject<EntityType<SpectralSkeleton>> SPECTRAL_SKELETON = register("spectral_skeleton",
            EntityType.Builder.of(SpectralSkeleton::new, MobCategory.CREATURE).sized(0.6F, 1.99F).clientTrackingRange(8));
    public static final RegistryObject<EntityType<SpectralZombie>> SPECTRAL_ZOMBIE = register("spectral_zombie",
            EntityType.Builder.of(SpectralZombie::new, MobCategory.CREATURE).sized(0.6F, 1.99F).clientTrackingRange(8));
    public static final RegistryObject<EntityType<SpectralSpider>> SPECTRAL_SPIDER = register("spectral_spider",
            EntityType.Builder.of(SpectralSpider::new, MobCategory.CREATURE).sized(0.6F, 1.99F).clientTrackingRange(8));
    public static final RegistryObject<EntityType<SpectralCreeper>> SPECTRAL_CREEPER = register("spectral_creeper",
            EntityType.Builder.of(SpectralCreeper::new, MobCategory.CREATURE).sized(0.6F, 1.99F).clientTrackingRange(8));
    public static final RegistryObject<EntityType<SpectralPhantom>> SPECTRAL_PHANTOM = register("spectral_phantom",
            EntityType.Builder.of(SpectralPhantom::new, MobCategory.CREATURE).sized(0.6F, 1.99F).clientTrackingRange(8));
    public static final RegistryObject<EntityType<SpectralBlaze>> SPECTRAL_BLAZE = register("spectral_blaze",
            EntityType.Builder.of(SpectralBlaze::new, MobCategory.CREATURE).sized(0.6F, 1.99F).clientTrackingRange(8));

    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(SPECTRAL_SKELETON.get(), SpectralSkeleton.createAttributes().build());
        event.put(SPECTRAL_ZOMBIE.get(), SpectralZombie.createAttributes().build());
        event.put(SPECTRAL_SPIDER.get(), SpectralSpider.createAttributes().build());
        event.put(SPECTRAL_CREEPER.get(), SpectralCreeper.createAttributes().build());
        event.put(SPECTRAL_PHANTOM.get(), SpectralPhantom.createAttributes().build());
        event.put(SPECTRAL_BLAZE.get(), SpectralBlaze.createAttributes().build());
    }

    public static void registerEntityRenderers(){
        EntityRenderers.register(SPECTRAL_SKELETON.get(), SpectralSkeletonRenderer::new);
        EntityRenderers.register(SPECTRAL_ZOMBIE.get(), SpectralZombieRenderer::new);
        EntityRenderers.register(SPECTRAL_SPIDER.get(), SpectralSpiderRenderer::new);
        EntityRenderers.register(SPECTRAL_CREEPER.get(), SpectralCreeperRenderer::new);
        EntityRenderers.register(SPECTRAL_PHANTOM.get(), SpectralPhantomRenderer::new);
        EntityRenderers.register(SPECTRAL_BLAZE.get(), SpectralBlazeRenderer::new);
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String p_20635_, EntityType.Builder<T> p_20636_) {
        return ENTITY_TYPES.register(p_20635_, ()->p_20636_.build(p_20635_));
    }
}
