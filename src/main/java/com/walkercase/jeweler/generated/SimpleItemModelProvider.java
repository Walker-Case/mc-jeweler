package com.walkercase.jeweler.generated;

import com.walkercase.jeweler.item.JewelerItems;
import com.walkercase.jeweler.item.tool.BrushItemBase;
import com.walkercase.jeweler.item.tool.ChiselItemBase;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SimpleItemModelProvider extends ItemModelProvider {
    public SimpleItemModelProvider(DataGenerator output, String modid, ExistingFileHelper existingFileHelper) {
        super(output.getPackOutput(), modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        JewelerItems.ITEMS.getEntries().stream().forEach(e -> {
            if (e.get() instanceof BrushItemBase || e.get() instanceof ChiselItemBase) {
                ResourceLocation location = e.getKey().location();
                this.basicItem(location);
            }
        });
    }
}
