package amreborn.compat;

import amreborn.api.recipes.RecipesEssenceRefiner;
import amreborn.compat.jei.EssenceRefinerRecipeCategory;
import amreborn.compat.jei.EssenceRefinerRecipeHandler;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

@JEIPlugin
public class JEIHandler implements IModPlugin{

	@Override
	public void register(IModRegistry registry) {
		registry.addRecipeCategories(new EssenceRefinerRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeHandlers(new EssenceRefinerRecipeHandler());
		registry.addRecipes(RecipesEssenceRefiner.essenceRefinement().getAllRecipes());
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
		
	}

	@Override
	public void registerIngredients(IModIngredientRegistration registry) {
		
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		
	}

}
