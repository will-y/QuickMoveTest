package com.example.examplemod;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ComponentItemHandler;

public class ContainerItem extends Item {
    public ContainerItem() {
        super(new Item.Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(
                    new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) -> new ContainerItemMenu(pContainerId, pPlayerInventory, stack),
                            Component.literal("Example Container")),
                    registryFriendlyByteBuf -> {
                        ItemStack.OPTIONAL_STREAM_CODEC.encode(registryFriendlyByteBuf, stack);
                    });
        }

        return InteractionResultHolder.success(stack);
    }

    public static ComponentItemHandler getComponentItemHandler(ItemStack stack) {
        return new ComponentItemHandler(stack, DataComponents.CONTAINER, 27);
    }
}
