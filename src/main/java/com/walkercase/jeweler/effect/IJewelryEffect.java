package com.walkercase.jeweler.effect;

import com.google.common.collect.Multimap;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Contains information and methods used in creating new Jewelery Effects.
 */
public interface IJewelryEffect {

    /**
     * Singleton random instance.
     */
    Random RANDOM = new Random();

    /**
     * The maximum level this effect can have.
     * @return
     */
    default int getMaxLevel(){
        return 10;
    }

    /**
     * Called to register specific events.
     * @param modEventBus
     * @param forgeEventBus
     */
    default void registerEvents(IEventBus modEventBus, IEventBus forgeEventBus){};

    /**
     * Called when the equipped player is hurt.
     * @param e
     * @param is
     * @param base
     */
    default void onHurtEvent(LivingHurtEvent e, ItemStack is, JewelerItemBase base){}

    /**
     * Called when the equipped player hurt another.
     * @param e
     * @param is
     * @param base
     */
    default void onHurtEntityEvent(LivingHurtEvent e, ItemStack is, JewelerItemBase base){}

    /**
     * Returns a stream of currently equipped curios with this effect on them.
     *
     * @return Stream
     */
    default Stream<ItemStack> getEquippedCuriosWithEffect(LivingEntity entity){
        IItemHandlerModifiable modif = CuriosApi.getCuriosHelper().getEquippedCurios(entity).resolve().get();
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for(int i=0;i< modif.getSlots();i++){
            ItemStack is = modif.getStackInSlot(i);
            if(EffectAPI.getEffectValue(this, is) > 0)
                stacks.add(is);
        }

        return stacks.stream();
    }

    /**
     * Called when the equipped curio should break.
     * @param player
     * @param stack
     */
    default void curioBreak(Player player, ItemStack stack){}

    /**
     * Returns this effects.json value for the given ItemStack.
     *
     * @param is ItemStack
     * @return int value
     */
    default int getEffectValue(ItemStack is){
        return EffectAPI.getEffectValue(this, is);
    }

    /**
     * Called when the curio is going to take tick damage.
     * Return true to stop damage.
     * @param slotContext
     * @param stack
     * @param item
     * @return
     */
    default boolean damageCurioTick(SlotContext slotContext, ItemStack stack, JewelerItemBase item){
        return false;
    }

    /**
     * This effects.json type. Used mostly for text formatting.
     */
    enum EffectType {
        POSITIVE,
        NEGATIVE,
        NEUTRAL
    }

    /**
     * This is the effects.json rarity.
     */
    enum EffectRarity{
        COMMON(0, ChatFormatting.GRAY, "jeweler:cut_emerald_gem"),
        UNCOMMON(1, ChatFormatting.YELLOW, "jeweler:cut_ruby_gem"),
        RARE(2, ChatFormatting.AQUA, "jeweler:cut_sapphire_gem"),
        SUPER_RARE(3, ChatFormatting.LIGHT_PURPLE, "jeweler:cut_amethyst_gem"),
        UNIQUE(4, ChatFormatting.GOLD, "jeweler:cut_prismatic_gem");

        public final int index;
        public final ChatFormatting chatFormatting;
        public final String icon;
        EffectRarity(int i, ChatFormatting chatFormatting, String icon) {
            this.index = i;
            this.chatFormatting = chatFormatting;
            this.icon = icon;
        }
    }

    /**
     * Return this effects.json EffectType
     * @return EffectType
     */
    EffectType getEffectType();

    /**
     * Return this effects.json ID.
     * @return
     */
    ResourceLocation effectID();

    /**
     * Returns this effects.json rarity. Mostly used for text formatting.
     * @return
     */
    default EffectRarity getEffectRarity(){
        return EffectRarity.COMMON;
    }

    /**
     * Called every tick.
     * @param slotContext
     * @param stack
     * @param item
     */
    default void curioTick(SlotContext slotContext, ItemStack stack, JewelerItemBase item){}

    /**
     * Called when a curio with this effect is equipped.
     * @param slotContext
     * @param prevStack
     * @param stack
     * @param item
     */
    default void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack, JewelerItemBase item){
        LivingEntity living = slotContext.entity();
        Level level = living.level;
        ParticleOptions particleType = getEquipParticle();

        playParticles(level, living, particleType, 20, 0.5d);
    }

    /**
     * Called to make a "poof" of the given particles.
     * @param level
     * @param living
     * @param particle
     * @param count
     * @param random
     */
    default <T extends ParticleOptions> void playParticles(Level level, LivingEntity living, T particle, int count, double random){
        if(particle != null){
            if(!level.isClientSide){
                for(int i = 0; i < count; ++i) {
                    double d0 = RANDOM.nextGaussian() * random;
                    double d1 = RANDOM.nextGaussian() * random;
                    double d2 = RANDOM.nextGaussian() * random;
                    ((ServerLevel)level).sendParticles(particle, living.getRandomX(1.0d), living.getRandomY(), living.getRandomZ(1.0d), 0, 5, d0, d1, d2);
                }
            }
        }
    }

    /**
     * Returns the particles to play when jewelery with this effect is equipped.
     *
     * @return
     */
    default ParticleOptions getEquipParticle(){
        return null;
    }

    /**
     * Called when this item is unequipped.
     * @param slotContext
     * @param newStack
     * @param stack
     * @param item
     */
    default void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack, JewelerItemBase item){}

    /**
     * Return the Attributes that this curio modifies.
     * @param slotContext
     * @param uuid
     * @param stack
     * @param item
     * @return
     */
    default Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack, JewelerItemBase item){
        return null;
    }

    /**
     * Damages the given ItemStack with some effect calculations.
     * @param is stack
     * @param player Player
     * @param random random
     * @param amount amount
     */
    static void damageStack(Player player, ItemStack is, Random random, int amount){
        int newAmount = (amount * (11 - Math.min(10, EffectAPI.getEffectValue(EffectAPI.UNBREAKING_JEWELRY_EFFECT, is))));

        is.hurt(newAmount, player.getRandom(), null);

        //For some reason this won't fire properly until after we damage the item.
        if(is.getDamageValue() + newAmount >= is.getMaxDamage()) {
            EffectAPI.Events.curioBreak(player, is);
            player.playSound(SoundEvents.ITEM_BREAK);
            is.setCount(0);
        }
    }
}
