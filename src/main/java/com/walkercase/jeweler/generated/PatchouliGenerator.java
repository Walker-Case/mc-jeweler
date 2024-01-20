package com.walkercase.jeweler.generated;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.AssetAPI;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class PatchouliGenerator implements DataProvider {

    @VisibleForTesting
    public final ExistingFileHelper existingFileHelper;
    public final PackOutput.PathProvider effectPathProvider;
    public final String modid;
    public final String lang;
    public final JsonObject i18n;

    public PatchouliGenerator(String modid, String lang, PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        this.modid = modid;
        this.lang = lang;
        i18n = AssetAPI.readAsset(new ResourceLocation(modid, "lang/" + lang)).getAsJsonObject();
        this.existingFileHelper = existingFileHelper;
        this.effectPathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "patchouli_books/prospecting/" + lang + "/entries/effects");
    }
    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        List<CompletableFuture<?>> list = new ArrayList<>();

        generateEffectsEntries(output, list);

        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    private void generateEffectsEntries(CachedOutput output, List<CompletableFuture<?>> list){
        list.add(DataProvider.saveStable(output,
                generateEffectPage(EffectAPI.EFFECTS.stream().filter(eff->eff.getEffectType() == IJewelryEffect.EffectType.POSITIVE),
                        "book.jeweler.title.effects.positive", "jeweler:emerald_gem", list),
                effectPathProvider.json(new ResourceLocation(this.modid, "positive"))));
        list.add(DataProvider.saveStable(output,
                generateEffectPage(EffectAPI.EFFECTS.stream().filter(eff->eff.getEffectType() == IJewelryEffect.EffectType.NEUTRAL),
                        "book.jeweler.title.effects.neutral", "jeweler:amethyst_gem", list),
                effectPathProvider.json(new ResourceLocation(this.modid, "neutral"))));
        list.add(DataProvider.saveStable(output,
                generateEffectPage(EffectAPI.EFFECTS.stream().filter(eff->eff.getEffectType() == IJewelryEffect.EffectType.NEGATIVE),
                        "book.jeweler.title.effects.negative", "jeweler:ruby_gem", list),
                effectPathProvider.json(new ResourceLocation(this.modid, "negative"))));
    }

    private JsonObject generateEffectPage(Stream<IJewelryEffect> stream, String i18nTitleKey, String icon, List<CompletableFuture<?>> list){
        JsonObject json = new JsonObject();

        json.addProperty("name", i18n.get(i18nTitleKey).getAsString());
        json.addProperty("icon", icon);
        json.addProperty("category", "jeweler:effects");
        json.addProperty("advancement", "jeweler:cut_gem_advancement");

        JsonArray pages = new JsonArray();

        stream.forEach(iJewelryEffect -> {
            JewelerMain.LOGGER.info("Generating patchouli effect: " + iJewelryEffect.effectID().toString());

            String namespace = iJewelryEffect.effectID().getNamespace();
            String path = iJewelryEffect.effectID().getPath();

            JsonObject pd = new JsonObject();
            pd.addProperty("type", "patchouli:spotlight");
            JsonObject itemIcon = new JsonObject();
            itemIcon.addProperty("item", iJewelryEffect.getEffectRarity().icon);
            pd.add("item", itemIcon);

            StringBuilder sb = new StringBuilder();
            sb.append("$(" + iJewelryEffect.getEffectRarity().chatFormatting.getChar() + ")");
            sb.append(i18n.get("effect." + namespace + "." + path).getAsString());
            sb.append("$(br)");
            sb.append("$()");

            if(iJewelryEffect.getEffectType() == IJewelryEffect.EffectType.NEGATIVE){
                sb.append("$(" + ChatFormatting.RED.getChar() + ")");
                sb.append(i18n.get("effect.lore." + namespace + "." + path).getAsString());
                sb.append("$()");
            }else if(iJewelryEffect.getEffectType() == IJewelryEffect.EffectType.POSITIVE){
                sb.append("$(" + ChatFormatting.GREEN.getChar() + ")");
                sb.append(i18n.get("effect.lore." + namespace + "." + path).getAsString());
                sb.append("$()");
            }else if(iJewelryEffect.getEffectType() == IJewelryEffect.EffectType.NEUTRAL){
                sb.append("$(" + ChatFormatting.AQUA.getChar() + ")");
                sb.append("$(o)");
                sb.append(i18n.get("effect.lore." + namespace + "." + path + ".0").getAsString());
                sb.append("$()");

                sb.append(" ");

                sb.append("$(" + ChatFormatting.RED.getChar() + ")");
                sb.append(i18n.get("literal.but").getAsString());
                sb.append("$()");

                sb.append(" ");

                sb.append("$(" + ChatFormatting.LIGHT_PURPLE.getChar() + ")");
                sb.append(i18n.get("effect.lore." + namespace + "." + path + ".1").getAsString());
                sb.append("$()");
            }

            pd.addProperty("text", sb.toString());

            pages.add(pd);
        });

        json.add("pages", pages);
        return json;
    };


    @Override
    public String getName() {
        return "jeweler-patchouli-generator";
    }
}
