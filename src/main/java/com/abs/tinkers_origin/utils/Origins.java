package com.abs.tinkers_origin.utils;

import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.origins.api.OriginsAPI;
import io.github.edwinmindcraft.origins.api.capabilities.IOriginContainer;
import io.github.edwinmindcraft.origins.api.origin.Origin;
import io.github.edwinmindcraft.origins.api.origin.OriginLayer;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;
import java.util.Optional;

public class Origins {
    public static boolean havePower(Player player, ResourceLocation power) {
        final boolean[] flag = {false};
        for (Holder.Reference<OriginLayer> layerReference : OriginsAPI.getActiveLayers()) {
            IOriginContainer.get(player).ifPresent(container -> {
                ResourceKey<Origin> origin = container.getOrigin(layerReference.value());
                Optional<Holder.Reference<Origin>> originHolder = OriginsAPI.getOriginsRegistry().getHolder(origin);
                originHolder.ifPresent(originReference -> {
                    for (HolderSet<ConfiguredPower<?, ?>> powerHolderSet : originReference.get().getPowers()) {
                        for (Holder<ConfiguredPower<?, ?>> powerHolder : powerHolderSet) {
                            flag[0] |= powerHolder.is(Objects.requireNonNull(power));
                        }
                    }
                });
            });
        }
        return flag[0];
    }
}
