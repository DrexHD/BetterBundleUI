package me.drex.betterbundleui.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientBundleTooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.component.BundleContents;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.drex.betterbundleui.BetterBundleUI.getColumns;

@Mixin(ClientBundleTooltip.class)
public abstract class ClientBundleTooltipMixin {

    @Shadow
    @Final
    private BundleContents contents;

    @Mutable
    @Shadow
    @Final
    private static int SLOT_SIZE;

    @Inject(
        method = "<clinit>",
        at = @At("RETURN")
    )
    private static void changeSlotSize(CallbackInfo ci) {
        SLOT_SIZE = 18;
    }

    @ModifyConstant(
        method = {
            "slotCount",
            "renderBundleWithItemsTooltip"
        },
        constant = @Constant(intValue = 12)
    )
    public int removeLimit(final int constant) {
        return Integer.MAX_VALUE;
    }

    @ModifyConstant(
        method = "gridSizeY",
        constant = @Constant(intValue = 4)
    )
    public int dynamicColumnCount(final int constant) {
        return getColumns(contents.size());
    }

    @ModifyConstant(
        method = "renderBundleWithItemsTooltip",
        constant = @Constant(intValue = 4, ordinal = 0)
    )
    public int dynamicColumnCount2(final int constant) {
        return getColumns(contents.size());
    }

    @ModifyConstant(
        method = {
            "renderBundleWithItemsTooltip",
            "getContentXOffset",
            "drawProgressbar"
        },
        constant = @Constant(intValue = 96)
    )
    public int changeWidth(final int constant) {
        return getColumns(contents.size()) * SLOT_SIZE;
    }

    @ModifyConstant(
        method = "drawProgressbar",
        constant = @Constant(intValue = 48)
    )
    public int changeWidth2(final int constant) {
        return (getColumns(contents.size()) * SLOT_SIZE) / 2;
    }

    /**
     * @author drex
     * @reason dynamic width
     */
    @Overwrite
    public int getWidth(Font font) {
        return getColumns(contents.size()) * SLOT_SIZE;
    }

    @ModifyConstant(
        method = "getProgressBarFill",
        constant = @Constant(intValue = 94)
    )
    public int changeWidth3(final int constant) {
        return (getColumns(contents.size()) * SLOT_SIZE) - 2;
    }

    @ModifyConstant(
        method = "*",
        constant = @Constant(intValue = 24)
    )
    public int changeSlotSize(final int constant) {
        return SLOT_SIZE;
    }

    @ModifyConstant(
        method = "renderSlot",
        constant = @Constant(intValue = 4)
    )
    public int changeSlotSize2(final int constant) {
        return 1;
    }

    @ModifyReturnValue(
        method = "getProgressBarFillText",
        at = @At("RETURN")
    )
    public Component addFractionText(Component original) {
        if (original == null) {
            Fraction fraction = this.contents.weight();
            if (Mth.isPowerOfTwo(fraction.getDenominator())) {
                // If the denominator is not a power of two the bundle contains stack(s) with weird max stack sizes
                // which will cause crazy fractions, which are not useful for the end user
                int multiplier = Math.max(1, 64 / fraction.getDenominator());
                return Component.literal("%d / %d".formatted(multiplier * fraction.getNumerator(), multiplier * fraction.getDenominator()));
            }

        }
        return original;
    }

}
