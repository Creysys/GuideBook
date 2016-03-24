package com.creysys.guideBook.client.recipe;

import com.creysys.guideBook.api.DrawableRecipe;
import com.creysys.guideBook.client.GuideBookGui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Creysys on 21 Mar 16.
 */
public class DrawableRecipeSmelting extends DrawableRecipe {

    public static final ResourceLocation smeltingGridTexture = new ResourceLocation("guidebook", "textures/gui/smeltingGrid.png");

    private ItemStack input;
    private ItemStack output;
    private int ticks;

    public DrawableRecipeSmelting(ItemStack input, ItemStack output) {
        this.input = input.copy();
        this.output = output.copy();
        this.ticks = 0;

        //Check for wildcard damage 32767
        if(this.input.getItemDamage() == 32767) this.input.setItemDamage(0);
        if(this.output.getItemDamage() == 32767) this.output.setItemDamage(0);
    }

    @Override
    public void update() {
        ticks++;
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
        if(pageRecipeIndex == 0) clickRecipe(gui, gui.left + 48,  gui.top + 14, mouseX, mouseY);
        else if(pageRecipeIndex == 1) clickRecipe(gui, gui.left + 48,  gui.top + 94, mouseX, mouseY);
    }


    public void drawRecipe(GuideBookGui gui, int left, int top) {
        gui.mc.getTextureManager().bindTexture(smeltingGridTexture);
        RenderHelper.disableStandardItemLighting();
        gui.drawTexturedModalRect(left, top, 0, 0, 82, 54);

        int j = ticks / 12 % 14;
        gui.drawTexturedModalRect(left + 2, top + 21 + j, 0, 77 + j, 13 ,14 - j);

        j = ticks / 6 % 22;
        gui.drawTexturedModalRect(left + 25, top + 19, 0, 56, j, 15);

        RenderHelper.enableGUIStandardItemLighting();
        gui.getRenderItem().renderItemAndEffectIntoGUI(input, left + 1, top + 1);
        gui.getRenderItem().renderItemOverlayIntoGUI(gui.getFontRenderer(), input, left + 1, top + 1, null);
        gui.getRenderItem().renderItemAndEffectIntoGUI(output, left + 60, top + 18);
        gui.getRenderItem().renderItemOverlayIntoGUI(gui.getFontRenderer(), output, left + 60, top + 18, null);
    }

    public void drawRecipeTooltip(GuideBookGui gui, int left, int top, int mouseX, int mouseY) {
        if(left + 1 < mouseX && mouseX < left + 1 + 18 && top + 1 < mouseY && mouseY < top + 1 + 18) {
            gui.drawHoveringString(input.getDisplayName(), mouseX, mouseY);
            return;
        }

        else if(left + 60 < mouseX && mouseX < left + 60 + 18 && top + 18 < mouseY && mouseY < top + 18 + 18) {
            gui.drawHoveringString(output.getDisplayName(), mouseX, mouseY);
            return;
        }
    }

    public void clickRecipe(GuideBookGui gui, int left, int top, int mouseX, int mouseY) {
        if(left + 1 < mouseX && mouseX < left + 1 + 18 && top + 1 < mouseY && mouseY < top + 1 + 18) {
            gui.openRecipeState(input);
            return;
        }
    }
}
