package com.creysys.guideBook.client.recipe;

import com.creysys.guideBook.api.DrawableRecipe;
import com.creysys.guideBook.client.GuideBookGui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creysys on 21 Mar 16.
 */
public class DrawableRecipeCrafting extends DrawableRecipe {

    public static final ResourceLocation craftingGridTexture = new ResourceLocation("guidebook", "textures/gui/craftingGrid.png");

    @SuppressWarnings("unchecked")
    public static DrawableRecipeCrafting parse(IRecipe recipe) {
        if(recipe instanceof ShapedRecipes) {
            ShapedRecipes r = (ShapedRecipes) recipe;
            return new DrawableRecipeCrafting(r.getRecipeOutput(), r.recipeItems, r.recipeWidth);
        } else if(recipe instanceof ShapelessRecipes) {
            ShapelessRecipes r = (ShapelessRecipes) recipe;
            return new DrawableRecipeCrafting(r.getRecipeOutput(), r.recipeItems.toArray(new ItemStack[0]), 3);
        } else if(recipe instanceof ShapedOreRecipe) {
            ShapedOreRecipe r = (ShapedOreRecipe) recipe;
            ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
            for (Object obj : r.getInput()) {
                if(obj instanceof ItemStack) {
                    ret.add((ItemStack) obj);
                } else if(obj instanceof List) {
                    List<ItemStack> l = (List<ItemStack>) obj;
                    if (l.size() == 0) return null;
                    ret.add(l.get(0));
                } else if(obj == null) {
                    ret.add(null);
                } else return null;
            }

            int width = ReflectionHelper.getPrivateValue(ShapedOreRecipe.class, r, 4);

            return new DrawableRecipeCrafting(r.getRecipeOutput(), ret.toArray(new ItemStack[0]), width);
        } else if(recipe instanceof ShapelessOreRecipe) {
            ShapelessOreRecipe r = (ShapelessOreRecipe) recipe;
            ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
            for (Object obj : r.getInput()) {
                if(obj instanceof ItemStack) {
                    ret.add((ItemStack) obj);
                } else if(obj instanceof List) {
                    List<ItemStack> l = (List<ItemStack>) obj;
                    if (l.size() == 0) return null;
                    ret.add(l.get(0));
                } else if(obj == null) {
                    ret.add(null);
                } else return null;
            }

            return new DrawableRecipeCrafting(r.getRecipeOutput(), ret.toArray(new ItemStack[0]), 3);
        }

        return null;
    }

    public ItemStack output;
    public ItemStack[] input;
    public int width;

    public DrawableRecipeCrafting(ItemStack output, ItemStack[] input, int width) {
        this.output = output.copy();
        this.input = new ItemStack[input.length];
        for(int i = 0; i < input.length; i++){
            if(input[i] != null) this.input[i] = input[i].copy();
        }
        this.width = width;
    }

    @Override
    public ItemStack[] getInput() {
        return input;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    @Override
    public void draw(GuideBookGui gui, int pageRecipeIndex) {
        if(pageRecipeIndex == 0) drawRecipe(gui, gui.left + 38,  gui.top + 14);
        else if(pageRecipeIndex == 1) drawRecipe(gui, gui.left + 38,  gui.top + 94);
    }

    @Override
    public void drawForeground(GuideBookGui gui, int pageRecipeIndex, int mouseX, int mouseY) {
        if(pageRecipeIndex == 0) drawRecipeTooltip(gui, gui.left + 38,  gui.top + 14, mouseX, mouseY);
        else if(pageRecipeIndex == 1) drawRecipeTooltip(gui, gui.left + 38,  gui.top + 94, mouseX, mouseY);
    }

    @Override
    public void mouseClick(GuideBookGui gui, int pageRecipeIndex, int mouseX, int mouseY, int mouseButton) {
        if(pageRecipeIndex == 0) clickRecipe(gui, gui.left + 38,  gui.top + 14, mouseX, mouseY, mouseButton);
        else if(pageRecipeIndex == 1) clickRecipe(gui, gui.left + 38,  gui.top + 94, mouseX, mouseY, mouseButton);
    }


    public void drawRecipe(GuideBookGui gui, int left, int top) {
        gui.mc.getTextureManager().bindTexture(craftingGridTexture);
        RenderHelper.disableStandardItemLighting();
        gui.drawModalRectWithCustomSizedTexture(left, top, 0, 0, 112, 54, 112, 54);

        drawItemStack(gui, output, left + 91, top + 19, true);
        for(int i = 0; i < input.length; i++)
            if(input[i] != null)
                drawItemStack(gui, input[i], left + (i % width) * 18 + 1, top + i / width * 18 + 1, false);
    }

    public void drawRecipeTooltip(GuideBookGui gui, int left, int top, int mouseX, int mouseY) {
        drawItemStackTooltip(gui, output, left + 91, top + 19, mouseX, mouseY);
        for(int i = 0; i < input.length; i++) {
            if(input[i] != null) {
                int x = left + (i % width) * 18 + 1;
                int y = top + i / width * 18 + 1;
                drawItemStackTooltip(gui, input[i], x, y, mouseX, mouseY);
            }
        }
    }

    public void clickRecipe(GuideBookGui gui, int left, int top, int mouseX, int mouseY, int mouseButton) {
        clickItemStack(gui, output,left + 91, top + 19, mouseX, mouseY, mouseButton);
        for(int i = 0; i < input.length; i++) {
            if(input[i] != null) {
                int x = left + (i % width) * 18 + 1;
                int y = top + i / width * 18 + 1;
                clickItemStack(gui, input[i], x, y, mouseX, mouseY, mouseButton);
            }
        }
    }
}
