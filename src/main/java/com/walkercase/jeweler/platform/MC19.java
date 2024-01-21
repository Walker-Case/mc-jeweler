package com.walkercase.jeweler.platform;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.unique.UniqueDropsAPI;
import com.walkercase.jeweler.item.JewelerItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class MC19 implements PlatformAPI {

    public static CreativeModeTab MOD_TAB;



    public void registerCreativeTab(CreativeModeTabEvent.Register event)
    {

        MOD_TAB = event.registerCreativeModeTab(new ResourceLocation(JewelerMain.MODID, "main_tab"), builder -> builder
                .icon(() -> new ItemStack(JewelerItems.CUT_PRISMATIC_GEM.get()))
                .title(Component.translatable(new ResourceLocation(JewelerMain.MODID, "main_tab").toString()))
                .displayItems((featureFlags, output) -> {
                    JewelerItems.ITEMS.getEntries().forEach(i->{
                        output.accept(i.get());
                    });

                    UniqueDropsAPI.getDisplayDrops().forEach(output::accept);
                })
        );
    }

    @Override
    public void registerEvents(IEventBus modEventBus, IEventBus forgeBus) {
        modEventBus.addListener(this::registerCreativeTab);
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
