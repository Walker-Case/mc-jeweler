package com.walkercase.jeweler.api;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.exception.DuplicateEntryException;
import com.walkercase.jeweler.effect.IJewelryEffect;
import com.walkercase.jeweler.effect.negative.*;
import com.walkercase.jeweler.effect.negative.potion.*;
import com.walkercase.jeweler.effect.neutral.UndyingJewelryEffect;
import com.walkercase.jeweler.effect.neutral.resist.*;
import com.walkercase.jeweler.effect.positive.*;
import com.walkercase.jeweler.effect.positive.potion.*;
import com.walkercase.jeweler.effect.positive.summon.*;
import com.walkercase.jeweler.item.ItemStackHelper;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.ApiStatus;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.walkercase.jeweler.effect.IJewelryEffect.EffectType.NEUTRAL;

public class EffectAPI {

    /**
     * Contains a list of all registered effects.
     */
    public static final ArrayList<IJewelryEffect> EFFECTS = new ArrayList<>();

    /**
     * After how many ticks to do ItemStack damage.
     */
    public static final int JEWELERY_DAMAGE_TICK_DELAY = 360;

    /*
     * POSITIVE
     */
    public static final AgileJewelryEffect AGILE_JEWELRY_EFFECT = new AgileJewelryEffect();
    public static final BloodlustJeweleryEffect BLOODLUST_JEWELERY_EFFECT = new BloodlustJeweleryEffect();
    public static final BreserkerJewelryEffect BRESERKER_JEWELRY_EFFECT = new BreserkerJewelryEffect();
    public static final BulwarkJewelryEffect BULWARK_JEWELRY_EFFECT = new BulwarkJewelryEffect();
    public static final LuckJeweleryEffect LUCK_JEWELERY_EFFECT = new LuckJeweleryEffect();
    public static final StaminaJewelryEffect STAMINA_JEWELRY_EFFECT = new StaminaJewelryEffect();
    public static final UnbreakingJewelryEffect UNBREAKING_JEWELRY_EFFECT = new UnbreakingJewelryEffect();
    public static final FingersJewelryEffect FINGERS_JEWELRY_EFFECT = new FingersJewelryEffect();
    public static final ReflectJewelryEffect REFLECT_JEWELRY_EFFECT = new ReflectJewelryEffect();
    public static final KnockbackResistJewelryEffect KNOCKBACK_RESIST_JEWELRY_EFFECT = new KnockbackResistJewelryEffect();
    public static final JumpStrengthEffect JUMP_STRENGTH_EFFECT = new JumpStrengthEffect();
    public static final IgniteJewelryEffect IGNITE_JEWELRY_EFFECT = new IgniteJewelryEffect();
    public static final SavingJewelryEffect SAVING_JEWELRY_EFFECT = new SavingJewelryEffect();
    public static final RegenerationJeweleryEffect REGENERATION_JEWELERY_EFFECT = new RegenerationJeweleryEffect();
    public static final WaterBreathingJeweleryEffect WATER_BREATHING_JEWELERY_EFFECT = new WaterBreathingJeweleryEffect();
    public static final NightVisionJewelryEffect NIGHT_VISION_JEWELRY_EFFECT = new NightVisionJewelryEffect();
    public static final SaturationJeweleryEffect SATURATION_JEWELERY_EFFECT = new SaturationJeweleryEffect();
    public static final SlowFallingJeweleryEffect SLOW_FALLING_JEWELERY_EFFECT = new SlowFallingJeweleryEffect();
    public static final AbsorptionJeweleryEffect ABSORPTION_JEWELERY_EFFECT = new AbsorptionJeweleryEffect();
    public static final UnluckJeweleryEffect UNLUCK_JEWELERY_EFFECT = new UnluckJeweleryEffect();
    public static final FireResistanceJeweleryEffect FIRE_RESISTANCE_JEWELERY_EFFECT = new FireResistanceJeweleryEffect();
    public static final DoubleFishingEffect DOUBLE_FISHING_EFFECT = new DoubleFishingEffect();
    public static final XpGainJeweleryEffect XP_GAIN_JEWELERY_EFFECT = new XpGainJeweleryEffect();
    public static final IncreasedCriticalEffect INCREASED_CRITICAL_EFFECT = new IncreasedCriticalEffect();
    public static final WoodcutterEffect WOODCUTTER_EFFECT = new WoodcutterEffect();
    public static final MinerEffect MINER_EFFECT = new MinerEffect();
    public static final ProspectingEffect PROSPECTING_EFFECT = new ProspectingEffect();
    public static final ExcavationEffect EXCAVATION_EFFECT = new ExcavationEffect();
    public static final ToolRepairEffect TOOL_REPAIR_EFFECT = new ToolRepairEffect();
    public static final ArmorRepairEffect ARMOR_REPAIR_EFFECT = new ArmorRepairEffect();
    public static final WolfSummonEffect WOLF_SUMMON_EFFECT = new WolfSummonEffect();
    public static final CatSummonEffect CAT_SUMMON_EFFECT = new CatSummonEffect();
    public static final SpectralSkeletonSummonEffect SPECTRAL_SKELETON_SUMMON_EFFECT = new SpectralSkeletonSummonEffect();
    public static final SpectralZombieSummonEffect SPECTRAL_ZOMBIE_SUMMON_EFFECT = new SpectralZombieSummonEffect();
    public static final SpectralSpiderSummonEffect SPECTRAL_SPIDER_SUMMON_EFFECT = new SpectralSpiderSummonEffect();
    public static final SpectralCreeperSummonEffect SPECTRAL_CREEPER_SUMMON_EFFECT = new SpectralCreeperSummonEffect();
    public static final SpectralPhantomSummonEffect SPECTRAL_PHANTOM_SUMMON_EFFECT = new SpectralPhantomSummonEffect();
    public static final SpectralBlazeSummonEffect SPECTRAL_BLAZE_SUMMON_EFFECT = new SpectralBlazeSummonEffect();

