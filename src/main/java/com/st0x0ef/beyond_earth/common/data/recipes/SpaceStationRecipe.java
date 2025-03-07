package com.st0x0ef.beyond_earth.common.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import com.st0x0ef.beyond_earth.common.registries.RecipeSerializersRegistry;
import com.st0x0ef.beyond_earth.common.registries.RecipeTypeRegistry;

public class SpaceStationRecipe extends BeyondEarthRecipe {

	public static final ResourceLocation KEY;

	static {
		ResourceLocation id = RecipeSerializersRegistry.RECIPE_SERIALIZER_SPACE_STATION.getId();
		KEY = new ResourceLocation(id.getNamespace(), id.getPath() + "/space_station");
	}

	private final NonNullList<IngredientStack> ingredients;

	public SpaceStationRecipe(ResourceLocation id, FriendlyByteBuf buffer) {
		super(id);
		this.ingredients = NonNullList.withSize(buffer.readInt(), IngredientStack.EMPTY);

        this.ingredients.replaceAll(ignored -> new IngredientStack(buffer));
	}

	public SpaceStationRecipe(ResourceLocation id, JsonObject json) {
		super(id);

		JsonArray asJsonArray = GsonHelper.getAsJsonArray(json, "ingredients");
		this.ingredients = NonNullList.withSize(asJsonArray.size(), IngredientStack.EMPTY);

		for (int i = 0; i < this.ingredients.size(); i++) {
			this.ingredients.set(i, new IngredientStack(asJsonArray.get(i)));
		}
	}

	public SpaceStationRecipe(ResourceLocation id, NonNullList<IngredientStack> ingredients) {
		super(id);
		this.ingredients = NonNullList.of(IngredientStack.EMPTY, ingredients.toArray(IngredientStack[]::new));
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		super.write(buffer);

		buffer.writeInt(this.ingredients.size());
		this.ingredients.forEach(e -> e.toNetwork(buffer));
	}

	@Override
	public boolean matches(Container p_44002_, Level p_44003_) {
		return false;
	}

	@Override
	public ItemStack assemble(Container p_44001_, RegistryAccess p_267165_) {
		return null;
	}

	@Override
	public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
		return true;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess p_267052_) {
		return null;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializersRegistry.RECIPE_SERIALIZER_SPACE_STATION.get();
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeTypeRegistry.SPACE_STATION.get();
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return NonNullList.of(Ingredient.EMPTY, this.ingredients.stream().map(IngredientStack::getIngredient).toArray(Ingredient[]::new));
	}

	public NonNullList<IngredientStack> getIngredientStacks() {
		return NonNullList.of(IngredientStack.EMPTY, this.ingredients.toArray(IngredientStack[]::new));
	}

}
