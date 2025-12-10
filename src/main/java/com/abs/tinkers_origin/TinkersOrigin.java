package com.abs.tinkers_origin;

import com.abs.tinkers_origin.modifier.SunscreenEvent;
import com.abs.tinkers_origin.registry.LivingEntityPredicateRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(TinkersOrigin.MODID)
public class TinkersOrigin {
    public static final String MODID = "tinkers_origin";
    public static final Logger LOGGER = LoggerFactory.getLogger(TinkersOrigin.class);

    public TinkersOrigin() {
        LOGGER.info("Tinker's Origin mod initialized");
        LivingEntityPredicateRegistry.register();
        MinecraftForge.EVENT_BUS.register(new SunscreenEvent());
    }
}