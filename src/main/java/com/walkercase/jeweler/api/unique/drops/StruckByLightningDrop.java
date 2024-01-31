package com.walkercase.jeweler.api.unique.drops;

import com.walkercase.jeweler.api.unique.ItemStackBuilder;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * Drops the given ItemStack when a player is struck by lightning.
 */
public class StruckByLightningDrop extends UniqueDrop{

    public StruckByLightningDrop(ItemStackBuilder builder, float chance) {
        super(builder, chance);
    }

    @Override
    public void registerEvents(IEventBus modBusEvent, IEventBus forgeEventBus) {
        forgeEventBus.addListener(this::struck);
    }

    public void struck(EntityStruckByLightningEvent event){
        if(event.getEntity() instanceof Player player && this.rollCheck()){
            if(!player.level().isClientSide()){
                this.dropItem(player.level(), player.getX(), player.getY(), player.getZ());
            }
        }
    }

}
