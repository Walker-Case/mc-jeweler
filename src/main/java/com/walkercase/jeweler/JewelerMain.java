package com.walkercase.jeweler;

import com.mojang.logging.LogUtils;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.api.unique.UniqueDropsAPI;
import com.walkercase.jeweler.block.JewelerBlocks;
import com.walkercase.jeweler.effect.positive.summon.WolfSummonEffect;
import com.walkercase.jeweler.entity.JewelerEntities;
import com.walkercase.jeweler.event.BlockHelper;
import com.walkercase.jeweler.generated.*;
import com.walkercase.jeweler.item.JewelerItems;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import com.walkercase.jeweler.platform.MC19;
import com.walkercase.jeweler.platform.PlatformAPI;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.util.Arrays;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(JewelerMain.MODID)
@Mod.EventBusSubscriber
public class JewelerMain {
    public static final String MODID = "jeweler";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final PlatformAPI PLATFORM_UTIL = new MC19();


    public JewelerMain() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, JewelerConfig.commonSpec, "jeweler-common-config.toml");

        JewelerItems.ITEMS.register(modEventBus);
        JewelerBlocks.BLOCKS.register(modEventBus);
        JewelerEntities.ENTITY_TYPES.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::enqueue);
        modEventBus.addListener(this::gatherDataEvent);
        modEventBus.addListener(JewelerEntities::registerEntityAttributes);

        MinecraftForge.EVENT_BUS.addListener(JewelerMain::serverShutdownEvent);
        MinecraftForge.EVENT_BUS.addListener(EffectAPI.Events::onLivingHurtEvent);
        MinecraftForge.EVENT_BUS.addListener(BlockHelper.GeodeHelper::blockBreakEvent);
        MinecraftForge.EVENT_BUS.addListener(BlockHelper.LootHelper::blockBreakEvent);
        MinecraftForge.EVENT_BUS.addListener(JewelerItems::itemCrafted);
        MinecraftForge.EVENT_BUS.addListener(JewelerItems::anvilUpdate);
        MinecraftForge.EVENT_BUS.addListener(JewelerItems::anvilRepair);

        EffectAPI.EFFECTS.forEach(eff->{
            eff.registerEvents(modEventBus, MinecraftForge.EVENT_BUS);
        });

        PLATFORM_UTIL.registerEvents(modEventBus, MinecraftForge.EVENT_BUS);
    }

    private void gatherDataEvent(GatherDataEvent event) {
        PLATFORM_UTIL.addProvider(event.getGenerator(), new SimpleItemModelProvider(event.getGenerator(), JewelerMain.MODID + ".simplejeweleritem", event.getExistingFileHelper()));
        PLATFORM_UTIL.addProvider(event.getGenerator(), new JewelerItemRecipeProvider(event.getGenerator()));
        PLATFORM_UTIL.addProvider(event.getGenerator(), new UniqueLanguageProvider(event.getGenerator().getPackOutput(), JewelerMain.MODID, "en_us"));
        PLATFORM_UTIL.addProvider(event.getGenerator(), new PatchouliGenerator(MODID, "en_us", event.getGenerator().getPackOutput(), event.getExistingFileHelper()));
        PLATFORM_UTIL.addProvider(event.getGenerator(), new JewelerItemBaseModelProvider(event.getGenerator(), JewelerMain.MODID + ".jeweleritem", event.getExistingFileHelper()));
    }

    public static void serverShutdownEvent(ServerStoppingEvent event){
        //Kill all summons on server shutdown.
        event.getServer().getAllLevels().forEach(level->{
            level.getEntities().getAll().forEach(ent->{
                if(ent.getPersistentData().getBoolean("jewelerSummon"))
                    ent.kill();
            });
        });
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            JewelerItems.setup();
            UniqueDropsAPI.generateUniqueDrops();
            UniqueDropsAPI.registerUniqueDropEvents(FMLJavaModLoadingContext.get().getModEventBus(), MinecraftForge.EVENT_BUS);
        });

        DistExecutor.safeRunWhenOn(Dist.CLIENT, ()->JewelerEntities::registerEntityRenderers);
    }

    public void enqueue(InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.RING.getMessageBuilder().size(4).build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().size(1).build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HANDS.getMessageBuilder().size(2).build());
    }

}
