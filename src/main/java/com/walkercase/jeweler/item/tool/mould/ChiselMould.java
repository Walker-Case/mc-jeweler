package com.walkercase.jeweler.item.tool.mould;

import com.walkercase.jeweler.JewelerMain;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class ChiselMould extends MouldItemBase {
    public ChiselMould(Properties props, Item ingotBase, Item ingotMelted, Item crafting) {
        super(props, ingotBase, ingotMelted, crafting);
    }

    @Override
    public void createRecipe(Consumer<FinishedRecipe> writer) {
        ShapedRecipeBuilder.shaped(this)
                .pattern(" b ")
                .pattern(" b ")
                .pattern(" b ")
                .define('b', ingotMelted)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ingotMelted))
                .save(writer, new ResourceLocation(JewelerMain.MODID, ForgeRegistries.ITEMS.getKey(this).getPath()));
    }
}
