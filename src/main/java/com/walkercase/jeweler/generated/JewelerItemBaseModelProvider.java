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

                JewelerMain.LOGGER.info("Generating item model for: " + location);

                ItemModelBuilder jeweleryBuilder =
                        getBuilder(new ResourceLocation(location.getNamespace(), location.getPath()).toString())
                                .parent(new ModelFile.UncheckedModelFile(item.parentModel));
                jeweleryBuilder.texture("0", new ResourceLocation(JewelerMain.MODID, "item/" + location.getPath()));

                JewelerItems.ITEMS.getEntries().stream().filter(g -> g.get() instanceof GemItemBase).forEach(g -> {
                    GemItemBase gem = (GemItemBase) g.get();
                    ResourceLocation gemModelFile = new ResourceLocation(location.getNamespace(), location.getPath() + "_" + g.getKey().location().getPath());
                    ResourceLocation gemParentModel = new ResourceLocation(item.parentModel.getNamespace(), item.parentModel.getPath() + "_" + g.getKey().location().getPath());

                    JewelerMain.LOGGER.info("Generating gem model for: " + gemModelFile);

                    ItemModelBuilder builderEmerald =
                            getBuilder(gemModelFile.toString())
                                    .parent(new ModelFile.UncheckedModelFile(gemParentModel));
                    builderEmerald.texture("0", new ResourceLocation(JewelerMain.MODID, "item/" + location.getPath()));
                    builderEmerald.texture("1", gemParentModel);
                    jeweleryBuilder.override().model(builderEmerald).predicate(new ResourceLocation(JewelerMain.MODID, "index"), gem.index);
                });


            }
        });
    }
}
