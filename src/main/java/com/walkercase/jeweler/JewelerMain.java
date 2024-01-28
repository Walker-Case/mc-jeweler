package com.walkercase.jeweler;

import com.mojang.logging.LogUtils;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.api.unique.UniqueDropsAPI;
import com.walkercase.jeweler.block.JewelerBlocks;
import com.walkercase.jeweler.event.BlockHelper;
import com.walkercase.jeweler.generated.*;
import com.walkercase.jeweler.item.JewelerItems;
import com.walkercase.jeweler.platform.MC19;
import com.walkercase.jeweler.platform.PlatformAPI;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
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
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(JewelerMain.MODID)
@Mod.EventBusSubscriber
public class JewelerMain {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "jeweler";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final PlatformAPI PLATFORM_UTIL = new MC19();

    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    //public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    // public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)));
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path


    public JewelerMain() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, JewelerConfig.commonSpec, "jeweler-common-config.toml");

        JewelerItems.ITEMS.register(modEventBus);
        JewelerBlocks.BLOCKS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::enqueue);
        modEventBus.addListener(this::gatherDataEvent);
        
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

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            JewelerItems.setup();
            UniqueDropsAPI.generateUniqueDrops();
            UniqueDropsAPI.registerUniqueDropEvents(FMLJavaModLoadingContext.get().getModEventBus(), MinecraftForge.EVENT_BUS);
        });

    }

    public void enqueue(InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.RING.getMessageBuilder().size(4).build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().size(1).build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HANDS.getMessageBuilder().size(2).build());
    }

}
