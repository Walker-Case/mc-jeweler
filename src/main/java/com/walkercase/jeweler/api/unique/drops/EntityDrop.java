package com.walkercase.jeweler.api.unique.drops;

import com.walkercase.jeweler.api.unique.ItemStackBuilder;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

/**
 * Has a chance to drop the item when an entity is killed by a player.
 */
public class EntityDrop extends UniqueDrop{
    private String entityKey;

    /**
     * Create a new Entity unique drop for the given entity and chance.
     * @param builder
     * @param entity
     * @param chance
     */
    public EntityDrop(ItemStackBuilder builder, String entity, float chance){
        super(builder, chance);
        this.entityKey = entity;
    }
    public void entityDied(LivingDeathEvent livingDeathEvent){
        if(!livingDeathEvent.getEntity().level().isClientSide()){
            String entityKey = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(livingDeathEvent.getEntity().getType())).toString();
            if(entityKey.equals(this.entityKey) && this.rollCheck()){
                Level level = livingDeathEvent.getEntity().level();
                dropItem(level, livingDeathEvent.getEntity().getX(), livingDeathEvent.getEntity().getY(), livingDeathEvent.getEntity().getZ());
            }
        }
    }

    @Override
    public void registerEvents(IEventBus modBusEvent, IEventBus forgeEventBus){
        forgeEventBus.addListener(this::entityDied);
    }
}
