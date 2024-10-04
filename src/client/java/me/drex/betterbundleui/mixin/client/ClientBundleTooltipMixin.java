package me.drex.betterbundleui.mixin.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientBundleTooltip;
import net.minecraft.world.item.component.BundleContents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static me.drex.betterbundleui.BetterBundleUI.getColumns;

@Mixin(ClientBundleTooltip.class)
public abstract class ClientBundleTooltipMixin {

    @Shadow
    @Final
    private BundleContents contents;

    @Shadow
    @Final
    private static int SLOT_SIZE;

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


}
