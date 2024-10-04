package me.drex.betterbundleui;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Mth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterBundleUI implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("betterbundleui");

    @Override
    public void onInitialize() {
    }

    public static int getColumns(int itemCount) {
        return Math.max(4, Mth.ceil(Mth.sqrt(itemCount)));
    }

}