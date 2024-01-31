package com.walkercase.jeweler.effect.positive;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * Doubles crafting sometimes.
 */
public class DoubleCraftingEffect implements IJewelryEffect {

    public EffectRarity getEffectRarity(){
        return EffectRarity.SUPER_RARE;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "double_crafting");
    }

    public static final DustParticleOptions PARTICLE = new DustParticleOptions(Vec3.fromRGB24(0x543308).toVector3f(), 1.0F);

    public ParticleOptions getEquipParticle(){
        return PARTICLE;
    }

    @Override
    public void registerEvents(IEventBus modEventBus, IEventBus forgeEventBus){
        forgeEventBus.addListener(this::onItemCrafted);
    }

    public void onItemCrafted(PlayerEvent.ItemCraftedEvent e) {
        this.getEquippedCuriosWithEffect(e.getEntity()).forEach(is->{
            float value = EffectAPI.getEffectValue(this, is);

            float percent = Math.min(10,(value/10));
            if(RANDOM.nextFloat() < percent){
                IJewelryEffect.damageStack(e.getEntity(), is, RANDOM, (int) (100 * value));
                e.getEntity().getInventory().add(e.getCrafting().copy());
                MutableComponent mutablecomponent = JewelerMain.PLATFORM_UTIL.getTranslatedComponent("effect.jeweler.chat.double_crafting");

                this.playParticles(e.getEntity().level(), e.getEntity(), PARTICLE, 20, 0.5d);
                e.getEntity().playSound(SoundEvents.BAMBOO_BREAK);

                e.getEntity().sendSystemMessage(mutablecomponent);
            }
        });
    }

}
