package com.walkercase.jeweler.generated;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.item.GemItemBase;
import com.walkercase.jeweler.item.JewelerItems;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class JewelerItemBaseModelProvider extends ItemModelProvider {
    public JewelerItemBaseModelProvider(DataGenerator output, String modid, ExistingFileHelper existingFileHelper) {
        super(output.getPackOutput(), modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        JewelerItems.registerSimpleItemModels(this);
        registerJeweleryItemModels();
    }

    private void registerJeweleryItemModels() {
        JewelerItems.ITEMS.getEntries().stream().filter(e -> e.get() instanceof JewelerItemBase).forEach(e -> {
            if (e.get() instanceof JewelerItemBase item) {
                ResourceLocation location = e.getKey().location();
                ItemModelBuilder builder = this.basicItem(location);

                JewelerItems.ITEMS.getEntries().stream().filter(g -> g.get() instanceof GemItemBase).forEach(g -> {
                    GemItemBase gem = (GemItemBase) g.get();

                    ItemModelBuilder builderEmerald =
                            getBuilder(new ResourceLocation(location.getNamespace(), location.getPath() + "_" + g.getKey().location().getPath()).toString())
                                    .parent(new ModelFile.UncheckedModelFile("item/generated"));
                    builderEmerald.texture("layer0", new ResourceLocation(JewelerMain.MODID, "item/" + location.getPath()));
                    builderEmerald.texture("layer1", new ResourceLocation(JewelerMain.MODID, "item/" + g.getId().getPath() + "_" + item.texturePosition.id));
                    builder.override().model(builderEmerald).predicate(new ResourceLocation(JewelerMain.MODID, "index"), gem.index);
                });


            }
        });
    }
}
