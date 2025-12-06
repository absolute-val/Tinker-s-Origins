package com.abs.tinkers_origin.mixin;

import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.apace100.origins.Origins;
import io.github.apace100.origins.enchantment.WaterProtectionEnchantment;
import io.github.apace100.origins.registry.ModEnchantments;
import io.github.edwinmindcraft.apoli.common.power.DamageOverTimePower;
import io.github.edwinmindcraft.apoli.common.power.configuration.DamageOverTimeConfiguration;
import io.github.edwinmindcraft.calio.api.network.CalioCodecHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;


import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

@Mixin(value = DamageOverTimePower.class,remap = false)
public abstract class MixinDamageOverTime {
    @Inject(
            method = "getProtection(Lio/github/edwinmindcraft/apoli/common/power/configuration/DamageOverTimeConfiguration;Lnet/minecraft/world/entity/Entity;)I",
            at = @At(value = "RETURN"),
            cancellable = true
    )
    private void onGetProtection(DamageOverTimeConfiguration configuration,
                                 Entity entity,
                                 CallbackInfoReturnable<Integer> cir
    ) {
        if (configuration.protectionEnchantment() == null) {
            cir.setReturnValue(0);
        } else if (entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)entity;
            Map<EquipmentSlot, ItemStack> enchantedItems = configuration.protectionEnchantment().getSlotItems(living);
            Collection<ItemStack> iterable = enchantedItems.values();
            int i = 0;
            int items = 0;

            for(ItemStack itemStack : iterable) {
                int enchLevel = EnchantmentHelper.getItemEnchantmentLevel(configuration.protectionEnchantment(), itemStack);
                if(configuration.protectionEnchantment() == io.github.apace100.origins.registry.ModEnchantments.WATER_PROTECTION.get()) {
                    ToolStack stack = ToolStack.from(itemStack);
                    enchLevel += stack.getModifierLevel(ModifierId.tryBuild("tinkers_origin", "water_protection"));
                }
                i += enchLevel;
                if (enchLevel > 0) {
                    ++items;
                }
            }
            cir.setReturnValue(i + items);
        } else {
            cir.setReturnValue(0);
        }
    }
}
