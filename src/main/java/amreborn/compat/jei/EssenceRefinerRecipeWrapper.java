package amreborn.compat.jei;

import java.util.List;

import com.google.common.collect.Lists;

import amreborn.api.recipes.RecipeArsMagica;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

public class EssenceRefinerRecipeWrapper implements IRecipeWrapper {
	
	RecipeArsMagica recipe;
	
	public EssenceRefinerRecipeWrapper(RecipeArsMagica recipe) {
		this.recipe = recipe;
	}
	
	public List<?> getInputs() {
		return Lists.newArrayList(recipe.getRecipeItems());
	}

	public List<?> getOutputs() {
		return Lists.newArrayList(recipe.getOutput());
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return null;
	}

	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
		return false;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		
	}

}
