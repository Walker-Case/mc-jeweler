package com.walkercase.jeweler.item.tool.mould;

import com.walkercase.jeweler.item.tool.JewelryTool;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;

/**
 * Used to create a new item mould.
 */
public class MouldItemBase extends JewelryTool {
    /**
     * The item this mould is used to create.
     */
    public final Item crafting;

    /**
     * The base item used to make this mould.
     * Currently unused. Will be removed in future versions.
     */
    @Deprecated
    public final Item ingotBase;

    /**
     * The item used to create this mould and repair created items.
     */
    public final Item ingotMelted;

    /**
     * Create a new mould!
     * @param props Item properties. Requires durability.
     * @param ingotBase The base
     * @param ingotMelted
     * @param crafting The item this mould is used to create.
     */
    public MouldItemBase(Properties props, Item ingotBase, Item ingotMelted, Item crafting) {
        super(props);
        this.crafting = crafting;
        this.ingotBase = ingotBase;
        this.ingotMelted = ingotMelted;
    }

    /**
     * Called by the data generator to create this moulds recipe.
     * @param writer
     */
    public void createRecipe(Consumer<FinishedRecipe> writer) {
    }
}
