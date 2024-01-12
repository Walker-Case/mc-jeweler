package com.walkercase.jeweler.generated;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.item.GemItemBase;
import com.walkercase.jeweler.item.JewelerItems;
import com.walkercase.jeweler.item.tool.ChiselItemBase;
import com.walkercase.jeweler.item.tool.mould.MouldItemBase;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Consumer;

public class JewelerItemRecipeProvider extends RecipeProvider {

    public JewelerItemRecipeProvider(DataGenerator p_248933_) {
        super(p_248933_.getPackOutput());
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        genGemRecipes(writer);
        genMouldRecipes(writer);
    }

    private static void genMouldRecipes(Consumer<FinishedRecipe> writer) {
        JewelerItems.ITEMS.getEntries().stream().filter(x -> x.get() instanceof MouldItemBase).forEach(x -> {
            MouldItemBase mould = (MouldItemBase) x.get();

            mould.createRecipe(writer);

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, JewelerItems.FORGE_ITEM.get())
                    .pattern("bbb")
                    .pattern("bab")
                    .pattern("bbb")
                    .define('a', mould)
                    .define('b', mould.ingotMelted)
                    .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(mould, mould.ingotMelted))
                    .save(writer, new ResourceLocation(JewelerMain.MODID, "forge_item_" + Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(mould.crafting)).getPath()));
        });
    }

    private static void genGemRecipes(Consumer<FinishedRecipe> writer) {
        JewelerItems.ITEMS.getEntries().stream().filter(x -> x.get() instanceof GemItemBase).forEach(gem -> {
            JewelerItems.ITEMS.getEntries().stream().filter(y -> y.get() instanceof ChiselItemBase).forEach(chisel -> {
                GemItemBase cutGem = (GemItemBase) gem.get();
                Item rawGem = cutGem.rawGem.get();

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, rawGem)
                        .pattern(" b ")
                        .pattern("bab")
                        .pattern(" b ")
                        .define('a', chisel.get())
                        .define('b', cutGem)
                        .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(chisel.get(), cutGem))
                        .save(writer, new ResourceLocation(JewelerMain.MODID, ForgeRegistries.ITEMS.getResourceKey(rawGem).get().location().getPath() + "_" + chisel.getId().getPath()));

                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, cutGem)
                        .requires(rawGem)
                        .requires(chisel.get())
                        .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(rawGem, chisel.get()))
                        .save(writer, new ResourceLocation(JewelerMain.MODID, gem.getId().getPath() + "_" + chisel.getId().getPath()));
            });
        });
    }
}
