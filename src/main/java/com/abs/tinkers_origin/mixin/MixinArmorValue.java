package com.abs.tinkers_origin.mixin;

import com.abs.tinkers_origin.TinkersOrigin;
import io.github.edwinmindcraft.apoli.common.condition.item.ComparingItemCondition;
import io.github.edwinmindcraft.apoli.common.registry.ApoliRegisters;
import io.github.edwinmindcraft.apoli.common.registry.condition.ApoliItemConditions;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.io.IOException;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import static com.abs.tinkers_origin.TinkersOrigin.LOGGER;

@Mixin(value = DeferredRegister.class, remap = false)
public class MixinArmorValue {
    @ModifyVariable(
            method = "register(Ljava/lang/String;Ljava/util/function/Supplier;)Lnet/minecraftforge/registries/RegistryObject;",
            at = @At("HEAD"),
            argsOnly = true,
            index = 2
    )
    private <I> Supplier<I> a(Supplier<I> sup, String name) {
        if (name.equals("armor_value")) {
            return () ->{
                I original = sup.get();
                if (original instanceof ComparingItemCondition) {
                    ComparingItemCondition condition = new ComparingItemCondition((itemStack) -> {Item patt2784$temp = itemStack.getItem();
                        int armor = 0;
                        if (patt2784$temp instanceof ArmorItem ai) {
                            armor = ai.getDefense();
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
