package com.creysys.guideBook.plugin.vanilla.recipe.handler;

import com.creysys.guideBook.api.DrawableRecipe;
import com.creysys.guideBook.api.ItemInfoManager;
import com.creysys.guideBook.api.RecipeHandler;
import com.creysys.guideBook.plugin.vanilla.recipe.DrawableRecipeInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Creysys on 27 Mar 16.
 */
public class RecipeHandlerInfo extends RecipeHandler {
    public static final ResourceLocation tabIcon = new ResourceLocation("guidebook", "textures/gui/infotabicon.png");

    @Override
    public String getName() {
        return "guidebook.info";
    }

    @Override
    public Object getTabIcon() {
        return tabIcon;
    }

    @Override
    public int recipesPerPage() {
        return 1;
    }

    @Override
    public ArrayList<DrawableRecipe> getRecipes() {
        ArrayList<DrawableRecipe> ret = new ArrayList<DrawableRecipe>();
        for (Map.Entry<ItemStack, String> entry : ItemInfoManager.infos.entrySet())
            ret.add(new DrawableRecipeInfo(entry.getKey(), entry.getValue()));

        return ret;
    }
}
