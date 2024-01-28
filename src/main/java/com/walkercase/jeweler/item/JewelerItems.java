package com.walkercase.jeweler.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.AssetAPI;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.block.JewelerBlocks;
import com.walkercase.jeweler.effect.IJewelryEffect;
import com.walkercase.jeweler.generated.JewelerItemBaseModelProvider;
import com.walkercase.jeweler.item.jewelry.JewelerAmulet;
import com.walkercase.jeweler.item.jewelry.JewelerBracers;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import com.walkercase.jeweler.item.jewelry.JewelerRing;
import com.walkercase.jeweler.item.tool.BrushItemBase;
import com.walkercase.jeweler.item.tool.ChiselItemBase;
import com.walkercase.jeweler.item.tool.mould.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class JewelerItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, JewelerMain.MODID);

    public static final RegistryObject<ForgeItem> FORGE_ITEM = ITEMS.register("forge_item", ForgeItem::new);

    public static final RegistryObject<Item> MELTED_COPPER_INGOT = ITEMS.register("melted_copper_ingot",
            () -> new Item(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(16)));
    public static final RegistryObject<Item> MELTED_IRON_INGOT = ITEMS.register("melted_iron_ingot",
            () -> new Item(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(16)));
    public static final RegistryObject<Item> MELTED_GOLD_INGOT = ITEMS.register("melted_gold_ingot",
            () -> new Item(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(16)));
    public static final RegistryObject<Item> MELTED_DIAMOND = ITEMS.register("melted_diamond",
            () -> new Item(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(16)));

    public static final RegistryObject<BlockItem> GEODE_BLOCK = ITEMS.register("geode_block",
            () -> new BlockItem(JewelerBlocks.GEODE_BLOCK.get(),
                    JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(8)));
    public static final RegistryObject<BlockItem> COPPER_GEODE_BLOCK = ITEMS.register("copper_geode_block",
            () -> new BlockItem(JewelerBlocks.COPPER_GEODE_BLOCK.get(),
                    JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(8)));

    public static final RegistryObject<BlockItem> IRON_GEODE_BLOCK = ITEMS.register("iron_geode_block",
            () -> new BlockItem(JewelerBlocks.IRON_GEODE_BLOCK.get(),
                    JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(8)));
    public static final RegistryObject<BlockItem> GOLD_GEODE_BLOCK = ITEMS.register("gold_geode_block",
            () -> new BlockItem(JewelerBlocks.GOLD_GEODE_BLOCK.get(),
                    JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(8)));
    public static final RegistryObject<BlockItem> DIAMOND_GEODE_BLOCK = ITEMS.register("diamond_geode_block",
            () -> new BlockItem(JewelerBlocks.DIAMOND_GEODE_BLOCK.get(),
                    JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(8)));


    public static final RegistryObject<ChiselItemBase> COPPER_CHISEL = ITEMS.register("copper_chisel", () -> new ChiselItemBase(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties()
            .stacksTo(1).durability(30).rarity(Rarity.COMMON)));
    public static final RegistryObject<ChiselItemBase> IRON_CHISEL = ITEMS.register("iron_chisel", () -> new ChiselItemBase(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties()
            .stacksTo(1).durability(80).rarity(Rarity.COMMON)));
    public static final RegistryObject<ChiselItemBase> GOLD_CHISEL = ITEMS.register("gold_chisel", () -> new ChiselItemBase(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties()
            .stacksTo(1).durability(40).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<ChiselItemBase> DIAMOND_CHISEL = ITEMS.register("diamond_chisel", () -> new ChiselItemBase(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties()
            .stacksTo(1).durability(300).rarity(Rarity.RARE)));
    public static final RegistryObject<ChiselItemBase> NETHERITE_CHISEL = ITEMS.register("netherite_chisel", () -> new ChiselItemBase(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties()
            .stacksTo(1).durability(600).rarity(Rarity.EPIC)));

    public static final RegistryObject<ChiselMould> COPPER_CHISEL_MOULD = ITEMS.register("copper_chisel_mould", () ->
            new ChiselMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(1), Items.COPPER_INGOT, MELTED_COPPER_INGOT.get(), COPPER_CHISEL.get()));
    public static final RegistryObject<ChiselMould> IRON_CHISEL_MOULD = ITEMS.register("iron_chisel_mould", () ->
            new ChiselMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(2), Items.IRON_INGOT, MELTED_IRON_INGOT.get(), IRON_CHISEL.get()));
    public static final RegistryObject<ChiselMould> GOLD_CHISEL_MOULD = ITEMS.register("gold_chisel_mould", () ->
            new ChiselMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(1), Items.GOLD_INGOT, MELTED_GOLD_INGOT.get(), GOLD_CHISEL.get()));
    public static final RegistryObject<ChiselMould> DIAMOND_CHISEL_MOULD = ITEMS.register("diamond_chisel_mould", () ->
            new ChiselMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(3), Items.DIAMOND, MELTED_DIAMOND.get(), DIAMOND_CHISEL.get()));
    public static final RegistryObject<ChiselMould> NETHERITE_CHISEL_MOULD = ITEMS.register("netherite_chisel_mould", () ->
            new ChiselMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(4), Items.NETHERITE_INGOT, Items.NETHER_STAR, NETHERITE_CHISEL.get()));


    public static final RegistryObject<BrushItemBase> COPPER_BRUSH = ITEMS.register("copper_brush", () -> new BrushItemBase(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties()
            .stacksTo(1).durability(30).rarity(Rarity.COMMON)));
    public static final RegistryObject<BrushItemBase> IRON_BRUSH = ITEMS.register("iron_brush", () -> new BrushItemBase(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties()
            .stacksTo(1).durability(80).rarity(Rarity.COMMON)));
    public static final RegistryObject<BrushItemBase> GOLD_BRUSH = ITEMS.register("gold_brush", () -> new BrushItemBase(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties()
            .stacksTo(1).durability(40).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<BrushItemBase> DIAMOND_BRUSH = ITEMS.register("diamond_brush", () -> new BrushItemBase(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties()
            .stacksTo(1).durability(300).rarity(Rarity.RARE)));
    public static final RegistryObject<BrushItemBase> NETHERITE_BRUSH = ITEMS.register("netherite_brush", () -> new BrushItemBase(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties()
            .stacksTo(1).durability(600).rarity(Rarity.EPIC)));

    public static final RegistryObject<BrushMould> COPPER_BRUSH_MOULD = ITEMS.register("copper_brush_mould", () ->
            new BrushMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(1), Items.COPPER_INGOT, MELTED_COPPER_INGOT.get(), COPPER_BRUSH.get()));
    public static final RegistryObject<BrushMould> IRON_BRUSH_MOULD = ITEMS.register("iron_brush_mould", () ->
            new BrushMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(2), Items.IRON_INGOT, MELTED_IRON_INGOT.get(), IRON_BRUSH.get()));
    public static final RegistryObject<BrushMould> GOLD_BRUSH_MOULD = ITEMS.register("gold_brush_mould", () ->
            new BrushMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(1), Items.GOLD_INGOT, MELTED_GOLD_INGOT.get(), GOLD_BRUSH.get()));
    public static final RegistryObject<BrushMould> DIAMOND_BRUSH_MOULD = ITEMS.register("diamond_brush_mould", () ->
            new BrushMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(3), Items.DIAMOND, MELTED_DIAMOND.get(), DIAMOND_BRUSH.get()));
    public static final RegistryObject<BrushMould> NETHERITE_BRUSH_MOULD = ITEMS.register("netherite_brush_mould", () ->
            new BrushMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(4), Items.NETHERITE_INGOT, Items.NETHER_STAR, NETHERITE_BRUSH.get()));

    public static final RegistryObject<JewelerRing> COPPER_RING = ITEMS.register("copper_ring", () ->
            new JewelerRing(Rarity.COMMON, MELTED_COPPER_INGOT.get(), 5000));
    public static final RegistryObject<JewelerRing> IRON_RING = ITEMS.register("iron_ring", () ->
            new JewelerRing(Rarity.UNCOMMON, MELTED_IRON_INGOT.get(), 6000));
    public static final RegistryObject<JewelerRing> GOLD_RING = ITEMS.register("gold_ring", () ->
            new JewelerRing(Rarity.UNCOMMON, MELTED_GOLD_INGOT.get(), 4000));
    public static final RegistryObject<JewelerRing> DIAMOND_RING = ITEMS.register("diamond_ring", () ->
            new JewelerRing(Rarity.RARE, MELTED_DIAMOND.get(), 32000));
    public static final RegistryObject<JewelerRing> NETHERITE_RING = ITEMS.register("netherite_ring", () ->
            new JewelerRing(Rarity.EPIC, Items.NETHER_STAR, 64000));

    public static final RegistryObject<RingMould> COPPER_RING_MOULD = ITEMS.register("copper_ring_mould", () ->
            new RingMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(1), Items.COPPER_INGOT, MELTED_COPPER_INGOT.get(), COPPER_RING.get()));
    public static final RegistryObject<RingMould> IRON_RING_MOULD = ITEMS.register("iron_ring_mould", () ->
            new RingMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(2), Items.IRON_INGOT, MELTED_IRON_INGOT.get(), IRON_RING.get()));
    public static final RegistryObject<RingMould> GOLD_RING_MOULD = ITEMS.register("gold_ring_mould", () ->
            new RingMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(1), Items.GOLD_INGOT, MELTED_GOLD_INGOT.get(), GOLD_RING.get()));
    public static final RegistryObject<RingMould> DIAMOND_RING_MOULD = ITEMS.register("diamond_ring_mould", () ->
            new RingMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(3), Items.DIAMOND, MELTED_DIAMOND.get(), DIAMOND_RING.get()));
    public static final RegistryObject<RingMould> NETHERITE_RING_MOULD = ITEMS.register("netherite_ring_mould", () ->
            new RingMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(4), Items.NETHERITE_INGOT, Items.NETHER_STAR, NETHERITE_RING.get()));


    public static final RegistryObject<JewelerAmulet> COPPER_AMULET = ITEMS.register("copper_amulet",
            () -> new JewelerAmulet(Rarity.COMMON, MELTED_COPPER_INGOT.get(), 10000));
    public static final RegistryObject<JewelerAmulet> IRON_AMULET = ITEMS.register("iron_amulet",
            () -> new JewelerAmulet(Rarity.UNCOMMON, MELTED_IRON_INGOT.get(), 20000));
    public static final RegistryObject<JewelerAmulet> GOLD_AMULET = ITEMS.register("gold_amulet",
            () -> new JewelerAmulet(Rarity.UNCOMMON, MELTED_GOLD_INGOT.get(), 6000));
    public static final RegistryObject<JewelerAmulet> DIAMOND_AMULET = ITEMS.register("diamond_amulet",
            () -> new JewelerAmulet(Rarity.RARE, MELTED_DIAMOND.get(), 60000));
    public static final RegistryObject<JewelerAmulet> NETHERITE_AMULET = ITEMS.register("netherite_amulet",
            () -> new JewelerAmulet(Rarity.EPIC, Items.NETHER_STAR, 120000));

    public static final RegistryObject<AmuletMould> COPPER_AMULET_MOULD = ITEMS.register("copper_amulet_mould", () ->
            new AmuletMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(1), Items.COPPER_INGOT, MELTED_COPPER_INGOT.get(), COPPER_AMULET.get()));
    public static final RegistryObject<AmuletMould> IRON_AMULET_MOULD = ITEMS.register("iron_amulet_mould", () ->
            new AmuletMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(2), Items.IRON_INGOT, MELTED_IRON_INGOT.get(), IRON_AMULET.get()));
    public static final RegistryObject<AmuletMould> GOLD_AMULET_MOULD = ITEMS.register("gold_amulet_mould", () ->
            new AmuletMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(1), Items.GOLD_INGOT, MELTED_GOLD_INGOT.get(), GOLD_AMULET.get()));
    public static final RegistryObject<AmuletMould> DIAMOND_AMULET_MOULD = ITEMS.register("diamond_amulet_mould", () ->
            new AmuletMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(3), Items.DIAMOND, MELTED_DIAMOND.get(), DIAMOND_AMULET.get()));
    public static final RegistryObject<AmuletMould> NETHERITE_AMULET_MOULD = ITEMS.register("netherite_amulet_mould", () ->
            new AmuletMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(4), Items.NETHERITE_INGOT, Items.NETHER_STAR, NETHERITE_AMULET.get()));


    public static final RegistryObject<JewelerBracers> COPPER_BRACERS = ITEMS.register("copper_bracers",
            () -> new JewelerBracers(Rarity.COMMON, MELTED_COPPER_INGOT.get(), 8000));
    public static final RegistryObject<JewelerBracers> IRON_BRACERS = ITEMS.register("iron_bracers",
            () -> new JewelerBracers(Rarity.UNCOMMON, MELTED_IRON_INGOT.get(), 12000));
    public static final RegistryObject<JewelerBracers> GOLD_BRACERS = ITEMS.register("gold_bracers",
            () -> new JewelerBracers(Rarity.UNCOMMON, MELTED_GOLD_INGOT.get(), 5000));
    public static final RegistryObject<JewelerBracers> DIAMOND_BRACERS = ITEMS.register("diamond_bracers",
            () -> new JewelerBracers(Rarity.RARE, MELTED_DIAMOND.get(), 40000));
    public static final RegistryObject<JewelerBracers> NETHERITE_BRACERS = ITEMS.register("netherite_bracers",
            () -> new JewelerBracers(Rarity.EPIC, Items.NETHER_STAR, 800000));

    public static final RegistryObject<BracersMould> COPPER_BRACERS_MOULD = ITEMS.register("copper_bracers_mould", () ->
            new BracersMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(1), Items.COPPER_INGOT, MELTED_COPPER_INGOT.get(), COPPER_BRACERS.get()));
    public static final RegistryObject<BracersMould> IRON_BRACERS_MOULD = ITEMS.register("iron_bracers_mould", () ->
            new BracersMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(2), Items.IRON_INGOT, MELTED_IRON_INGOT.get(), IRON_BRACERS.get()));
    public static final RegistryObject<BracersMould> GOLD_BRACERS_MOULD = ITEMS.register("gold_bracers_mould", () ->
            new BracersMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(1), Items.GOLD_INGOT, MELTED_GOLD_INGOT.get(), GOLD_BRACERS.get()));
    public static final RegistryObject<BracersMould> DIAMOND_BRACERS_MOULD = ITEMS.register("diamond_bracers_mould", () ->
            new BracersMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(3), Items.DIAMOND, MELTED_DIAMOND.get(), DIAMOND_BRACERS.get()));
    public static final RegistryObject<BracersMould> NETHERITE_BRACERS_MOULD = ITEMS.register("netherite_bracers_mould", () ->
            new BracersMould(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).durability(4), Items.NETHERITE_INGOT, Items.NETHER_STAR, NETHERITE_BRACERS.get()));

    public static final RegistryObject<Item> EMERALD_GEM = ITEMS.register("emerald_gem",
            () -> new Item(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> RUBY_GEM = ITEMS.register("ruby_gem",
            () -> new Item(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SAPPHIRE_GEM = ITEMS.register("sapphire_gem",
            () -> new Item(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> AMETHYST_GEM = ITEMS.register("amethyst_gem",
            () -> new Item(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> PRISMATIC_GEM = ITEMS.register("prismatic_gem",
            () -> new Item(JewelerMain.PLATFORM_UTIL.getDefaultItemProperties().stacksTo(1).rarity(Rarity.EPIC)));

    public static final RegistryObject<GemItemBase> CUT_EMERALD_GEM = ITEMS.register("cut_emerald_gem",
            () -> new GemItemBase(1, Rarity.COMMON, EMERALD_GEM, 1, 0, 1));
    public static final RegistryObject<GemItemBase> CUT_RUBY_GEM = ITEMS.register("cut_ruby_gem",
            () -> new GemItemBase(2, Rarity.UNCOMMON, RUBY_GEM, 2, 1, 2));
    public static final RegistryObject<GemItemBase> CUT_SAPPHIRE_GEM = ITEMS.register("cut_sapphire_gem",
            () -> new GemItemBase(3, Rarity.RARE, SAPPHIRE_GEM, 3, 2, 3));
    public static final RegistryObject<GemItemBase> CUT_AMETHYST_GEM = ITEMS.register("cut_amethyst_gem",
            () -> new GemItemBase(4, Rarity.RARE, AMETHYST_GEM, 4, 4, 2));
    public static final RegistryObject<GemItemBase> CUT_PRISMATIC_GEM = ITEMS.register("cut_prismatic_gem",
            () -> new GemItemBase(5, Rarity.EPIC, PRISMATIC_GEM, 8, 3, 2));

    public static void registerSimpleItemModels(JewelerItemBaseModelProvider provider) {
        provider.basicItem(COPPER_RING_MOULD.get());
        provider.basicItem(IRON_RING_MOULD.get());
        provider.basicItem(GOLD_RING_MOULD.get());
        provider.basicItem(DIAMOND_RING_MOULD.get());
        provider.basicItem(NETHERITE_RING_MOULD.get());

        provider.basicItem(COPPER_AMULET_MOULD.get());
        provider.basicItem(IRON_AMULET_MOULD.get());
        provider.basicItem(GOLD_AMULET_MOULD.get());
        provider.basicItem(DIAMOND_AMULET_MOULD.get());
        provider.basicItem(NETHERITE_AMULET_MOULD.get());

        provider.basicItem(COPPER_BRACERS_MOULD.get());
        provider.basicItem(IRON_BRACERS_MOULD.get());
        provider.basicItem(GOLD_BRACERS_MOULD.get());
        provider.basicItem(DIAMOND_BRACERS_MOULD.get());
        provider.basicItem(NETHERITE_BRACERS_MOULD.get());

        provider.basicItem(COPPER_BRUSH_MOULD.get());
        provider.basicItem(IRON_BRUSH_MOULD.get());
        provider.basicItem(GOLD_BRUSH_MOULD.get());
        provider.basicItem(DIAMOND_BRUSH_MOULD.get());
        provider.basicItem(NETHERITE_BRUSH_MOULD.get());

        provider.basicItem(COPPER_CHISEL_MOULD.get());
        provider.basicItem(IRON_CHISEL_MOULD.get());
        provider.basicItem(GOLD_CHISEL_MOULD.get());
        provider.basicItem(DIAMOND_CHISEL_MOULD.get());
        provider.basicItem(NETHERITE_CHISEL_MOULD.get());

        provider.basicItem(FORGE_ITEM.get());
    }

    public static void setup() {
        ForgeRegistries.ITEMS.getEntries().stream().filter(x -> x.getValue() instanceof JewelerItemBase).forEach(obj -> {
            ItemProperties.register(obj.getValue(),
                    new ResourceLocation(JewelerMain.MODID, "index"), (stack, level, living, id) -> {
                        CompoundTag tag = ItemStackHelper.getModNBT(stack);
                        return tag.contains("textureIndex") ? tag.getInt("textureIndex") : 0;
                    });
        });
    }

    public static void anvilUpdate(AnvilUpdateEvent e) {
        if (!e.getPlayer().level.isClientSide()) {
            if (e.getLeft().getItem() instanceof JewelerItemBase jewelery) {
                e.setCost(10);
                if (e.getRight().getItem() instanceof GemItemBase gem) {
                    if (EffectAPI.getEffectsNBT(e.getLeft()).getBoolean("socketed")) {
                        e.setCanceled(true);
                        return;
                    }

                    ItemStack result = e.getLeft().copy();
                    EffectAPI.addAllEffects(e.getRight(), result);
                    EffectAPI.getEffectsNBT(result).putBoolean("socketed", true);

                    Optional<IJewelryEffect> rarestEffect = Arrays.stream(EffectAPI.getEffects(result)).max(Comparator.comparingInt(a -> a.getEffectRarity().index));

                    if(rarestEffect.isPresent()){
                        IJewelryEffect eff = rarestEffect.get();
                        String I18nPrefix = "effect." + eff.effectID().getNamespace() + "." + eff.effectID().getPath() + ".displayPrefix";
                        String I18nSuffix = "effect." + eff.effectID().getNamespace() + "." + eff.effectID().getPath() + ".displaySuffix";

                        MutableComponent mutablecomponent = JewelerMain.PLATFORM_UTIL.getTranslatedComponent(I18n.exists(I18nPrefix) ? I18nPrefix : "");
                        mutablecomponent.append(JewelerMain.PLATFORM_UTIL.getTranslatedComponent(result.getDisplayName().plainCopy().getString().replace("[", "").replace("]", "")));
                        mutablecomponent.append(JewelerMain.PLATFORM_UTIL.getTranslatedComponent(I18n.exists(I18nSuffix) ? I18nSuffix : ""));
                        mutablecomponent.withStyle(eff.getEffectRarity().chatFormatting);

                        result.setHoverName(mutablecomponent);
                    }

                    ItemStackHelper.getModNBT(result).putInt("textureIndex", gem.index);

                    e.setOutput(result);
                } else if (e.getRight().getItem() == jewelery.repairItem && e.getRight().getCount() >= 16) {
                    ItemStack result = e.getLeft().copy();
                    e.setMaterialCost(16);

                    AtomicInteger repairCost = new AtomicInteger(10);
                    Arrays.stream(EffectAPI.getEffects(result)).forEach(eff -> {
                        repairCost.addAndGet(EffectAPI.getEffectValue(eff, result) * 3);
                    });
                    e.setCost(repairCost.get());
                    result.setDamageValue(0);
                    e.setOutput(result);
                }else{
                    e.setOutput(ItemStack.EMPTY);
                }
            }

            if (e.getLeft().getItem() instanceof ForgeItem) {
                ItemStack r = e.getLeft().copy();
                e.setCost(ForgeItem.getCost(r));
                e.setMaterialCost(1);

                int nextDamage = ForgeItem.getItemDamage(e.getLeft()) + 1;
                if (nextDamage >= ForgeItem.getItemMaxDamage(e.getLeft())) {
                    ItemStack result = ForgeItem.getResult(e.getLeft()).copy();

                    try {
                        EffectAPI.addAllEffects(r, result);
                        ForgeItem.addBaseEffects(e.getRight(), result);
                    } catch (UnsupportedEncodingException ex) {
                        throw new RuntimeException(ex);
                    }

                    e.setOutput(result);
                    e.setCost(ForgeItem.getCost(r));
                    //EffectAPI.copyEffects(e.getLeft(), result);
                } else {
                    try {
                        ForgeItem.addBaseEffects(e.getRight(), r);
                    } catch (UnsupportedEncodingException ex) {
                        throw new RuntimeException(ex);
                    }

                    float percent = ((float) nextDamage / (float) ForgeItem.getItemMaxDamage(r));
                    r.setDamageValue((int) (percent * 10));
                    e.setOutput(r);
                    e.setCost(ForgeItem.getCost(r));
                    ForgeItem.setItemDamage(r, nextDamage);
                }
            }
        }
    }

    public static void anvilRepair(@NotNull AnvilRepairEvent e) {
        if (!e.getEntity().level.isClientSide) {
            //    if (e.getLeft().getItem() instanceof ForgeItem && e.getRight().getItem() instanceof MouldItemBase) {
            //      e.getRight().hurt(1, e.getEntity().getRandom(), (ServerPlayer) e.getEntity());
            //     e.getEntity().getInventory().add(e.getRight());
            // }
        }
    }

    public static void itemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (!event.getEntity().level.isClientSide()) {
            
            if (event.getCrafting().getItem() instanceof ForgeItem forgeItem) {
                for (int i = 0; i < event.getInventory().getContainerSize(); i++) {
                    ItemStack is = event.getInventory().getItem(i);

                    if (is.getItem() instanceof MouldItemBase mould) {
                        CompoundTag tag = ForgeItem.getForgeItemTag(event.getCrafting());
                        if (tag.contains("result")) {
                            event.setCanceled(true);
                            return;
                        }

                        JsonObject forgeitem = AssetAPI.readForgeItem(new ResourceLocation(JewelerMain.MODID, Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(mould.crafting)).getPath())).getAsJsonObject();

                        tag.put("result", new ItemStack(mould.crafting).serializeNBT());
                        event.getCrafting().setRepairCost(ForgeItem.getCost(event.getCrafting()));
                        event.getCrafting().setDamageValue(9);
                        ForgeItem.setItemMaxDamage(event.getCrafting(), forgeitem.get("max_damage").getAsInt());
                        ForgeItem.setItemDamage(event.getCrafting(), 0);

                    }
                }
            }

            if (event.getCrafting().getItem() instanceof GemItemBase gem) {
                for (int i = 0; i < event.getInventory().getContainerSize(); i++) {
                    ItemStack is = event.getInventory().getItem(i);

                    if (is.getItem() instanceof ChiselItemBase chisel) {
                        IJewelryEffect.damageStack(event.getEntity(), is, RANDOM, 1);
                    }
                }

                JsonObject obj = AssetAPI.readLootTable(new ResourceLocation(JewelerMain.MODID, "cut_effects/" + Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(gem)).getPath())).getAsJsonObject();
                if (obj != null) {
                    JsonObject entries = obj.getAsJsonObject("entries");
                    addEffectsFor(event.getCrafting(), entries, "POSITIVE", gem.positiveRolls);
                    addEffectsFor(event.getCrafting(), entries, "NEUTRAL", gem.negativeRolls);
                    addEffectsFor(event.getCrafting(), entries, "NEGATIVE", gem.negativeRolls);
                }
            }
        }
    }

    private static final Random RANDOM = new Random();
    private static void addEffectsFor(ItemStack is, JsonObject entries, String array, int rolls){
        if(entries.has(array)){
            JsonArray arr = entries.get(array).getAsJsonArray();

            if(!arr.isEmpty()){
                for(int i=0;i<rolls;i++){
                    JsonObject entry = arr.get(RANDOM.nextInt(arr.size())).getAsJsonObject();

                    JsonObject obj1 = entry.getAsJsonObject();
                    Optional<IJewelryEffect> effect = EffectAPI.EFFECTS.stream().filter(x -> x.effectID().toString().equals(obj1.get("id").getAsString())).findAny();
                    int maxValue = obj1.get("maxLevel").getAsInt();
                    float chance = obj1.get("chance").getAsFloat();

                    if (effect.isPresent()) {
                        int level = RANDOM.nextInt(maxValue + 1);
                        if (RANDOM.nextFloat() < chance)
                            EffectAPI.setEffect(is, effect.get(), level);
                    }
                }
            }else{
                JewelerMain.LOGGER.debug("Unknown effect table entry: " + array);
            }
        }

    }

}