    /*
     * NEUTRAL
     */
    public static final UndyingJewelryEffect UNDYING_JEWELRY_EFFECT = new UndyingJewelryEffect();
    public static final ZombieResistEffect ZOMBIE_RESIST_EFFECT = new ZombieResistEffect();
    public static final SkeletonResistEffect SKELETON_RESIST_EFFECT = new SkeletonResistEffect();
    public static final SpiderResistEffect SPIDER_RESIST_EFFECT = new SpiderResistEffect();
    public static final CreeperResistEffect CREEPER_RESIST_EFFECT = new CreeperResistEffect();
    public static final PillagerResistEffect PILLAGER_RESIST_EFFECT = new PillagerResistEffect();
    public static final VindicatorResistEffect VINDICATOR_RESIST_EFFECT = new VindicatorResistEffect();
    public static final EvokerResistEffect EVOKER_RESIST_EFFECT = new EvokerResistEffect();
    public static final VexResistEffect VEX_RESIST_EFFECT = new VexResistEffect();
    public static final BlazeResistEffect BLAZE_RESIST_EFFECT = new BlazeResistEffect();
    public static final GhastResistEffect GHAST_RESIST_EFFECT = new GhastResistEffect();
    public static final PhantomResistEffect PHANTOM_RESIST_EFFECT = new PhantomResistEffect();
    public static final SilverfishResistEffect SILVERFISH_RESIST_EFFECT = new SilverfishResistEffect();
    public static final WitherResistEffect WITHER_RESIST_EFFECT = new WitherResistEffect();
    public static final EnderDragonResistEffect ENDER_DRAGON_RESIST_EFFECT = new EnderDragonResistEffect();

