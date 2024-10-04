package me.drex.betterbundleui.mixin.client;

import net.minecraft.client.gui.screens.inventory.tooltip.ClientBundleTooltip;
import net.minecraft.world.item.component.BundleContents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static me.drex.betterbundleui.BetterBundleUI.COLUMNS;
import static me.drex.betterbundleui.BetterBundleUI.ROWS;

@Mixin(ClientBundleTooltip.class)
public abstract class ClientBundleTooltipMixin {

    // TODO Add gridSizeX to not always draw all columns

    @Shadow @Final private BundleContents contents;

    @ModifyConstant(
        method = {
            "slotCount",
            "renderBundleWithItemsTooltip"
        },
        constant = @Constant(intValue = 12)
    )
    public int changeLimit(final int constant) {
        return ROWS * COLUMNS;
    }

    @ModifyConstant(
        method = "gridSizeY",
        constant = @Constant(intValue = 4)
    )
    public int changeColumnCount(final int constant) {
        return COLUMNS;
    }

    @ModifyConstant(
        method = "renderBundleWithItemsTooltip",
        constant = @Constant(intValue = 4, ordinal = 0)
    )
    public int changeColumnCount2(final int constant) {
        return COLUMNS;
    }

    @ModifyConstant(
        method = {
            "getWidth",
            "renderBundleWithItemsTooltip",
            "getContentXOffset"
        },
        constant = @Constant(intValue = 96)
    )
    public int changeWidth(final int constant) {
        return COLUMNS * 24;
    }


//    @ModifyConstant(
//        method = "getProgressBarFillText",
//        constant = @Constant(nullValue = true)
//    )
//    public Object addFractionText(Object _null)

//    @ModifyReturnValue(
//        method = "getProgressBarFillText",
//        at = @At("RETURN")
//    )
//    public Component addFractionText(Component original) {
//        if (original == null) {
//            Fraction weight = this.contents.weight();
//            return Component.literal("%d / %d".formatted(weight.getNumerator(), weight.getDenominator()));
//        }
//        return original;
//    }

}
