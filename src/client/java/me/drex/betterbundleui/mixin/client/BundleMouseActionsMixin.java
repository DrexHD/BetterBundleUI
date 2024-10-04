package me.drex.betterbundleui.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.BundleMouseActions;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static me.drex.betterbundleui.BetterBundleUI.getColumns;

@Mixin(BundleMouseActions.class)
public abstract class BundleMouseActionsMixin {

    @WrapOperation(
        method = "onMouseScrolled",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/ScrollWheelHandler;getNextScrollWheelSelection(DII)I"
        )
    )
    public int getNextSlot(double scrollVector, int currentItem, int itemCount, Operation<Integer> original) {
        if (Screen.hasShiftDown()) {
            // [VanillaCopy] getNextScrollWheelSelection
            int k = (int)Math.signum(scrollVector);
            k *= getColumns(itemCount);
            currentItem -= k;

            while (currentItem < 0) {
                currentItem += itemCount;
            }

            while (currentItem >= itemCount) {
                currentItem -= itemCount;
            }

            return currentItem;
        }
        return original.call(scrollVector, currentItem, itemCount);
    }

}