    /*
     * NEGATIVE
     */
    public static final FeedingJewelryEffect FEEDING_JEWELRY_EFFECT = new FeedingJewelryEffect();
    public static final FrailJewelryEffect FRAIL_JEWELRY_EFFECT = new FrailJewelryEffect();
    public static final HealthReductionJewelryEffect HEALTH_REDUCTION_JEWELRY_EFFECT = new HealthReductionJewelryEffect();
    public static final InvertedHealingJeweleryEffect INVERTED_HEALING_JEWELERY_EFFECT = new InvertedHealingJeweleryEffect();
    public static final LightenedJewelryEffect LIGHTENED_JEWELRY_EFFECT = new LightenedJewelryEffect();
    public static final SlownessJewelryEffect SLOWNESS_JEWELRY_EFFECT = new SlownessJewelryEffect();
    public static final WeakenedJewelryEffect WEAKENED_JEWELRY_EFFECT = new WeakenedJewelryEffect();
    public static final WimpyJewelryEffect WIMPY_JEWELRY_EFFECT = new WimpyJewelryEffect();
    public static final JumpWeaknessEffect JUMP_WEAKNESS_EFFECT = new JumpWeaknessEffect();
    public static final DoubleCraftingEffect DOUBLE_CRAFTING_EFFECT = new DoubleCraftingEffect();
    public static final ConfusionJeweleryEffect CONFUSION_JEWELERY_EFFECT = new ConfusionJeweleryEffect();
    public static final BlindnessJeweleryEffect BLINDNESS_JEWELERY_EFFECT = new BlindnessJeweleryEffect();
    public static final PoisonJeweleryEffect POISON_JEWELERY_EFFECT = new PoisonJeweleryEffect();
    public static final WitherJeweleryEffect WITHER_JEWELERY_EFFECT = new WitherJeweleryEffect();
    public static final DigSlowdownJeweleryEffect DIG_SLOWDOWN_JEWELERY_EFFECT = new DigSlowdownJeweleryEffect();
    public static final XpReductionJeweleryEffect XP_REDUCTION_JEWELERY_EFFECT = new XpReductionJeweleryEffect();
    public static final ReducedCriticalEffect REDUCED_CRITICAL_EFFECT = new ReducedCriticalEffect();
    public static final ReducedWoodcutterEffect REDUCED_WOODCUTTER_EFFECT = new ReducedWoodcutterEffect();

    static {
        /*
         * POSITIVE
         */
        register(AGILE_JEWELRY_EFFECT);
        register(BLOODLUST_JEWELERY_EFFECT);
        register(BRESERKER_JEWELRY_EFFECT);
        register(BULWARK_JEWELRY_EFFECT);
        register(LUCK_JEWELERY_EFFECT);
        register(STAMINA_JEWELRY_EFFECT);
        register(UNBREAKING_JEWELRY_EFFECT);
        register(FINGERS_JEWELRY_EFFECT);
        register(REFLECT_JEWELRY_EFFECT);
        register(KNOCKBACK_RESIST_JEWELRY_EFFECT);
        register(JUMP_STRENGTH_EFFECT);
        register(IGNITE_JEWELRY_EFFECT);
        register(DOUBLE_CRAFTING_EFFECT);
        register(SAVING_JEWELRY_EFFECT);
        register(REGENERATION_JEWELERY_EFFECT);
        register(WATER_BREATHING_JEWELERY_EFFECT);
        register(NIGHT_VISION_JEWELRY_EFFECT);
        register(SATURATION_JEWELERY_EFFECT);
        register(SLOW_FALLING_JEWELERY_EFFECT);
        register(ABSORPTION_JEWELERY_EFFECT);
        register(UNLUCK_JEWELERY_EFFECT);
        register(FIRE_RESISTANCE_JEWELERY_EFFECT);
        register(DOUBLE_FISHING_EFFECT);
        register(XP_GAIN_JEWELERY_EFFECT);
        register(INCREASED_CRITICAL_EFFECT);
        register(WOODCUTTER_EFFECT);
        register(MINER_EFFECT);
        register(PROSPECTING_EFFECT);
        register(EXCAVATION_EFFECT);
        register(TOOL_REPAIR_EFFECT);
        register(ARMOR_REPAIR_EFFECT);
        register(WOLF_SUMMON_EFFECT);
        register(CAT_SUMMON_EFFECT);
        register(SPECTRAL_SKELETON_SUMMON_EFFECT);
        register(SPECTRAL_ZOMBIE_SUMMON_EFFECT);
        register(SPECTRAL_SPIDER_SUMMON_EFFECT);
        register(SPECTRAL_CREEPER_SUMMON_EFFECT);
        register(SPECTRAL_PHANTOM_SUMMON_EFFECT);
        register(SPECTRAL_BLAZE_SUMMON_EFFECT);

        /*
         * NEUTRAL
         */
        register(UNDYING_JEWELRY_EFFECT);
        register(ZOMBIE_RESIST_EFFECT);
        register(SKELETON_RESIST_EFFECT);
        register(SPIDER_RESIST_EFFECT);
        register(CREEPER_RESIST_EFFECT);
        register(PILLAGER_RESIST_EFFECT);
        register(VINDICATOR_RESIST_EFFECT);
        register(EVOKER_RESIST_EFFECT);
        register(VEX_RESIST_EFFECT);
        register(BLAZE_RESIST_EFFECT);
        register(GHAST_RESIST_EFFECT);
        register(PHANTOM_RESIST_EFFECT);
        register(SILVERFISH_RESIST_EFFECT);
        register(WITHER_RESIST_EFFECT);
        register(ENDER_DRAGON_RESIST_EFFECT);

        /*
         * NEGATIVE
         */
        register(FEEDING_JEWELRY_EFFECT);
        register(FRAIL_JEWELRY_EFFECT);
        register(HEALTH_REDUCTION_JEWELRY_EFFECT);
        register(INVERTED_HEALING_JEWELERY_EFFECT);
        register(LIGHTENED_JEWELRY_EFFECT);
        register(SLOWNESS_JEWELRY_EFFECT);
        register(WEAKENED_JEWELRY_EFFECT);
        register(WIMPY_JEWELRY_EFFECT);
        register(JUMP_WEAKNESS_EFFECT);
        register(CONFUSION_JEWELERY_EFFECT);
        register(BLINDNESS_JEWELERY_EFFECT);
        register(POISON_JEWELERY_EFFECT);
        register(WITHER_JEWELERY_EFFECT);
        register(DIG_SLOWDOWN_JEWELERY_EFFECT);
        register(XP_REDUCTION_JEWELERY_EFFECT);
        register(REDUCED_CRITICAL_EFFECT);
        register(REDUCED_WOODCUTTER_EFFECT);
    }

