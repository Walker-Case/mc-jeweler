package com.walkercase.jeweler.generated;

import com.google.gson.JsonObject;
import com.walkercase.jeweler.api.AssetAPI;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.LanguageProvider;

public class UniqueLanguageProvider extends LanguageProvider {

    public final JsonObject i18n;
    public UniqueLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
        i18n = AssetAPI.readAsset(new ResourceLocation(modid, "lang/" + locale)).getAsJsonObject();
    }

    @Override
    protected void addTranslations() {
        //RUN ONLY WHEN NEEDED AND COPY ADDITIONS
        /**
         * EffectAPI.EFFECTS.stream().filter(x->x.getEffectType() == IJewelryEffect.EffectType.NEGATIVE).forEach(e->{
         *             this.add(e.effectID().getNamespace() + ".unique." + e.effectID().getPath(),
         *                     i18n.get("effect.jeweler." + e.effectID().getPath()).getAsString() + " Focus");
         *         });
         *
         *         EffectAPI.EFFECTS.stream().filter(x->x.getEffectType() == IJewelryEffect.EffectType.POSITIVE).forEach(e->{
         *             this.add(e.effectID().getNamespace() + ".unique." + e.effectID().getPath() + "_0",
         *                     "Weak " + i18n.get("effect.jeweler." + e.effectID().getPath()).getAsString() + " Focus");
         *             this.add(e.effectID().getNamespace() + ".unique." + e.effectID().getPath() + "_1",
         *                     i18n.get("effect.jeweler." + e.effectID().getPath()).getAsString() + " Focus");
         *             this.add(e.effectID().getNamespace() + ".unique." + e.effectID().getPath() + "_2",
         *                     "Strong " + i18n.get("effect.jeweler." + e.effectID().getPath()).getAsString() + " Focus");
         *
         *         });
         */
    }
}
