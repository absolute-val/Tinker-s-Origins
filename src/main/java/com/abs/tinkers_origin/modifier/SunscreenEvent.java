package com.abs.tinkers_origin.modifier;

import com.abs.tinkers_origin.utils.Origins;
import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import io.github.edwinmindcraft.apoli.common.registry.ApoliPowers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class SunscreenEvent {
    @SubscribeEvent
    public void onTickEvent(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase == TickEvent.Phase.END) {
            if (Origins.havePower(player, ResourceLocation.fromNamespaceAndPath("origins", "burn_in_daylight"))) {
                if (IPowerContainer.getPowers(player, ApoliPowers.PHASING.get()).isEmpty() && player.tickCount % 20 == 0) {
                    ToolStack stack = ToolStack.from(player.getItemBySlot(EquipmentSlot.HEAD));
                    ToolDamageUtil.damageAnimated(stack, 1, player, EquipmentSlot.HEAD);
                }
            }
        }
    }
}