    /**
     * Returns an effect that matches the given ResourceLocation.
     * @param rl Resoure
     * @return
     */
    public static Optional<IJewelryEffect> getEffect(ResourceLocation rl) {
        return EFFECTS.stream().filter(x -> x.effectID().equals(rl)).findAny();
    }

    /**
     * Called to register a new jewelry effect.
     * This MUST be done for an effect to function.
     * @param effect
     * @throws DuplicateEntryException
     */
    public static void register(IJewelryEffect effect) throws DuplicateEntryException {
        if(EFFECTS.contains(effect))
            throw new DuplicateEntryException("Duplicate entry " + effect.effectID().toString() + " in EffectAPI effect registry!");
        EFFECTS.add(effect);
    }

    /**
     * Returns a list of effect interfaces contained in the itemstack.
     *
     * @param is
     * @return
     */
    public static IJewelryEffect[] getEffects(ItemStack is) {
        CompoundTag tag = getEffectsNBT(is);

        List<IJewelryEffect> o = EFFECTS.stream().filter(eff -> {
            ResourceLocation location = eff.effectID();

            return tag.getAllKeys().stream().anyMatch(key -> key.equals(location.toString()));
        }).toList();

        return o.toArray(new IJewelryEffect[o.size()]);
    }

    /**
     * Returns all attribute modifiers for the given stack.
     *
     * @param slotContext
     * @param uuid
     * @param stack
     * @return
     */
    public static Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack, JewelerItemBase item) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();

        Arrays.stream(EffectAPI.getEffects(stack)).filter(x -> EffectAPI.getEffectValue(x, stack) > 0).forEach(effect -> {
            int value = EffectAPI.getEffectValue(effect, stack);
            if (value > 0) {
                Multimap<Attribute, AttributeModifier> modif = effect.getAttributeModifiers(slotContext, uuid, stack, item);

                if (modif != null)
                    modif.forEach(atts::put);
            }
        });

        return atts;
    }

    /**
     * Returns an empty attribute map.
     * @return
     */
    public static Multimap<Attribute, AttributeModifier> getEmptyAttributeMap() {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        return atts;
    }

    /**
     * Returns the effect level for the given ItemStack.
     * @param effect
     * @param is
     * @return
     */
    public static int getEffectValue(IJewelryEffect effect, ItemStack is) {
        CompoundTag tag = getEffectsNBT(is);
        ResourceLocation location = effect.effectID();
        Optional<String> opt = tag.getAllKeys().stream().filter(key -> key.equals(location.toString())).findAny();
        return opt.map(tag::getInt).orElse(0);
    }

    /**
     * Returns true if the effect NBT data tag exists.
     * @param is
     * @return
     */
    public static boolean effectsNBTExists(ItemStack is) {
        return ItemStackHelper.getModNBT(is).contains("effects");
    }

    /**
     * Returns effect specific NBTTagCompound.
     * @param is
     * @param effect
     * @return
     */
    public static CompoundTag getEffectsDataNBT(ItemStack is, IJewelryEffect effect) {
        CompoundTag tag = ItemStackHelper.getTransientNBT(is);
        ResourceLocation location = effect.effectID();
        if (!tag.contains("effect.data." + location.getNamespace() + "." + location.getPath()))
            tag.put("effect.data." + location.getNamespace() + "." + location.getPath(), new CompoundTag());
        return tag.getCompound("effect.data." + location.getNamespace() + "." + location.getPath());
    }

    /**
     * Returns the effects NBT for the given ItemStack.
     * @param is
     * @return
     */
    public static CompoundTag getEffectsNBT(ItemStack is) {
        CompoundTag tag = ItemStackHelper.getModNBT(is);
        if (!tag.contains("effects")) {
            tag.put("effects", new CompoundTag());
        }

        tag.getCompound("effects").getAllKeys().stream().filter(key -> tag.getCompound("effects").getInt(key) <= 0).forEach(tag::remove);

        return tag.getCompound("effects");
    }

    /**
     * Returns true if the given ItemStack is foil or has the "enchanted" visual.
     * @param is
     * @return
     */
    public static boolean isFoil(ItemStack is){
        return getEffectsNBT(is).getBoolean("foil");
    }

    /**
     * Sets the given ItemStack to foil or to show the "enchanted" visual.
     * Only works on items extending JewelerItemBase or GemItemBase.
     * @param is
     */
    public static void setFoil(ItemStack is, boolean b){
        getEffectsNBT(is).putBoolean("foil", b);
    }

    /**
     * Add an effect and add to level.
     *
     * @param is
     * @param effect
     * @param level
     * @return
     */
    public static ItemStack addEffect(ItemStack is, IJewelryEffect effect, int level) {
        return setEffect(is, effect, getEffectValue(effect, is) + level);
    }

    /**
     * Add all effects from is1 to is2 and add to levels.
     *
     * @param is1
     * @param is2
     * @return
     */
    public static ItemStack addAllEffects(ItemStack is1, ItemStack is2) {
        Arrays.stream(EffectAPI.getEffects(is1)).forEach(eff -> EffectAPI.addEffect(is2, eff, EffectAPI.getEffectValue(eff, is1)));
        return is2;
    }

    /**
     * Set effect level.
     *
     * @param is
     * @param effect
     * @param level
     * @return
     */
    public static ItemStack setEffect(ItemStack is, IJewelryEffect effect, int level) {
        if (level > 0)
            getEffectsNBT(is).putInt(effect.effectID().toString(), level);
        else
            getEffectsNBT(is).remove(effect.effectID().toString());
        return is;
    }

    /**
     * Copy effects from is1 to is2 exactly.
     *
     * @param is1
     * @param is2
     */
    public static void copyEffects(ItemStack is1, ItemStack is2) {
        for (IJewelryEffect effect : EffectAPI.getEffects(is1)) {
            EffectAPI.setEffect(is2, effect, EffectAPI.getEffectValue(effect, is1));
        }
    }

    /**
     * Removes all effects from the provided item.
     *
     * @param is1
     */
    public static void removeEffects(ItemStack is1) {
        Arrays.stream(EffectAPI.getEffects(is1)).forEach(eff -> {
            EffectAPI.setEffect(is1, eff, 0);
        });
    }

    /**
     * Adds the effects tooltip to the given itemstack.
     *
     * @param is
     * @param list
     */
    public static void addEffectTooltip(ItemStack is, List<Component> list) {
        Arrays.stream(getEffects(is)).forEach(effect -> {
            int value = getEffectValue(effect, is);
            if (value > 0)
                list.add(getEffectComponent(effect, value));
        });
    }

    /**
     * Returns the IJewelryEffect MutableComponent for tooltips.
     * @param effect
     * @param level
     * @return
     */
    private static MutableComponent getEffectComponent(IJewelryEffect effect, int level) {
        ResourceLocation location = effect.effectID();
        JsonObject i18n = AssetAPI.readLang(JewelerMain.MODID, "en_us");

        ChatFormatting effectFormat = effect.getEffectType() ==
                IJewelryEffect.EffectType.POSITIVE ? ChatFormatting.GREEN
                : effect.getEffectType() == NEUTRAL ? ChatFormatting.GOLD :
                ChatFormatting.RED;

        ChatFormatting loreFormat = effect.getEffectType() ==
                IJewelryEffect.EffectType.POSITIVE ? ChatFormatting.GREEN : ChatFormatting.RED;

        MutableComponent mutablecomponent = JewelerMain.PLATFORM_UTIL.getTranslatedComponent("effect." + location.getNamespace() + "." + location.getPath());
        mutablecomponent.withStyle(effectFormat);
        mutablecomponent.append(JewelerMain.PLATFORM_UTIL.getLiteralComponent(" "));
        mutablecomponent.append(JewelerMain.PLATFORM_UTIL.getLiteralComponent(level + " "));

        if(effect.getEffectType() != NEUTRAL){
            MutableComponent mutablecomponent2 = JewelerMain.PLATFORM_UTIL.getTranslatedComponent("effect.lore." + location.getNamespace() + "." + location.getPath());
            mutablecomponent2.withStyle(ChatFormatting.ITALIC);
            mutablecomponent2.withStyle(effectFormat);

            mutablecomponent.append(mutablecomponent2);
        }else{
            MutableComponent lore0 = JewelerMain.PLATFORM_UTIL.getTranslatedComponent("effect.lore." + location.getNamespace() + "." + location.getPath() + ".0");
            lore0.withStyle(ChatFormatting.ITALIC);
            lore0.withStyle(ChatFormatting.AQUA);

            mutablecomponent.append(lore0);

            MutableComponent but = JewelerMain.PLATFORM_UTIL.getTranslatedComponent(i18n.has("literal.but") ? i18n.get("literal.but").getAsString() : " BUT ");
            but.withStyle(ChatFormatting.RED);

            mutablecomponent.append(but);

            MutableComponent lore1 = JewelerMain.PLATFORM_UTIL.getTranslatedComponent("effect.lore." + location.getNamespace() + "." + location.getPath() + ".1");
            lore1.withStyle(ChatFormatting.LIGHT_PURPLE);

            mutablecomponent.append(lore1);
        }

        return mutablecomponent;
    }

    /**
     * Returns true every x calls.
     * @param tag
     * @param maxValue
     * @return
     */
    public static boolean doAfterXCalls(CompoundTag tag, String key, int maxValue){
        AtomicBoolean b = new AtomicBoolean(false);
        if (!tag.contains(key))
            tag.putInt(key, maxValue);

        tag.putInt(key, tag.getInt(key) - 1);

        if (tag.getInt(key) <= 0) {
            tag.putInt(key, maxValue);

            b.set(true);
        }

        return b.get();
    }

    /**
     * Contains methods regarding specific events.
     */
    @ApiStatus.Internal
    public static class Events {

        /**
         * Called to execute the IJeweleryEffect::onHurtEvent event.
         * @param e
         */
        public static void onLivingHurtEvent(LivingHurtEvent e) {
            if (!e.getEntity().level.isClientSide()) {
                    CuriosApi.getCuriosHelper().getEquippedCurios(e.getEntity()).filter(x -> true).ifPresent(item -> {
                        for (int i = 0; i < item.getSlots(); i++) {
                            ItemStack is = item.getStackInSlot(i);

                            if (is.getItem() instanceof JewelerItemBase base) {
                                Arrays.stream(getEffects(is)).filter(eff
                                        -> EffectAPI.getEffectValue(eff, is) > 0).forEach(eff -> eff.onHurtEvent(e, is, base));
                            }
                        }
                    });

                    if (e.getSource().getEntity() instanceof LivingEntity source){
                        CuriosApi.getCuriosHelper().getEquippedCurios(source).filter(x -> true).ifPresent(item -> {
                            for (int i = 0; i < item.getSlots(); i++) {
                                ItemStack is = item.getStackInSlot(i);

                                if (is.getItem() instanceof JewelerItemBase base) {
                                    Arrays.stream(getEffects(is)).filter(eff
                                            -> EffectAPI.getEffectValue(eff, is) > 0).forEach(eff -> eff.onHurtEntityEvent(e, is, base));
                                }
                            }
                        });
                    }
            }
        }

        /**
         * Called to execute the IJewelryEffect::curioBreak event.
         * @param player
         * @param stack
         */
        public static void curioBreak(Player player, ItemStack stack) {
            if (stack.getItem() instanceof JewelerItemBase) {
                Arrays.stream(getEffects(stack)).filter(eff -> EffectAPI.getEffectValue(eff, stack) > 0).forEach(eff -> eff.curioBreak(player, stack));
            }
        }

        static class TickDelay{
            int current;
            long lastTime;

            public TickDelay(int current, long lastTime){
                this.current = current;
                this.lastTime = lastTime;
            }
        }
        private static final HashMap<UUID, TickDelay> curioTickDelay = new HashMap<UUID, TickDelay>();
        private static long lastCleanup = 0;

        private static synchronized void cleanupTimers(){
            lastCleanup = System.currentTimeMillis();
            ArrayList<UUID> clean = new ArrayList<>();
            curioTickDelay.entrySet().stream().filter(entry-> entry.getValue().lastTime + 50000 < System.currentTimeMillis()).forEach(e->{
                clean.add(e.getKey());
            });

            clean.forEach(curioTickDelay::remove);
        }

        /**
         * Called to execute the IJewelryEffect::damageCurioTick and IJeweleryEffect::curioTick events.
         * @param slotContext
         * @param stack
         */
        public static boolean onCurioTick(SlotContext slotContext, ItemStack stack) {
            AtomicBoolean b = new AtomicBoolean(true);
            if (stack.getItem() instanceof JewelerItemBase item) {
                CompoundTag tag = ItemStackHelper.getModNBT(stack);
                UUID uuid = ItemStackHelper.getUUID(stack);

                if(!curioTickDelay.containsKey(uuid))
                    curioTickDelay.put(uuid, new TickDelay(0, System.currentTimeMillis()));

                curioTickDelay.get(uuid).current ++;

                if(curioTickDelay.get(uuid).current >= JEWELERY_DAMAGE_TICK_DELAY){
                    b.set(false);

                    curioTickDelay.get(uuid).current = 0;
                    curioTickDelay.get(uuid).lastTime = System.currentTimeMillis();

                    Arrays.stream(getEffects(stack)).filter(eff -> EffectAPI.getEffectValue(eff, stack) > 0).forEach(eff -> {
                        if (eff.damageCurioTick(slotContext, stack, item))
                            b.set(true);
                    });
                }

                Arrays.stream(getEffects(stack)).filter(eff -> EffectAPI.getEffectValue(eff, stack) > 0).forEach(eff ->
                        eff.curioTick(slotContext, stack, item));
            }

            if(lastCleanup + 25000 < System.currentTimeMillis())
                cleanupTimers();
            return b.get();
        }

        /**
         * Called to execute the IJewelryEffect::onEquip event.
         * @param slotContext
         * @param prevStack
         * @param stack
         */
        public static void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
            if (stack.getItem() instanceof JewelerItemBase item) {
                if(!ItemStackHelper.equalsIgnoreTransient(prevStack, stack)){
                    Arrays.stream(getEffects(stack)).filter(eff -> EffectAPI.getEffectValue(eff, stack) > 0).forEach(eff ->
                            eff.onEquip(slotContext, prevStack, stack, item));
                }
            }
        }

        /**
         * Called to execute the IJewelryEffect::onUnequip event.
         * @param slotContext
         * @param newStack
         * @param stack
         */
        public static void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
            if (stack.getItem() instanceof JewelerItemBase item) {
                if(!ItemStackHelper.equalsIgnoreTransient(newStack, stack)){
                    Arrays.stream(getEffects(stack)).filter(eff -> EffectAPI.getEffectValue(eff, stack) > 0).forEach(eff ->
                            eff.onUnequip(slotContext, newStack, stack, item));
                }
            }
        }
    }

}
