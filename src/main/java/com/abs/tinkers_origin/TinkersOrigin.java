package com.abs.tinkers_origin;

import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(TinkersOrigin.MODID)
public class TinkersOrigin {
    public static final String MODID = "tinkers_origin";
    public static final Logger LOGGER = LoggerFactory.getLogger(TinkersOrigin.class);

    public TinkersOrigin() {
        LOGGER.info("Tinker's Origin mod initialized");
    }
}