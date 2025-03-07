package com.st0x0ef.beyond_earth.common.menus.planetselection;

import com.st0x0ef.beyond_earth.common.registries.ContainerRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.IContainerFactory;

public class ErrorMenu {

    public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
        @Override
        public GuiContainer create(int id, Inventory inv, FriendlyByteBuf extraData) {
            return new GuiContainer(id, inv, extraData);
        }
    }

    public static class GuiContainer extends AbstractContainerMenu {
        private final Player player;

        public GuiContainer(int id, Inventory inv, FriendlyByteBuf extraData) {
            super(ContainerRegistry.ERROR_GUI.get(), id);
            this.player = inv.player;
        }

        public ItemStack quickMoveStack(Player player, int p_219988_) {
            return ItemStack.EMPTY;
        }

        @Override
        public boolean stillValid(Player player) {
            return !player.isDeadOrDying();
        }

        public Player getPlayer() {
            return this.player;
        }

    }
}
