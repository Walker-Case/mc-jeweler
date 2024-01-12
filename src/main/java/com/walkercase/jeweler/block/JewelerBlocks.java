package com.walkercase.jeweler.block;

import com.walkercase.jeweler.JewelerMain;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JewelerBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, JewelerMain.MODID);

    public static final RegistryObject<GeodeBlock> GEODE_BLOCK = BLOCKS.register("geode_block", () -> new GeodeBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(0.5F, 2.0F).noOcclusion()));
    public static final RegistryObject<GeodeBlock> COPPER_GEODE_BLOCK = BLOCKS.register("copper_geode_block", () -> new GeodeBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.0F, 3.0F).noOcclusion()));

    public static final RegistryObject<GeodeBlock> IRON_GEODE_BLOCK = BLOCKS.register("iron_geode_block", () -> new GeodeBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 4.0F).noOcclusion()));
    public static final RegistryObject<GeodeBlock> GOLD_GEODE_BLOCK = BLOCKS.register("gold_geode_block", () -> new GeodeBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F).noOcclusion()));
    public static final RegistryObject<GeodeBlock> DIAMOND_GEODE_BLOCK = BLOCKS.register("diamond_geode_block", () -> new GeodeBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(2.5F, 6.0F).noOcclusion()));
}
