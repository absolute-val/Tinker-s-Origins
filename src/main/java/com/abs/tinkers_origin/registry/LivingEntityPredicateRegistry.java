package com.abs.tinkers_origin.registry;

import com.abs.tinkers_origin.utils.Origins;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import slimeknights.mantle.data.predicate.entity.LivingEntityPredicate;

public class LivingEntityPredicateRegistry {
    public static void register() {
        LivingEntityPredicate.LOADER.register(ResourceLocation.fromNamespaceAndPath("tinkers_origin", "breathe_underwater"), LivingEntityPredicate.simple(
                living -> {
                    if (living instanceof Player) {
                        return Origins.havePower((Player) living, ResourceLocation.fromNamespaceAndPath("origins", "water_breathing"));
                    }
                    return false;
                }
        ).getLoader());
    }
}
