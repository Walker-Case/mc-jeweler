package com.walkercase.jeweler.platform;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;

public class MC18 implements PlatformAPI{
    @Override
    public void registerEvents(IEventBus modEventBus, IEventBus forgeBus) {

    }

    @Override
    public <T extends DataProvider> void addProvider(DataGenerator gen, T provider) {
        gen.addProvider(provider);
    }

    @Override
    public MutableComponent getLiteralComponent(String text) {
        return new TextComponent(text);
    }

    @Override
    public MutableComponent getTranslatedComponent(String text) {
        return new TranslatableComponent(text);
    }

    @Override
    public Item.Properties getDefaultItemProperties() {
        return new Item.Properties();
    }
}
