package com.creysys.guideBook.client.recipe.handler;

import com.creysys.guideBook.api.DrawableRecipe;
import com.creysys.guideBook.api.RecipeHandler;
import com.creysys.guideBook.client.recipe.DrawableRecipeSmelting;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Creysys on 21 Mar 16.
 */
public class RecipeHandlerSmelting extends RecipeHandler {
    @Override
    public String getName() {
        return "guideBook.smelting";
    }

    @Override
    public Object getTabIcon() {
        return Blocks.furnace;
    }

    @Override
    public int recipesPerPage() {
        return 2;
    }

    @Override
    public ArrayList<DrawableRecipe> getRecipes() {
        ArrayList<DrawableRecipe> ret = new ArrayList<DrawableRecipe>();
        for (Map.Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet()) {
            if(entry.getKey() != null && entry.getValue() != null) ret.add(new DrawableRecipeSmelting(entry.getKey(), entry.getValue()));
        }
        return ret;
    }
}
