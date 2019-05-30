package de.mybukit.mybrightness.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import de.mybukit.mybrightness.MyBrightness;
import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)

public class MixinClient {
	@Inject(method = "handleInputEvents", at = @At("TAIL"))
	private void handleKeybinds(CallbackInfo callbackInfo) {
		MyBrightness.handleKeybinds();
	}
}
