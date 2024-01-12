package com.walkercase.jeweler.event;

import com.google.gson.JsonArray;
import com.walkercase.jeweler.api.LootAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class EntityHelper {
    public static void entityDied(LivingDeathEvent livingDeathEvent){
        if(!livingDeathEvent.getEntity().getLevel().isClientSide){
            LootAPI.Entity.ENTITY_LOOT_MODIFIERS.forEach(elm->{
                JsonArray entries = elm.getAsJsonObject().getAsJsonArray("entries");
                entries.forEach(entry->{
                    if(entry.getAsJsonObject().has("entityId")){
                        String entityKey = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(livingDeathEvent.getEntity().getType())).toString();
                        if(entry.getAsJsonObject().get("entityId").getAsString().equals(entityKey)){
                            LootAPI.Entity.dropLiving(livingDeathEvent.getEntity(), new ResourceLocation(entry.getAsJsonObject().get("lootTable").getAsString()));
                        }
                    }
                });
            });
        }
    }
}
