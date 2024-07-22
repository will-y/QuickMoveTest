package com.example.examplemod;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.SlotItemHandler;

/**
 * Slot class that can be used with immutable {@link IItemHandler}s
 * like {@link ComponentItemHandler}
 */
public class SlotItemHandlerImmutable extends StackCopySlot {
    private final SlotItemHandler slotItemHandler;

    public SlotItemHandlerImmutable(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(xPosition, yPosition);
        slotItemHandler = new SlotItemHandler(itemHandler, index, xPosition, yPosition);
    }

    public SlotItemHandlerImmutable(SlotItemHandler slotItemHandler) {
        super(slotItemHandler.x, slotItemHandler.y);
        this.slotItemHandler = slotItemHandler;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return slotItemHandler.mayPlace(stack);
    }

    @Override
    protected ItemStack getStackCopy() {
        return slotItemHandler.getItem();
    }

    @Override
    protected void setStackCopy(ItemStack stack) {
        ((IItemHandlerModifiable) slotItemHandler.getItemHandler()).setStackInSlot(index, stack);
    }

    @Override
    public void onQuickCraft(ItemStack oldStackIn, ItemStack newStackIn) {
        slotItemHandler.onQuickCraft(oldStackIn, newStackIn);
    }

    @Override
    public int getMaxStackSize() {
        return slotItemHandler.getMaxStackSize();
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return slotItemHandler.getMaxStackSize(stack);
    }

    @Override
    public boolean mayPickup(Player playerIn) {
        return slotItemHandler.mayPickup(playerIn);
    }

    @Override
    public boolean isSameInventory(Slot other) {
        return slotItemHandler.isSameInventory(other);
    }

    public IItemHandler getItemHandler() {
        return slotItemHandler.getItemHandler();
    }
}