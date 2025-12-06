package com.abs.tinkers_origin;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(tinkers_origin.MODID)
public class tinkers_origin {
    public static final String MODID = "tinkers_origin";
    private static final Logger LOGGER = LoggerFactory.getLogger(tinkers_origin.class);

    public tinkers_origin() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        LOGGER.info("Tinker's Origin mod initialized");
    }
}