package amreborn.compat.jei;

import java.util.List;

import amreborn.ArsMagicaReborn;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

@SuppressWarnings("deprecation")
public class EssenceRefinerRecipeCategory implements IRecipeCategory<EssenceRefinerRecipeWrapper> {

	IDrawableStatic background;
	
	public EssenceRefinerRecipeCategory(IGuiHelper helpers) {
		this.background = helpers.createDrawable(new ResourceLocation(ArsMagicaReborn.MODID + ":textures/gui/essenceextractorgui.png"), 3, 25, 170, 114);
	}

	@Override
	public String getTitle() {
		return I18n.translateToLocal(getUid());
	}

	@Override
	public String getUid() {
		return "am2.essence_refiner";
	}
	
	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		
	}


	@Override
	public void setRecipe(IRecipeLayout recipeLayout, EssenceRefinerRecipeWrapper recipeWrapper, IIngredients ingredients) {
		recipeLayout.getItemStacks().init(0, true, 76, 16);
		recipeLayout.getItemStacks().init(1, true, 44, 48);
		recipeLayout.getItemStacks().init(2, true, 76, 48);
		recipeLayout.getItemStacks().init(3, true, 108, 48);
		recipeLayout.getItemStacks().init(4, true, 76, 81);
		recipeLayout.getItemStacks().init(5, false, 139, 84);
		
		recipeLayout.getItemStacks().set(0, (ItemStack)recipeWrapper.getInputs().get(0));
		recipeLayout.getItemStacks().set(1, (ItemStack)recipeWrapper.getInputs().get(1));
		recipeLayout.getItemStacks().set(2, (ItemStack)recipeWrapper.getInputs().get(2));
		recipeLayout.getItemStacks().set(3, (ItemStack)recipeWrapper.getInputs().get(3));
		recipeLayout.getItemStacks().set(4, (ItemStack)recipeWrapper.getInputs().get(4));
		recipeLayout.getItemStacks().set(5, (ItemStack)recipeWrapper.getOutputs().get(0));
	}

	@Override
	public String getModName() {
		return "ArsMagicaRebor,";
	}

	@Override
	public IDrawable getIcon() {
		return background;
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return null;
	}

}
