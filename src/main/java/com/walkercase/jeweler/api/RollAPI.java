package com.walkercase.jeweler.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.record.Roll;
import com.walkercase.jeweler.api.record.RollData;
import com.walkercase.jeweler.api.record.RollType;
import com.walkercase.jeweler.effect.IJewelryEffect;
import com.walkercase.jeweler.item.JewelerItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import oshi.util.tuples.Pair;

import java.util.*;

import static com.walkercase.jeweler.api.AssetAPI.readLootTable;

public class RollAPI {

    private static Random RANDOM = new Random();

    public static final RollType POSITIVE = new RollType("POSITIVE");
    public static final RollType NEUTRAL = new RollType("NEUTRAL");
    public static final RollType NEGATIVE = new RollType("NEGATIVE");

    private static final ArrayList<RollType> ROLL_TYPES = new ArrayList<>();
    private static final HashMap<Item, ArrayList<RollData>> CUT_EFFECT_ROLLS = new HashMap<>();

    public static RollData[] readRollData(ResourceLocation location){
        JsonObject obj = readLootTable(location).getAsJsonObject();
        HashMap<RollType, ArrayList<Roll>> map = new HashMap<>();
        if(obj != null){
            JsonObject entries = obj.getAsJsonObject("entries");
            entries.keySet().forEach(key->{
                RollType rollType = RollAPI.getRollType(key);
                if(!map.containsKey(rollType))
                    map.put(rollType, new ArrayList<>());

                JsonArray arr = entries.get(key).getAsJsonArray();

                if(!arr.isEmpty()) {
                    arr.forEach(y->{
                    JsonObject obj1 = y.getAsJsonObject();
                    Optional<IJewelryEffect> effect = EffectAPI.EFFECTS.stream().filter(x -> x.effectID().toString().equals(obj1.get("id").getAsString())).findAny();
                    int maxValue = obj1.get("maxLevel").getAsInt();
                    float chance = obj1.get("chance").getAsFloat();
                    if (effect.isPresent()) {
                        Roll roll = new Roll(chance, maxValue, effect.get());
                        map.get(rollType).add(roll);
                    }
                    });
                }
            });
            ArrayList<RollData> rollData = new ArrayList<>();
            map.forEach((key, value) -> rollData.add(new RollData(key, value.toArray(new Roll[0]))));
            return rollData.toArray(new RollData[0]);
        }
        return null;
    }

    public static synchronized void registerCutEffectRoll(Item item, RollData... rollData){
        JewelerMain.LOGGER.info("Registering cut effect rolls for: " + item);
        if(!CUT_EFFECT_ROLLS.containsKey(item)) {
            ArrayList<RollData> data = new ArrayList<>(List.of(rollData));
            CUT_EFFECT_ROLLS.put(item, data);
        }else{
            CUT_EFFECT_ROLLS.get(item).addAll(List.of(rollData));
        }
    }

    public static Pair<IJewelryEffect, Integer>[] rollFor(Item item, RollType type){
        RollData[] data = getRollData(item);
        ArrayList<Pair<IJewelryEffect, Integer>> list = new ArrayList<>();
        for(RollData d : data){
            if(d.rollType() == type){
                for(Roll r : d.rolls()){
                    if(RANDOM.nextFloat() < r.chance()){
                        list.add(new Pair<>(r.roll(), RANDOM.nextInt(r.maxLevel())));
                    }
                }
            }
        }

        return list.toArray(new Pair[0]);
    }

    public static RollData[] getRollData(Item item){
        if(CUT_EFFECT_ROLLS.containsKey(item)) {
            return CUT_EFFECT_ROLLS.get(item).toArray(new RollData[0]);
        }
        return null;
    }

    public static synchronized void registerRollType(RollType rollType){
        JewelerMain.LOGGER.info("Registering roll type: " + rollType.name());
        if(ROLL_TYPES.stream().anyMatch(x->x.name().equals(rollType.name())))
            throw new ArrayStoreException("Roll type already exists!");
        ROLL_TYPES.add(rollType);
    }

    public static RollType getRollType(String name){
        Optional<RollType> opt = ROLL_TYPES.stream().filter(x->x.name().equals(name)).findFirst();
        return opt.orElse(null);
    }
}
