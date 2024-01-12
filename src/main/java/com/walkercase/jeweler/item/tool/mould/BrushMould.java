package com.walkercase.jeweler.item.tool.mould;

import com.walkercase.jeweler.JewelerMain;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class BrushMould extends MouldItemBase {
    public BrushMould(Properties props, Item ingotBase, Item meltedIngot, Item crafting) {
        super(props, ingotBase, meltedIngot, crafting);
    }

    @Override
    public void createRecipe(Consumer<FinishedRecipe> writer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, this)
                .pattern("   ")
                .pattern("bbb")
                .pattern("b b")
                .define('b', ingotMelted)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ingotMelted))
                .save(writer, new ResourceLocation(JewelerMain.MODID, ForgeRegistries.ITEMS.getKey(this).getPath()));
    }
}
