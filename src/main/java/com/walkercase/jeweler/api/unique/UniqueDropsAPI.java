package com.walkercase.jeweler.api.unique;

import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.api.unique.drops.BlockDrop;
import com.walkercase.jeweler.api.unique.drops.EntityDrop;
import com.walkercase.jeweler.api.unique.drops.StruckByLightningDrop;
import com.walkercase.jeweler.api.unique.drops.UniqueDrop;
import com.walkercase.jeweler.effect.IJewelryEffect;
import com.walkercase.jeweler.item.JewelerItems;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.ArrayList;

public class UniqueDropsAPI {

    private static final ArrayList<UniqueDrop> UNIQUE_DROPS = new ArrayList<>();

    /**
     * Returns a list of copied unique drops.
     * @return
     */
    public static synchronized ArrayList<ItemStack> getUniqueDrops(){
        ArrayList<ItemStack> isList = new ArrayList<>();
        UNIQUE_DROPS.forEach(e->isList.add(e.getItemStackBuider().build()));
        return isList;
    }

    /**
     * Returns a list of copied unique drops with some visual modifiers such as damage removed.
     * @return
     */
    public static synchronized ArrayList<ItemStack> getDisplayDrops(){
        ArrayList<ItemStack> isList = new ArrayList<>();
        UNIQUE_DROPS.forEach(e->isList.add(e.getItemStackBuider().buildForDisplay()));
        return isList;
    }

    /**
     * Called to register unique drop specific events.
     * @param modBusEvent
     * @param forgeEventBus
     */
    public static synchronized void registerUniqueDropEvents(IEventBus modBusEvent, IEventBus forgeEventBus){
        UNIQUE_DROPS.forEach(d->d.registerEvents(modBusEvent, forgeEventBus));
    }

    public static synchronized void registerUniqueDrop(UniqueDrop drop){
        UNIQUE_DROPS.add(drop);
    }

