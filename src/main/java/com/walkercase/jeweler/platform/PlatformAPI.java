package com.walkercase.jeweler.platform;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;

public interface PlatformAPI {

    void registerEvents(IEventBus modEventBus, IEventBus forgeBus);

    /**
     * Adds data gen providers to the registry.
     * @param gen
     * @param provider
     * @param <T>
     */
    <T extends DataProvider> void addProvider(DataGenerator gen, T provider);

    /**
     * Returns a literal MutableComponent.
     * @param text
     * @return
     */
    MutableComponent getLiteralComponent(String text);

    /**
     * Returns a translated component.
     * @param text
     * @return
     */
    MutableComponent getTranslatedComponent(String text);

    /**
     * Returns the default item properties.
     * @return
     */
    Item.Properties getDefaultItemProperties();
}
