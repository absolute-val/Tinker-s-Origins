package com.abs.tinkers_origin.mixin;

import com.abs.tinkers_origin.utils.Origins;
import io.github.edwinmindcraft.apoli.common.condition.entity.SimpleEntityCondition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.Objects;

@Mixin(value = SimpleEntityCondition.class, remap = false)
public class MixinExposeToSun {
    @Inject(
            method = "isExposedToSun(Lnet/minecraft/world/entity/Entity;)Z",
            at = @At(value = "RETURN"),
            cancellable = true
    )
    private static void onCheckExposedToSun(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entity;
            ToolStack stack = ToolStack.from(living.getItemBySlot(EquipmentSlot.HEAD));
            if (stack.getModifier(Objects.requireNonNull(ModifierId.tryBuild("tinkers_origin", "sunscreen"))) != ModifierEntry.EMPTY && !stack.isBroken()) {
                if (Origins.havePower((Player) living, ResourceLocation.fromNamespaceAndPath("origins", "burn_in_daylight"))) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