    public static synchronized void generateUniqueDrops(){
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.zombie_gem_0", ChatFormatting.WHITE)
                .addEffect(EffectAPI.ZOMBIE_RESIST_EFFECT, 1)
                ,"minecraft:zombie", 0.05f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.zombie_gem_1", ChatFormatting.YELLOW)
                .addEffect(EffectAPI.ZOMBIE_RESIST_EFFECT, 2),
                "minecraft:zombie", 0.02f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.skeleton_gem_0", ChatFormatting.WHITE)
                .addEffect(EffectAPI.SKELETON_RESIST_EFFECT, 1),
                "minecraft:skeleton", 0.05f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.skeleton_gem_1", ChatFormatting.YELLOW)
                .addEffect(EffectAPI.SKELETON_RESIST_EFFECT, 2),
                "minecraft:skeleton", 0.02f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.spider_gem_0", ChatFormatting.WHITE)
                .addEffect(EffectAPI.SPIDER_RESIST_EFFECT, 1),
                "minecraft:spider", 0.05f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.spider_gem_1", ChatFormatting.YELLOW)
                .addEffect(EffectAPI.SPIDER_RESIST_EFFECT, 2),
                "minecraft:spider", 0.02f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.creeper_gem_0", ChatFormatting.WHITE)
                .addEffect(EffectAPI.CREEPER_RESIST_EFFECT, 1),
                "minecraft:creeper", 0.05f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.creeper_gem_1", ChatFormatting.YELLOW)
                .addEffect(EffectAPI.CREEPER_RESIST_EFFECT, 2),
                "minecraft:creeper", 0.02f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.blaze_gem_0", ChatFormatting.WHITE)
                .addEffect(EffectAPI.BLAZE_RESIST_EFFECT, 1),
                "minecraft:blaze", 0.05f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.blaze_gem_1", ChatFormatting.YELLOW)
                .addEffect(EffectAPI.BLAZE_RESIST_EFFECT, 2),
                "minecraft:blaze", 0.02f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.ender_dragon_gem_0", ChatFormatting.GOLD)
                .addEffect(EffectAPI.ENDER_DRAGON_RESIST_EFFECT, 1),
                "minecraft:ender_dragon", 0.1f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.ender_dragon_gem_1", ChatFormatting.RED)
                .addEffect(EffectAPI.ENDER_DRAGON_RESIST_EFFECT, 3)
                .setFoil(true),
                "minecraft:ender_dragon", 0.01f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.evoker_gem_0", ChatFormatting.WHITE)
                .addEffect(EffectAPI.EVOKER_RESIST_EFFECT, 1),
                "minecraft:evoker", 0.05f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.evoker_gem_1", ChatFormatting.YELLOW)
                .addEffect(EffectAPI.EVOKER_RESIST_EFFECT, 2),
                "minecraft:evoker", 0.02f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.ghast_gem_0", ChatFormatting.WHITE)
                .addEffect(EffectAPI.GHAST_RESIST_EFFECT, 1),
                "minecraft:ghast", 0.05f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.ghast_gem_1", ChatFormatting.YELLOW)
                .addEffect(EffectAPI.GHAST_RESIST_EFFECT, 2),
                "minecraft:ghast", 0.02f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.phantom_gem_0", ChatFormatting.WHITE)
                .addEffect(EffectAPI.PHANTOM_RESIST_EFFECT, 1),
                "minecraft:phantom", 0.05f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.phantom_gem_1", ChatFormatting.YELLOW)
                .addEffect(EffectAPI.PHANTOM_RESIST_EFFECT, 2),
                "minecraft:phantom", 0.02f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.pillager_gem_0", ChatFormatting.WHITE)
                .addEffect(EffectAPI.PILLAGER_RESIST_EFFECT, 1),
                "minecraft:pillager", 0.05f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.pillager_gem_1", ChatFormatting.YELLOW)
                .addEffect(EffectAPI.PILLAGER_RESIST_EFFECT, 2),
                "minecraft:pillager", 0.02f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.silverfish_gem_0", ChatFormatting.WHITE)
                .addEffect(EffectAPI.SILVERFISH_RESIST_EFFECT, 1),
                "minecraft:silverfish", 0.05f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.silverfish_gem_1", ChatFormatting.YELLOW)
                .addEffect(EffectAPI.SILVERFISH_RESIST_EFFECT, 2),
                "minecraft:silverfish", 0.02f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.vex_gem_0", ChatFormatting.WHITE)
                .addEffect(EffectAPI.VEX_RESIST_EFFECT, 1),
                "minecraft:vex", 0.05f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.vex_gem_1", ChatFormatting.YELLOW)
                .addEffect(EffectAPI.VEX_RESIST_EFFECT, 2),
                "minecraft:vex", 0.02f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.vindicator_gem_0", ChatFormatting.WHITE)
                .addEffect(EffectAPI.VINDICATOR_RESIST_EFFECT, 1),
                "minecraft:vindicator", 0.05f));
        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.CUT_AMETHYST_GEM.get())
                .displayText("jeweler.unique.vindicator_gem_1", ChatFormatting.YELLOW)
                .addEffect(EffectAPI.VINDICATOR_RESIST_EFFECT, 2),
                "minecraft:vindicator", 0.02f));

        registerUniqueDrop(new EntityDrop(new ItemStackBuilder(JewelerItems.DIAMOND_RING.get())
                .displayText("jeweler.unique.wither_lost_diamond_ring", ChatFormatting.GOLD)
                .setGem(JewelerItems.CUT_AMETHYST_GEM.get())
                .setRandomDamage(0, 4000)
                .addEffect(EffectAPI.UNDYING_JEWELRY_EFFECT, 4)
                .addEffect(EffectAPI.UNBREAKING_JEWELRY_EFFECT, 2)
                .addEffect(EffectAPI.SAVING_JEWELRY_EFFECT, 1)
                .addEffect(EffectAPI.WITHER_RESIST_EFFECT, 1),
                "minecraft:wither", 0.05f));

        registerUniqueDrop(new BlockDrop(new ItemStackBuilder(JewelerItems.IRON_RING.get())
                .displayText("jeweler.unique.pristine_obisidan_circle", ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC)
                .setGem(JewelerItems.CUT_SAPPHIRE_GEM.get())
                .setRandomDamage(0, 5000)
                .addEffect(EffectAPI.UNBREAKING_JEWELRY_EFFECT, 9),
                Blocks.OBSIDIAN,
                0.05f));

        registerUniqueDrop(new BlockDrop(new ItemStackBuilder(JewelerItems.GOLD_AMULET.get())
                .displayText("jeweler.unique.old_miners_gold_chain", ChatFormatting.GOLD, ChatFormatting.ITALIC)
                .setGem(JewelerItems.CUT_SAPPHIRE_GEM.get())
                .addEffect(EffectAPI.MINER_EFFECT, 6)
                .addEffect(EffectAPI.PROSPECTING_EFFECT, 5)
                .addEffect(EffectAPI.EXCAVATION_EFFECT, 2)
                .addEffect(EffectAPI.UNBREAKING_JEWELRY_EFFECT, 2),
                Blocks.GOLD_ORE,
                0.05f));
        registerUniqueDrop(new StruckByLightningDrop(new ItemStackBuilder(JewelerItems.DIAMOND_AMULET.get())
                .displayText("jeweler.unique.enlightened_amulet_0", ChatFormatting.GOLD)
                .setGem(JewelerItems.CUT_PRISMATIC_GEM.get())
                .addEffect(EffectAPI.UNBREAKING_JEWELRY_EFFECT, 1)
                .addEffect(EffectAPI.NIGHT_VISION_JEWELRY_EFFECT, 1)
                .addEffect(EffectAPI.WATER_BREATHING_JEWELERY_EFFECT, 1)
                .addEffect(EffectAPI.DOUBLE_CRAFTING_EFFECT, 1)
                .setRandomDamage(0, 50000)
                .setFoil(true)
                ,0.1f));
        registerUniqueDrop(new StruckByLightningDrop(new ItemStackBuilder(JewelerItems.DIAMOND_AMULET.get())
                .displayText("jeweler.unique.enlightened_amulet_1", ChatFormatting.AQUA, ChatFormatting.ITALIC)
                .setGem(JewelerItems.CUT_PRISMATIC_GEM.get())
                .addEffect(EffectAPI.UNBREAKING_JEWELRY_EFFECT, 2)
                .addEffect(EffectAPI.NIGHT_VISION_JEWELRY_EFFECT, 2)
                .addEffect(EffectAPI.WATER_BREATHING_JEWELERY_EFFECT, 2)
                .addEffect(EffectAPI.DOUBLE_CRAFTING_EFFECT, 2)
                .setFoil(true)
                ,0.01f));

        registerUniqueDrop(new StruckByLightningDrop(new ItemStackBuilder(JewelerItems.NETHERITE_RING.get())
                .displayText("jeweler.unique.peace_ring", ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD)
                .setGem(JewelerItems.CUT_PRISMATIC_GEM.get())
                .addEffect(EffectAPI.UNBREAKING_JEWELRY_EFFECT, 5)
                .addEffect(EffectAPI.NIGHT_VISION_JEWELRY_EFFECT, 1)
                .addEffect(EffectAPI.WATER_BREATHING_JEWELERY_EFFECT, 5)
                .addEffect(EffectAPI.DOUBLE_CRAFTING_EFFECT, 2)
                .addEffect(EffectAPI.EXCAVATION_EFFECT, 2)
                .addEffect(EffectAPI.PROSPECTING_EFFECT, 1)
                .addEffect(EffectAPI.STAMINA_JEWELRY_EFFECT, 3)
                .addEffect(EffectAPI.SAVING_JEWELRY_EFFECT, 1)
                .setFoil(true)
                ,0.001f));

        EffectAPI.EFFECTS.stream().filter(x->x.getEffectType() == IJewelryEffect.EffectType.NEGATIVE).forEach(e->{
            registerUniqueDrop(new UniqueDrop(new ItemStackBuilder(JewelerItems.CUT_RUBY_GEM.get())
                    .displayText(e.effectID().getNamespace() + ".unique." + e.effectID().getPath(), ChatFormatting.GRAY)
                    .addEffect(e, 1), 1.0f));
        });

        EffectAPI.EFFECTS.stream().filter(x->x.getEffectType() == IJewelryEffect.EffectType.POSITIVE).forEach(e->{
            registerUniqueDrop(new UniqueDrop(new ItemStackBuilder(JewelerItems.CUT_EMERALD_GEM.get())
                    .displayText(e.effectID().getNamespace() + ".unique." + e.effectID().getPath() + "_0", ChatFormatting.GRAY)
                    .addEffect(e, 1), 1.0f));

            registerUniqueDrop(new UniqueDrop(new ItemStackBuilder(JewelerItems.CUT_SAPPHIRE_GEM.get())
                    .displayText(e.effectID().getNamespace() + ".unique." + e.effectID().getPath() + "_1", ChatFormatting.GRAY)
                    .addEffect(e, 5), 1.0f));

            registerUniqueDrop(new UniqueDrop(new ItemStackBuilder(JewelerItems.CUT_PRISMATIC_GEM.get())
                    .displayText(e.effectID().getNamespace() + ".unique." + e.effectID().getPath() + "_2", ChatFormatting.GRAY)
                    .addEffect(e, 10), 1.0f));
        });
    }

}
