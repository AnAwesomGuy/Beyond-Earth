package com.st0x0ef.beyond_earth.common.data.recipes;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class WorkbenchingRecipeSerializer extends BeyondEarthRecipeSerializer<WorkbenchingRecipe> {

	@Override
	public WorkbenchingRecipe fromJson(ResourceLocation id, JsonObject json) {
		return new WorkbenchingRecipe(id, json);
	}

	@Override
	public WorkbenchingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
		return new WorkbenchingRecipe(id, buffer);
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, WorkbenchingRecipe recipe) {
		recipe.write(buffer);
	}

}
