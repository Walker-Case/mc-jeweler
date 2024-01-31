package com.walkercase.jeweler.api.unique.drops;

import com.walkercase.jeweler.api.unique.ItemStackBuilder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.Random;

/**
 * Used to add unique drops to the creative tab and register drop specific events.
 */
public class UniqueDrop{
    protected static final Random RANDOM = new Random();
    private ItemStackBuilder builder;
    private float chance;

    public boolean addToTab = true;

    /**
     * Create a new Unique Drop. Note: This class does nothing on it's own.
     * @param builder
     * @param chance
     */
    public UniqueDrop(ItemStackBuilder builder, float chance){
        this.builder = builder;
        this.chance = chance;
    }

    /**
     * Set to true if this drop should be added to the creative tab.
     * True by default.
     * @param b True to add to creative tab.
     * @return this
     */
    public UniqueDrop setAddToTab(boolean b){
        this.addToTab = b;
        return this;
    }

    /**
     * Returns true if this drop passes it's roll check.
     * @return
     */
    public boolean rollCheck(){
        return RANDOM.nextFloat() < this.chance;
    }

    /**
     * The itemstack to add to the creative table.
     * @return
     */
    public ItemStackBuilder getItemStackBuider(){
        return this.builder;
    }

    /**
     * Called to register events that relate to this drop.
     * @param modBusEvent
     * @param forgeEventBus
     */
    public void registerEvents(IEventBus modBusEvent, IEventBus forgeEventBus){}

    /**
     * Called to drop this ItemStack in the world.
     * @param level
     * @param x
     * @param y
     * @param z
     */
    public void dropItem(Level level, double x, double y, double z){
        level.addFreshEntity(new ItemEntity(level, x, y, z, getItemStackBuider().build()));
    }
}