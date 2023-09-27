package com.st0x0ef.beyond_earth.mixin;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.common.MinecraftForge;
import com.st0x0ef.beyond_earth.common.events.forge.ItemEntityTickAtEndEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityTickEnd {
    @Inject(at = @At(value = "RETURN"), method = "tick")
    private void tick(CallbackInfo info) {
        ItemEntity itemEntity = (ItemEntity) ((Object) this);

        MinecraftForge.EVENT_BUS.post(new ItemEntityTickAtEndEvent(itemEntity));
    }
}
