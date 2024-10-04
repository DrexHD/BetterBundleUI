package me.drex.betterbundleui.mixin;

import net.minecraft.world.item.component.BundleContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static me.drex.betterbundleui.BetterBundleUI.COLUMNS;
import static me.drex.betterbundleui.BetterBundleUI.ROWS;

@Mixin(BundleContents.class)
public abstract class BundleContentsMixin {

    @ModifyConstant(
        method = "getNumberOfItemsToShow",
        constant = @Constant(intValue = 12)
    )
    public int changeLimit(final int constant) {
        return ROWS * COLUMNS;
    }

    @ModifyConstant(
        method = "getNumberOfItemsToShow",
        constant = @Constant(intValue = 11)
    )
    public int changeLimit2(final int constant) {
        return (ROWS * COLUMNS) - 1;
    }

    @ModifyConstant(
        method = "getNumberOfItemsToShow",
        constant = @Constant(intValue = 4)
    )
    public int changeColumns(final int constant) {
        return COLUMNS;
    }

}
