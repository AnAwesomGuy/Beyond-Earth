package com.st0x0ef.beyond_earth.common.world.trees;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import com.st0x0ef.beyond_earth.common.world.ModConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class GlacioTreeGrower  extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
        return ModConfiguredFeature.GLACIO_TREE_KEY;
    }
}