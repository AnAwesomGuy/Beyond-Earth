package com.st0x0ef.beyond_earth.common.menus;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.network.IContainerFactory;
import com.st0x0ef.beyond_earth.common.blocks.entities.machines.OxygenDistributorBlockEntity;
import com.st0x0ef.beyond_earth.common.blocks.entities.machines.OxygenMakingBlockEntity;
import com.st0x0ef.beyond_earth.common.menus.helper.MenuHelper;
import com.st0x0ef.beyond_earth.common.registries.ContainerRegistry;

public class OxygenBubbleDistributorMenu {

    public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
        public GuiContainer create(int id, Inventory inv, FriendlyByteBuf extraData) {
            BlockPos pos = extraData.readBlockPos();
            OxygenDistributorBlockEntity blockEntity = (OxygenDistributorBlockEntity) inv.player.level()
                    .getBlockEntity(pos);
            return new GuiContainer(id, inv, blockEntity);
        }
    }

    public static class GuiContainer extends AbstractContainerMenu {
        private final OxygenDistributorBlockEntity blockEntity;

        public GuiContainer(int id, Inventory inv, OxygenDistributorBlockEntity blockEntity) {
            super(ContainerRegistry.OXYGEN_BUBBLE_DISTRIBUTOR_GUI.get(), id);
            this.blockEntity = blockEntity;

            IItemHandlerModifiable itemHandler = blockEntity.getItemHandler();
            this.addSlot(new SlotItemHandler(itemHandler, OxygenMakingBlockEntity.SLOT_INPUT_SOURCE, 17, 58));
            this.addSlot(new SlotItemHandler(itemHandler, OxygenMakingBlockEntity.SLOT_INPUT_SINK, 17, 88));

            MenuHelper.createInventorySlots(inv, this::addSlot, 8, 138);
        }

        public OxygenDistributorBlockEntity getBlockEntity() {
            return this.blockEntity;
        }

        @Override
        public boolean stillValid(Player player) {
            return !this.getBlockEntity().isRemoved();
        }

        @Override
        public ItemStack quickMoveStack(Player playerIn, int index) {
            return MenuHelper.transferStackInSlot(this, playerIn, index, this.getBlockEntity(), this::moveItemStackTo);
        }
    }
}
