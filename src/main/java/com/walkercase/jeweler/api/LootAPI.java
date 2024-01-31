package com.walkercase.jeweler.api;

import com.google.gson.JsonElement;
import com.walkercase.jeweler.JewelerMain;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

/**
 * Allows for easy vanilla loot modification.
 */
public class LootAPI {

    /**
     * Allows for very simple block break drop tables.
     */
    public static class Block{
        public static final ArrayList<JsonElement> BLOCK_LOOT_MODIIFERS = new ArrayList<JsonElement>();

        static{
            registerBlockLootTableModifier(new ResourceLocation(JewelerMain.MODID, "jeweler_block_modifiers"));
        }

        /**
         * Register the loot modifier table here.
         * @param resourceLocation
         */
        public static synchronized void registerBlockLootTableModifier(ResourceLocation resourceLocation){
            BLOCK_LOOT_MODIIFERS.add(AssetAPI.readLootModifier(resourceLocation));
        }

        /**
         * Returns the block pos as a Vec3.
         * @param pos
         * @return
         */
        public static Vec3 blockPosToVec3(BlockPos pos) {
            return new Vec3(pos.getX(), pos.getY(), pos.getZ());
        }

        /**
         * Rolls for the given block and drops where it is.
         * @param player
         * @param pos
         * @param state
         * @param lootTable
         */
        public static void drop(Player player, BlockPos pos, BlockState state, ResourceLocation lootTable) {
            getDrops(player, pos, state, lootTable).forEach(drop -> {
                player.level().addFreshEntity(new ItemEntity(player.level(), pos.getX(), pos.getY(), pos.getZ(), drop));
            });
        }

        /**
         * Rolls and returns block drops.
         * @param player
         * @param pos
         * @param state
         * @param lootTable
         * @return
         */
        public static ObjectArrayList<ItemStack> getDrops(Player player, BlockPos pos, BlockState state, ResourceLocation lootTable) {
            return getDrops(player, blockPosToVec3(pos), state, lootTable);
        }

        /**
         * Rolls and returns block drops.
         * @param player
         * @param state
         * @param lootTable
         * @return
         */
        public static ObjectArrayList<ItemStack> getDrops(Player player, Vec3 origin, BlockState state, ResourceLocation lootTable) {
            ServerLevel serverlevel = (ServerLevel)player.level();
            LootTable loottable = serverlevel.getServer().getLootData().getLootTable(BuiltInLootTables.SNIFFER_DIGGING);
            LootParams lootparams = createBlockLootContext(player, origin, state);

            return new ObjectArrayList<>(loottable.getRandomItems(lootparams));
        }

        private static LootParams createBlockLootContext(Player player, Vec3 origin, BlockState state) {
            Level level = player.level();

            return (new LootParams.Builder((ServerLevel) level))
                    .withLuck(player.getLuck())
                    .withParameter(LootContextParams.ORIGIN, origin)
                    .withParameter(LootContextParams.BLOCK_STATE, state)
                    .withParameter(LootContextParams.TOOL, player.getMainHandItem())
                    .withParameter(LootContextParams.THIS_ENTITY, player)
                    .create(LootContextParamSets.BLOCK);
        }
    }


    /**
     * Allows for very simple entity death drop tables.
     */
    public static class Entity {

        public static final ArrayList<JsonElement> ENTITY_LOOT_MODIFIERS = new ArrayList<JsonElement>();

        static{
            registerEntityLootTableModifier(new ResourceLocation(JewelerMain.MODID, "jeweler_entity_modifiers"));
        }

        /**
         * Register the loot modifier table here.
         * @param resourceLocation
         */
        public static synchronized void registerEntityLootTableModifier(ResourceLocation resourceLocation){
            ENTITY_LOOT_MODIFIERS.add(AssetAPI.readLootModifier(resourceLocation));
        }

        /**
         * Do a table drops roll for the given living entity.
         * @param living
         * @param lootTable
         */
        public static void dropLiving(LivingEntity living, ResourceLocation lootTable) {
            getLivingDrops(living, lootTable).forEach(drop -> {
                living.level().addFreshEntity(new ItemEntity(living.level(), living.getX(), living.getY(), living.getZ(), drop));
            });
        }

        /**
         * Rolls and returns living entity drops.
         * @param living
         * @param lootTable
         * @return
         */
        public static ObjectArrayList<ItemStack> getLivingDrops(LivingEntity living, ResourceLocation lootTable) {
            ServerLevel serverlevel = (ServerLevel)living.level();
            LootTable loottable = serverlevel.getServer().getLootData().getLootTable(BuiltInLootTables.SNIFFER_DIGGING);
            LootParams lootparams = createLootParams(living);

            return new ObjectArrayList<>(loottable.getRandomItems(lootparams));
        }

        private static LootParams createLootParams(LivingEntity living) {
            Level level = living.level();

            return (new LootParams.Builder((ServerLevel) level))
                    .withParameter(LootContextParams.TOOL, living.getMainHandItem())
                    .withParameter(LootContextParams.THIS_ENTITY, living)
                    .create(LootContextParamSets.ENTITY);
        }
    }
}
