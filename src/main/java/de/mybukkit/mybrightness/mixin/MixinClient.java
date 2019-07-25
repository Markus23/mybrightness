package de.mybukkit.mybrightness.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.mybukkit.mybrightness.MyBrightness;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)

public class MixinClient {
	@Inject(method = "handleInputEvents", at = @At("TAIL"))
	private void handleKeybinds(CallbackInfo callbackInfo) {
		MyBrightness.handleKeybinds();
	}
}
