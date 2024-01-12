package com.walkercase.jeweler.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class GeodeBlock extends Block {

    public static final IntegerProperty TEXTURE_INDEX = IntegerProperty.create("jeweler_texture_index", 0, 3);

    public GeodeBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.getStateDefinition().any().setValue(TEXTURE_INDEX, 0));
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public void createBlockStateDefinition(StateDefinition.Builder builder) {
        builder.add(TEXTURE_INDEX);
    }

}
