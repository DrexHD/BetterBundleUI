package me.drex.betterbundleui.mixin;

import net.minecraft.world.item.component.BundleContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BundleContents.class)
public abstract class BundleContentsMixin {

    @Shadow
    public abstract int size();

    /**
     * @author drex
     * @reason show all items
     */
    @Overwrite
    public int getNumberOfItemsToShow() {
        return this.size();
    }

}
