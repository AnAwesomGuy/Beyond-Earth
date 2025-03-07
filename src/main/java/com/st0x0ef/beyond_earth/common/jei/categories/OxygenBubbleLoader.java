package com.st0x0ef.beyond_earth.common.jei.categories;

import java.util.List;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.common.Constants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import com.st0x0ef.beyond_earth.BeyondEarth;
import com.st0x0ef.beyond_earth.common.config.Config;
import com.st0x0ef.beyond_earth.common.data.recipes.OxygenBubbleDistributorRecipe;
import com.st0x0ef.beyond_earth.common.jei.Jei;
import com.st0x0ef.beyond_earth.common.jei.helper.CustomFluidRenderer;
import com.st0x0ef.beyond_earth.common.jei.helper.EnergyIngredient;
import com.st0x0ef.beyond_earth.common.jei.helper.O2Ingredient;
import com.st0x0ef.beyond_earth.common.registries.ItemsRegistry;

public class OxygenBubbleLoader implements IRecipeCategory<OxygenBubbleDistributorRecipe> {
    public static final ResourceLocation GUI = new ResourceLocation(BeyondEarth.MODID, "textures/jei/jei_gui_1.png");

    public static final int width = 128;
    public static final int height = 64;

    private final IDrawable background;
    private final IDrawable icon;
    private final String localizedName;
    final IGuiHelper guiHelper;

    private final LoadingCache<Integer, IDrawableAnimated> cachedArrow;

    public OxygenBubbleLoader(final IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        this.background = guiHelper.createDrawable(OxygenBubbleLoader.GUI, 0, 128, OxygenBubbleLoader.width,
                OxygenBubbleLoader.height);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(ItemsRegistry.COAL_GENERATOR_ITEM.get()));
        this.localizedName = I18n.get("container." + BeyondEarth.MODID + ".oxygen_bubble_distributor");

        this.cachedArrow = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<>() {
            @Override
            public IDrawableAnimated load(Integer cookTime) {
                return guiHelper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 82, 128, 24, 17).buildAnimated(cookTime,
                        IDrawableAnimated.StartDirection.LEFT, false);
            }
        });
    }

    @Override
    public RecipeType<OxygenBubbleDistributorRecipe> getRecipeType() {
        return Jei.OXYGEN_BUBBLE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal(this.localizedName);
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public List<Component> getTooltipStrings(OxygenBubbleDistributorRecipe recipe, IRecipeSlotsView recipeSlotsView,
            double mouseX, double mouseY) {
        return IRecipeCategory.super.getTooltipStrings(recipe, recipeSlotsView, mouseX, mouseY);
    }

    @Override
    public void draw(OxygenBubbleDistributorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {

        IDrawableAnimated arrow = cachedArrow.getUnchecked(100 / Config.FUEL_REFINERY_ENERGY_USAGE.get());
        arrow.draw(graphics, 40, 22);

        // Update the energy cost
        recipeSlotsView.getSlotViews(RecipeIngredientRole.INPUT).get(0).getIngredients(Jei.FE_INGREDIENT_TYPE)
                .forEach(i -> i.setAmount(Config.FUEL_REFINERY_ENERGY_USAGE.get()));

        // Update the o2 amount
        recipeSlotsView.getSlotViews(RecipeIngredientRole.OUTPUT).get(0).getIngredients(Jei.O2_INGREDIENT_TYPE)
                .forEach(i -> i.setAmount(recipe.getOxygen()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, OxygenBubbleDistributorRecipe recipe, IFocusGroup focuses) {
        IRecipeSlotBuilder inputStack = builder.addSlot(RecipeIngredientRole.INPUT, 20, 8);
        inputStack.addIngredients(ForgeTypes.FLUID_STACK, recipe.getInput().toStacks());
        inputStack.setCustomRenderer(ForgeTypes.FLUID_STACK, new CustomFluidRenderer(true));

        inputStack = builder.addSlot(RecipeIngredientRole.INPUT, 108, 9);
        inputStack.addIngredient(Jei.FE_INGREDIENT_TYPE, EnergyIngredient.INTANK);
        inputStack.setCustomRenderer(Jei.FE_INGREDIENT_TYPE, EnergyIngredient.INTANK);

        IRecipeSlotBuilder outputStack = builder.addSlot(RecipeIngredientRole.OUTPUT, 73, 8);
        outputStack.addIngredient(Jei.O2_INGREDIENT_TYPE, O2Ingredient.OUTTANK);
        outputStack.setCustomRenderer(Jei.O2_INGREDIENT_TYPE, O2Ingredient.OUTTANK);
    }

}
