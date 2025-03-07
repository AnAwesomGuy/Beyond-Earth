package com.st0x0ef.beyond_earth.common.data.recipes;

import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.ForgeRegistries;
import com.st0x0ef.beyond_earth.common.registries.RecipeSerializersRegistry;
import com.st0x0ef.beyond_earth.common.registries.RecipeTypeRegistry;

public class AlienTradingRecipePotionedItem extends AlienTradingRecipeItemStack {

	public AlienTradingRecipePotionedItem(ResourceLocation id, JsonObject json) {
		super(id, json);
	}

	public AlienTradingRecipePotionedItem(ResourceLocation id, FriendlyByteBuf buffer) {
		super(id, buffer);
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		super.write(buffer);
	}

	@Override
	public ItemStack getResult(Entity trader, RandomSource rand) {
		ItemStack result = super.getResult(trader, rand);
		List<Potion> potions = ForgeRegistries.POTIONS.getValues().stream().filter(this::testPotion).toList();
		Potion potion = potions.get(rand.nextInt(potions.size()));
		return PotionUtils.setPotion(result, potion);
	}

	public boolean testPotion(Potion potion) {
		return !potion.getEffects().isEmpty() && PotionBrewing.isBrewablePotion(potion);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializersRegistry.RECIPE_SERIALIZER_ALIEN_TRADING_POTIONED_ITEM.get();
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.ALIEN_TRADING_POTIONED_ITEM.get();
	}

	public static class Serializer extends BeyondEarthRecipeSerializer<AlienTradingRecipePotionedItem> {
		@Override
		public AlienTradingRecipePotionedItem fromJson(ResourceLocation id, JsonObject json) {
			return new AlienTradingRecipePotionedItem(id, json);
		}

		@Override
		public AlienTradingRecipePotionedItem fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
			return new AlienTradingRecipePotionedItem(id, buffer);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, AlienTradingRecipePotionedItem recipe) {
			recipe.write(buffer);
		}

	}

}
