package com.example.examplemod;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

/**
 * Slot class that can be used with immutable itemstack storages (Ex: {@link ComponentItemHandler}
 */
public class SlotItemHandlerImmutable extends SlotItemHandler {
    // Vanilla MC code modifies the stack returned by `getItem()` directly, but it
    // calls `setChanged()` when that happens, so we just cache the returned stack,
    // and set it when `setChanged()` is called.
    @Nullable
    private ItemStack cachedReturnedStack = null;
    private final int index;

    public SlotItemHandlerImmutable(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.index =index;
    }

    /**
     * Perform the real get, by default has the same behavior as {@link SlotItemHandler}
     * <p>
     * Override this method to do get logic instead of {@link SlotItemHandlerImmutable#getItem()}
     * @return The stack that is in this slot
     */
    protected ItemStack getRealStack() {
        return super.getItem();
    }

    /**
     * Perform the real set, by default has the same behavior as {@link SlotItemHandler}
     * <p>
     * Override this method to do set logic instead of {@link SlotItemHandlerImmutable#set(ItemStack)}
     *
     * @param stack The stack to put in this slot
     */
    protected void setRealStack(ItemStack stack) {
        ((IItemHandlerModifiable) this.getItemHandler()).setStackInSlot(index, stack);
    }

    @Override
    public final ItemStack getItem() {
        return cachedReturnedStack = getRealStack();
    }

    @Override
    public final void set(ItemStack stack) {
        cachedReturnedStack = stack;
        setRealStack(stack);
    }

    @Override
    public final void setChanged() {
        if (cachedReturnedStack != null) {
            set(cachedReturnedStack);
        }
    }

    @Override
    public final ItemStack remove(int amount) {
        ItemStack stack = getItem().copy();
        ItemStack ret = stack.split(amount);
        set(stack);
        cachedReturnedStack = null;
        return ret;
    }
}
