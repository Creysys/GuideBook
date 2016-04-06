package com.creysys.guideBook.client.recipe.handler;

import com.creysys.guideBook.api.DrawableRecipe;
import com.creysys.guideBook.api.RecipeHandler;
import com.creysys.guideBook.client.recipe.DrawableRecipeCrafting;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;

import java.util.ArrayList;

/**
 * Created by Creysys on 21 Mar 16.
 */
public class RecipeHandlerCrafting extends RecipeHandler {

    public void addFireworkRecipes(ArrayList<DrawableRecipe> list) {
    }

    public void addTippedArrowRecipes(ArrayList<DrawableRecipe> list) {
        ItemStack arrow = new ItemStack(Items.arrow);

        for (PotionType type : PotionType.potionTypeRegistry) {
            ItemStack input = new ItemStack(Items.lingering_potion);
            PotionUtils.addPotionToItemStack(input, type);

            ItemStack output = new ItemStack(Items.tipped_arrow, 8);
            PotionUtils.addPotionToItemStack(output, type);

            list.add(new DrawableRecipeCrafting(output, new ItemStack[]{arrow, arrow, arrow, arrow, input, arrow, arrow, arrow, arrow}, 3));
        }
    }

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

        addFireworkRecipes(ret);
        addTippedArrowRecipes(ret);

        return ret;
    }
}
