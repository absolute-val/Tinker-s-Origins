package com.abs.tinkers_origin.mixin;

import io.github.edwinmindcraft.apoli.common.condition.item.ComparingItemCondition;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.function.Supplier;

import static com.abs.tinkers_origin.TinkersOrigin.LOGGER;

@Mixin(value = DeferredRegister.class, remap = false)
public class MixinArmorValue {
    @ModifyVariable(
            method = "register(Ljava/lang/String;Ljava/util/function/Supplier;)Lnet/minecraftforge/registries/RegistryObject;",
            at = @At("HEAD"),
            argsOnly = true,
            index = 2
    )
    private <I> Supplier<I> onRegisterArmorValue(Supplier<I> sup, String name) {
        if (name.equals("armor_value")) {
            return () ->{
                I original = sup.get();
                if (original instanceof ComparingItemCondition) {
                    ComparingItemCondition condition = new ComparingItemCondition((itemStack) -> {Item item = itemStack.getItem();
                        int armor = 0;
                        if (item instanceof ArmorItem) {
                            armor = ((ArmorItem)item).getDefense();
                        }
                        ToolStack stack = ToolStack.from(itemStack);
                        armor += stack.getStats().get(ToolStats.ARMOR);
                        LOGGER.info(Integer.toString(armor));
                        return armor;
                    });
                    @SuppressWarnings("unchecked")
                    I result = (I) condition;
                    return result;
                }
                return original;
            };
        }
        return sup;
    }
}
