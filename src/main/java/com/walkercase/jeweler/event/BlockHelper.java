package com.walkercase.jeweler.event;

import com.google.gson.JsonArray;
import com.walkercase.jeweler.api.LootAPI;
import com.walkercase.jeweler.block.GeodeBlock;
import com.walkercase.jeweler.effect.IJewelryEffect;
import com.walkercase.jeweler.item.tool.BrushItemBase;
import com.walkercase.jeweler.item.tool.ChiselItemBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import static com.walkercase.jeweler.api.LootAPI.Block.drop;

public class BlockHelper {

    /**
     * Contains methods to fire loot events.
     */
    public static class LootHelper{
        public static void blockBreakEvent(BlockEvent.BreakEvent event){
            if(!event.getLevel().isClientSide()){
                LootAPI.Block.BLOCK_LOOT_MODIIFERS.forEach(elm->{
                    JsonArray entries = elm.getAsJsonObject().getAsJsonArray("entries");
                    entries.forEach(entry->{
                        if(entry.getAsJsonObject().has("blockId")){
                            String blockKey = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(event.getState().getBlock())).toString();
                            if(entry.getAsJsonObject().get("blockId").getAsString().equals(blockKey)){
                                LootAPI.Block.drop(event.getPlayer(), event.getPos(), event.getState(),
                                        new ResourceLocation(entry.getAsJsonObject().get("lootTable").getAsString()));
                            }
                        }
                    });
                });
            }
        }
    }

    /**
     * Contains methods regarding the Geode blocks.
     */
    public static class GeodeHelper {

        @SubscribeEvent
        public static void blockBreakEvent(BlockEvent.BreakEvent e) {
            if (!e.getLevel().isClientSide()) {
                if (e.getState().getBlock() instanceof GeodeBlock geode) {
                    ResourceLocation blockKey = ForgeRegistries.BLOCKS.getKey(e.getState().getBlock());

                    int currentValue = e.getState().getValue(GeodeBlock.TEXTURE_INDEX);
                    ItemStack mainHand = e.getPlayer().getMainHandItem();

                    if (currentValue == 0) {
                        if (mainHand.getItem() instanceof PickaxeItem) {
                            e.getLevel().setBlock(e.getPos(), geode.defaultBlockState().setValue(GeodeBlock.TEXTURE_INDEX, currentValue + 1), 3);
                            drop(e.getPlayer(), e.getPos(), e.getState(), new ResourceLocation(blockKey.getNamespace(), "geode/" + blockKey.getPath() + "_0"));
                            IJewelryEffect.damageStack(e.getPlayer(), mainHand, IJewelryEffect.RANDOM, 1);
                            e.setCanceled(true);
                        }
                    } else if (currentValue == 1) {
                        if (mainHand.getItem() instanceof ShovelItem) {
                            IJewelryEffect.damageStack(e.getPlayer(), mainHand, IJewelryEffect.RANDOM, 1);
                            drop(e.getPlayer(), e.getPos(), e.getState(), new ResourceLocation(blockKey.getNamespace(), "geode/" + blockKey.getPath() + "_1"));
                            e.getLevel().setBlock(e.getPos(), geode.defaultBlockState().setValue(GeodeBlock.TEXTURE_INDEX, currentValue + 1), 3);
                            e.setCanceled(true);
                        }
                    } else if (currentValue == 2) {
                        if (mainHand.getItem() instanceof ChiselItemBase) {
                            IJewelryEffect.damageStack(e.getPlayer(), mainHand, IJewelryEffect.RANDOM, 1);
                            drop(e.getPlayer(), e.getPos(), e.getState(), new ResourceLocation(blockKey.getNamespace(), "geode/" + blockKey.getPath() + "_2"));
                            e.getLevel().setBlock(e.getPos(), geode.defaultBlockState().setValue(GeodeBlock.TEXTURE_INDEX, currentValue + 1), 3);
                            e.setCanceled(true);
                        }
                    } else if (currentValue == 3) {
                        if (mainHand.getItem() instanceof BrushItemBase) {
                            e.setExpToDrop(e.getExpToDrop() + 5);
                            drop(e.getPlayer(), e.getPos(), e.getState(), new ResourceLocation(blockKey.getNamespace(), "geode/" + blockKey.getPath() + "_3"));
                            IJewelryEffect.damageStack(e.getPlayer(), mainHand, IJewelryEffect.RANDOM, 1);
                        }
                    }

                }
            }

        }


    }
}
