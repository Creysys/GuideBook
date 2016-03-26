package com.creysys.guideBook.client.recipe;

import com.creysys.guideBook.api.DrawableRecipe;
import com.creysys.guideBook.client.GuideBookGui;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Creysys on 21 Mar 16.
 */
public class DrawableRecipeSmelting extends DrawableRecipe {

    public static final ResourceLocation smeltingGridTexture = new ResourceLocation("guidebook", "textures/gui/smeltingGrid.png");
    public static final ItemStack coal = new ItemStack(Items.coal);

    private ItemStack input;
    private ItemStack output;

    public DrawableRecipeSmelting(ItemStack input, ItemStack output) {
        this.input = input.copy();
        this.output = output.copy();
    }

    @Override
    public ItemStack[] getInput() {
        return new ItemStack[] { input };
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    @Override
    public void draw(GuideBookGui gui, int pageRecipeIndex) {
        if(pageRecipeIndex == 0) drawRecipe(gui, gui.left + 48,  gui.top + 14);
        else if(pageRecipeIndex == 1) drawRecipe(gui, gui.left + 48,  gui.top + 94);
    }

    @Override
    public void drawForeground(GuideBookGui gui, int pageRecipeIndex, int mouseX, int mouseY) {
        if(pageRecipeIndex == 0) drawRecipeTooltip(gui, gui.left + 48,  gui.top + 14, mouseX, mouseY);
        else if(pageRecipeIndex == 1) drawRecipeTooltip(gui, gui.left + 48,  gui.top + 94, mouseX, mouseY);
    }

    @Override
    public void mouseClick(GuideBookGui gui, int pageRecipeIndex, int mouseX, int mouseY, int mouseButton) {
        if(pageRecipeIndex == 0) clickRecipe(gui, gui.left + 48,  gui.top + 14, mouseX, mouseY, mouseButton);
        else if(pageRecipeIndex == 1) clickRecipe(gui, gui.left + 48,  gui.top + 94, mouseX, mouseY, mouseButton);
    }


    public void drawRecipe(GuideBookGui gui, int left, int top) {
        gui.mc.getTextureManager().bindTexture(smeltingGridTexture);
        RenderHelper.disableStandardItemLighting();
        Gui.drawModalRectWithCustomSizedTexture(left, top, 0, 0, 82, 54, 105, 54);

        int j = ticks / 12 % 14;
        Gui.drawModalRectWithCustomSizedTexture(left + 2, top + 20 + j, 83, 29 + j, 13 ,14 - j, 105, 54);

        j = ticks / 6 % 23;
        Gui.drawModalRectWithCustomSizedTexture(left + 25, top + 19, 83, 8, j, 16, 105, 54);

        drawItemStack(gui, input, left + 1, top + 1, false);
        drawItemStack(gui, output, left + 61, top + 19, true);
        drawItemStack(gui, coal, left + 1, top + 37, false);
    }

    public void drawRecipeTooltip(GuideBookGui gui, int left, int top, int mouseX, int mouseY) {
        drawItemStackTooltip(gui, input, left + 1, top + 1, mouseX, mouseY);
        drawItemStackTooltip(gui, output, left + 60, top + 18, mouseX, mouseY);
        drawItemStackTooltip(gui, coal, left + 1, top + 37, mouseX, mouseY);
    }

    public void clickRecipe(GuideBookGui gui, int left, int top, int mouseX, int mouseY, int mouseButton) {
        clickItemStack(gui, input, left + 1, top + 1, mouseX, mouseY, mouseButton);
        clickItemStack(gui, output, left + 60, top + 18, mouseX, mouseY, mouseButton);
        clickItemStack(gui, coal, left + 1, top + 37, mouseX, mouseY, mouseButton);
    }
}
