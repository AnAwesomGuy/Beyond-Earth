package com.st0x0ef.beyond_earth.common.blocks.machines;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.st0x0ef.beyond_earth.common.blocks.entities.machines.WaterPumpBlockEntity;
import com.st0x0ef.beyond_earth.common.blocks.entities.machines.gauge.GaugeTextHelper;
import com.st0x0ef.beyond_earth.common.blocks.entities.machines.gauge.GaugeValueHelper;
import com.st0x0ef.beyond_earth.common.config.Config;

public class WaterPump extends AbstractMachineBlock<WaterPumpBlockEntity> {

    public static VoxelShape SOUTH_SHAPE = Shapes.empty();
    public static VoxelShape NORTH_SHAPE = Shapes.empty();
    public static VoxelShape EAST_SHAPE = Shapes.empty();
    public static VoxelShape WEST_SHAPE = Shapes.empty();

    public WaterPump(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean useFacing() {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            default -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
        };
    }

    static {
        NORTH_SHAPE = Shapes.join(NORTH_SHAPE, Shapes.box(0.34375, 0, 0.34375, 0.65625, 0.0625, 0.65625), BooleanOp.OR);
        NORTH_SHAPE = Shapes.join(NORTH_SHAPE, Shapes.box(0.343749, 0.34375, 0, 0.65625, 0.65625, 0.0625),
                BooleanOp.OR);
        NORTH_SHAPE = Shapes.join(NORTH_SHAPE, Shapes.box(0.375, 0.375, 0.0625, 0.625, 0.625, 0.375), BooleanOp.OR);
        NORTH_SHAPE = Shapes.join(NORTH_SHAPE, Shapes.box(0.34375, 0.8125, 0.34375, 0.65625, 1, 0.65625), BooleanOp.OR);
        NORTH_SHAPE = Shapes.join(NORTH_SHAPE, Shapes.box(0.375, 0.0625, 0.375, 0.625, 0.8125, 0.625), BooleanOp.OR);

        SOUTH_SHAPE = Shapes.join(SOUTH_SHAPE, Shapes.box(0.34375, 0, 0.34375, 0.65625, 0.0625, 0.65625), BooleanOp.OR);
        SOUTH_SHAPE = Shapes.join(SOUTH_SHAPE, Shapes.box(0.343749, 0.34375, 0.9375, 0.65625, 0.65625, 1),
                BooleanOp.OR);
        SOUTH_SHAPE = Shapes.join(SOUTH_SHAPE, Shapes.box(0.375, 0.375, 0.625, 0.625, 0.625, 0.9375), BooleanOp.OR);
        SOUTH_SHAPE = Shapes.join(SOUTH_SHAPE, Shapes.box(0.34375, 0.8125, 0.34375, 0.65625, 1, 0.65625), BooleanOp.OR);
        SOUTH_SHAPE = Shapes.join(SOUTH_SHAPE, Shapes.box(0.375, 0.0625, 0.375, 0.625, 0.8125, 0.625), BooleanOp.OR);

        EAST_SHAPE = Shapes.join(EAST_SHAPE, Shapes.box(0.34375, 0, 0.34375, 0.65625, 0.0625, 0.65625), BooleanOp.OR);
        EAST_SHAPE = Shapes.join(EAST_SHAPE, Shapes.box(0.9375, 0.34375, 0.343749, 1, 0.65625, 0.65625), BooleanOp.OR);
        EAST_SHAPE = Shapes.join(EAST_SHAPE, Shapes.box(0.625, 0.375, 0.375, 0.9375, 0.625, 0.625), BooleanOp.OR);
        EAST_SHAPE = Shapes.join(EAST_SHAPE, Shapes.box(0.34375, 0.8125, 0.34375, 0.65625, 1, 0.65625), BooleanOp.OR);
        EAST_SHAPE = Shapes.join(EAST_SHAPE, Shapes.box(0.375, 0.0625, 0.375, 0.625, 0.8125, 0.625), BooleanOp.OR);

        WEST_SHAPE = Shapes.join(WEST_SHAPE, Shapes.box(0.34375, 0, 0.34375, 0.65625, 0.0625, 0.65625), BooleanOp.OR);
        WEST_SHAPE = Shapes.join(WEST_SHAPE, Shapes.box(0, 0.34375, 0.343749, 0.0625, 0.65625, 0.65625), BooleanOp.OR);
        WEST_SHAPE = Shapes.join(WEST_SHAPE, Shapes.box(0.0625, 0.375, 0.375, 0.375, 0.625, 0.625), BooleanOp.OR);
        WEST_SHAPE = Shapes.join(WEST_SHAPE, Shapes.box(0.34375, 0.8125, 0.34375, 0.65625, 1, 0.65625), BooleanOp.OR);
        WEST_SHAPE = Shapes.join(WEST_SHAPE, Shapes.box(0.375, 0.0625, 0.375, 0.625, 0.8125, 0.625), BooleanOp.OR);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = this.defaultBlockState();

        if (this.useFacing()) {
            return state.setValue(FACING, context.getHorizontalDirection());
        } else {
            return state;
        }
    }

    @Override
    public void appendHoverText(ItemStack itemstack, BlockGetter level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, level, list, flag);
        list.add(GaugeTextHelper.buildBlockTooltip(GaugeTextHelper
                .getTransferPerTickText(GaugeValueHelper.getFluid(Config.WATER_PUMP_TANK_TRANSFER.get()))));
    }

    @Override
    public WaterPumpBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WaterPumpBlockEntity(pos, state);
    }

    @Override
    public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, Mob entity) {
        return BlockPathTypes.BLOCKED;
    }
}
