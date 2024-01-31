package com.walkercase.jeweler.generated;

import com.google.gson.JsonObject;
import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.AssetAPI;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.LanguageProvider;

public class UniqueLanguageProvider extends LanguageProvider {

    public final JsonObject i18n;
    public UniqueLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid + "_lang", locale);
        i18n = AssetAPI.readAsset(new ResourceLocation(modid, "lang/" + locale)).getAsJsonObject();
    }

    @Override
    protected void addTranslations() {
        JsonObject lang = AssetAPI.readAsset(new ResourceLocation(JewelerMain.MODID, "lang/en_us")).getAsJsonObject();
        EffectAPI.EFFECTS.stream().filter(x->x.getEffectType() == IJewelryEffect.EffectType.NEGATIVE).forEach(e->{
            this.addTranslation(lang,e.effectID().getNamespace() + ".unique." + e.effectID().getPath(),
                    i18n.get("effect.jeweler." + e.effectID().getPath()).getAsString() + " Focus");
        });

        EffectAPI.EFFECTS.stream().filter(x->x.getEffectType() == IJewelryEffect.EffectType.POSITIVE).forEach(e->{
            this.addTranslation(lang,e.effectID().getNamespace() + ".unique." + e.effectID().getPath() + "_0",
                    "Weak " + i18n.get("effect.jeweler." + e.effectID().getPath()).getAsString() + " Focus");
            this.addTranslation(lang, e.effectID().getNamespace() + ".unique." + e.effectID().getPath() + "_1",
                    i18n.get("effect.jeweler." + e.effectID().getPath()).getAsString() + " Focus");
            this.addTranslation(lang, e.effectID().getNamespace() + ".unique." + e.effectID().getPath() + "_2",
                    "Strong " + i18n.get("effect.jeweler." + e.effectID().getPath()).getAsString() + " Focus");

        });
    }

    private void addTranslation(JsonObject lang, String key, String text){
        JewelerMain.LOGGER.info("Adding translation " + key + "=" + text);
        if(!lang.has(key))
            this.add(key, text);
    }
}
