package com.creysys.guideBook.client.recipe.handler;

import com.creysys.guideBook.api.DrawableRecipe;
import com.creysys.guideBook.api.RecipeHandler;
import com.creysys.guideBook.client.recipe.DrawableRecipeCrafting;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;

/**
 * Created by Creysys on 21 Mar 16.
 */
public class RecipeHandlerCrafting extends RecipeHandler {
    @Override
    public String getName() {
        return "guideBook.crafting";
    }

    @Override
    public Object getTabIcon() {
        return Blocks.crafting_table;
    }

    @Override
    public int recipesPerPage() {
        return 2;
    }

    @Override
    public ArrayList<DrawableRecipe> getRecipes() {
        ArrayList<DrawableRecipe> ret = new ArrayList<DrawableRecipe>();
        for (IRecipe recipe : CraftingManager.getInstance().getRecipeList()) {
            DrawableRecipeCrafting r = DrawableRecipeCrafting.parse(recipe);
            if(r != null) ret.add(r);
        }
        return ret;
    }
}
