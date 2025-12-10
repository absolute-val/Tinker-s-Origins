package com.abs.tinkers_origin.mixin;

import com.abs.tinkers_origin.utils.Origins;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import slimeknights.mantle.Mantle;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.data.predicate.entity.LivingEntityPredicate;
import slimeknights.mantle.data.registry.GenericLoaderRegistry;

@Mixin(value = GenericLoaderRegistry.class, remap = false)
public class MixinPlayerWaterSensitive {
    @ModifyVariable(
            method = "register(Lnet/minecraft/resources/ResourceLocation;Lslimeknights/mantle/data/loadable/record/RecordLoadable;)V",
            at = @At("HEAD"),
            argsOnly = true,
            index = 2
    )
    @SuppressWarnings("unchecked")
    private <T> RecordLoadable<? extends T> onRegisterWaterSensitive(RecordLoadable<? extends T> value, ResourceLocation name) {
        if(name.equals(Mantle.getResource("water_sensitive"))) {
            return (RecordLoadable<? extends T>) LivingEntityPredicate.simple(
                    living -> {
                    if (living instanceof Player) {
                        return Origins.havePower((Player) living, ResourceLocation.tryBuild("origins", "water_vulnerability"));
                    }
                    return living.isSensitiveToWater();
                }
            ).getLoader();
        }
        return value;
    }
//    @Shadow
//    protected void register(ResourceLocation name, RecordLoadable<? extends T> loader) {
//        this.loaders.register(name, loader);
//    }
//    @Inject(
//            method = "simple(Ljava/util/function/Predicate;)Lslimeknights/mantle/data/predicate/entity/LivingEntityPredicate;",
//            at = @At("HEAD")
//    )
//    private static void onLivingEntityPredicateTransfer(final Predicate<LivingEntity> predicate, CallbackInfoReturnable<LivingEntityPredicate> cir) {
//        if (predicate.equals((Predicate<LivingEntity>) LivingEntity::isSensitiveToWater)) {
//            RegistryPredicateRegistry
//            cir.setReturnValue(SingletonLoader.singleton((loader) -> new LivingEntityPredicate() {
//                public boolean matches(@NotNull LivingEntity entity) {
//                    return entity.isSensitiveToWater();
//                }
//                public RecordLoadable<? extends LivingEntityPredicate> getLoader() {
//                    return loader;
//                }
//            }));
//        }
//    }
}
