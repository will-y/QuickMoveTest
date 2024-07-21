package com.example.examplemod;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ContainerItemMenu extends AbstractContainerMenu {
    private final ComponentItemHandler inventory;

    // Client constructor
    public ContainerItemMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf data) {
        this(containerId, playerInventory, ItemStack.OPTIONAL_STREAM_CODEC.decode(data));
    }

    // Server constructor
    public ContainerItemMenu(int containerId, Inventory playerInventory, ItemStack stack) {
        super(ExampleMod.EXAMPLE_CONTAINER_MENU.get(), containerId);

        this.inventory = ContainerItem.getComponentItemHandler(stack);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new SlotItemHandlerImmutable(inventory, i * 9 + j, 8 + j * 18, 18 + i * 18));
            }
        }

        setPlayerInventory(playerInventory);
    }

    // Copied from chest
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex < 27) {
                if (!this.moveItemStackTo(itemstack1, 27, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 27, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    private void setPlayerInventory(Container playerInventory) {
        // Inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                int index = j + i * 9 + 9;
                this.addSlot(new Slot(playerInventory, index, 8 + j * 18, i * 18 + 85));
            }
        }

        // Hotbar
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 143));
        }
    }
}
