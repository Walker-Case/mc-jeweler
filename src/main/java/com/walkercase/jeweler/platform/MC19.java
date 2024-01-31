package com.walkercase.jeweler.platform;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.unique.UniqueDropsAPI;
import com.walkercase.jeweler.item.JewelerItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MC19 implements PlatformAPI {

    private static final DeferredRegister<CreativeModeTab> REGISTRAR = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, JewelerMain.MODID);
    public static final RegistryObject<CreativeModeTab> MOD_TAB = REGISTRAR.register("main_tab", () -> CreativeModeTab.builder()
            // Set name of tab to display
            .title(Component.translatable("item_group." + JewelerMain.MODID + ".main"))
            // Set icon of creative tab
            .icon(() -> new ItemStack(JewelerItems.CUT_PRISMATIC_GEM.get()))
            // Add default items to tab
            .displayItems((params, output) -> {
                UniqueDropsAPI.getUniqueDrops().forEach(output::accept);
                JewelerItems.ITEMS.getEntries().forEach(ro->{
                    output.accept(ro.get());
                });
            })
            .build()
    );

    @Override
    public void registerEvents(IEventBus modEventBus, IEventBus forgeBus) {
        REGISTRAR.register(modEventBus);
    }

    @Override
    public <T extends DataProvider> void addProvider(DataGenerator gen, T provider) {
        gen.addProvider(true, provider);
    }

    @Override
    public MutableComponent getLiteralComponent(String text) {
        return Component.literal(text);
    }

    @Override
    public MutableComponent getTranslatedComponent(String text) {
        return Component.translatable(text);
    }

    @Override
    public Item.Properties getDefaultItemProperties() {
        return new Item.Properties();
    }
}
