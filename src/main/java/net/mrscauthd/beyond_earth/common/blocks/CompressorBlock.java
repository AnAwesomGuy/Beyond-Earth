package net.mrscauthd.beyond_earth.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.mrscauthd.beyond_earth.common.blocks.entities.CompressorBlockEntity;

public class CompressorBlock extends AbstractMachineBlock<CompressorBlockEntity> {

    public CompressorBlock(BlockBehaviour.Properties properties) {
            super(properties);
    }

    @Override
    protected boolean useLit() {
            return true;
    }

    @Override
    protected boolean useFacing() {
            return true;
    }

    @Override
    public CompressorBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new CompressorBlockEntity(pos, state);
    }
}
