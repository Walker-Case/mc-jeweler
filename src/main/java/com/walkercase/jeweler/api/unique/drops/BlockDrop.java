package com.walkercase.jeweler.api.unique.drops;

import com.walkercase.jeweler.api.unique.ItemStackBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * Drops the ItemStack for the given block.
 */
public class BlockDrop extends UniqueDrop {
    private Block block;

    /**
     * Drops the ItemStack for the given block with chance.
     * @param isBuilder
     * @param block
     * @param chance
     */
    public BlockDrop(ItemStackBuilder isBuilder, Block block, float chance){
        super(isBuilder, chance);
        this.block = block;
    }

    @Override
    public void registerEvents(IEventBus modBusEvent, IEventBus forgeEventBus){
        forgeEventBus.addListener(this::blockBreakEvent);
    }

    public void blockBreakEvent(BlockEvent.BreakEvent event){
        if(!event.getLevel().isClientSide()){
            if(event.getState().getBlock() == block && this.rollCheck()){
                dropItem(event.getPlayer().level(), event.getPlayer().getX(), event.getPlayer().getY(), event.getPlayer().getZ());
            }
        }
    }
}