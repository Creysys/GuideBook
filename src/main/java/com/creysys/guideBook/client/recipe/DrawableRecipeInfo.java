package com.creysys.guideBook.client.recipe;

import com.creysys.guideBook.api.DrawableRecipe;
import com.creysys.guideBook.client.GuideBookGui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import org.lwjgl.opengl.GL11;

/**
 * Created by Creysys on 27 Mar 16.
 */
public class DrawableRecipeInfo extends DrawableRecipe {
    public ItemStack stack;
    public String localizationKey;

    public DrawableRecipeInfo(ItemStack stack, String localizationKey) {
        this.stack = stack.copy();
        this.localizationKey = localizationKey;
    }

    @Override
    public ItemStack[] getInput() {
        return new ItemStack[0];
    }

    @Override
    public ItemStack getOutput() {
        return stack;
    }

    @Override
    public void draw(GuideBookGui gui, int pageRecipeIndex) {
        drawItemStack(gui, stack, gui.left + 85, gui.top + 4, false);
    }

    @Override
    public void drawForeground(GuideBookGui gui, int pageRecipeIndex, int mouseX, int mouseY) {
        drawItemStackTooltip(gui, stack, gui.left + 85, gui.top, mouseX, mouseY);

        RenderHelper.disableStandardItemLighting();
        GL11.glPushMatrix();
        float scale = 0.75f;
        GL11.glScalef(scale, scale, 1);
        String[] lines = I18n.translateToLocal(localizationKey).split("\\\\n");
        for(int i = 0; i < lines.length; i++)
            gui.getFontRenderer().drawString(lines[i], gui.left + 94, gui.top + 50 + i * 10, 0xFF000000);
        GL11.glPopMatrix();
    }

    @Override
    public void mouseClick(GuideBookGui gui, int pageRecipeIndex, int mouseX, int mouseY, int mouseButton) {
        clickItemStack(gui, stack, gui.left + 85, gui.top, mouseX, mouseY, mouseButton);
    }
}
