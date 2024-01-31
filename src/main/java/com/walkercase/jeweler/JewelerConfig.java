package com.walkercase.jeweler;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class JewelerConfig {

    static {
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }

    public static final ForgeConfigSpec commonSpec;
    public static final JewelerConfig.Common COMMON;

    public static class Common {
        public final ForgeConfigSpec.ConfigValue<String> serverLocale;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("Common");
            {
                this.serverLocale = builder.comment("Lang file to use for items.").define("serverLocale", "jeweler:en_us");
            }
            builder.pop();
        }
    }
}
