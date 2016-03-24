package com.creysys.guideBook.api;

import com.creysys.guideBook.client.GuideBookGui;
import net.minecraft.item.ItemStack;

/**
 * Created by Creysys on 21 Mar 16.
 */
public abstract class DrawableRecipe {
    public abstract ItemStack getOutput();
    public abstract void draw(GuideBookGui gui, int pageRecipeIndex);
    public abstract void drawForeground(GuideBookGui gui, int pageRecipeIndex, int mouseX, int mouseY);
    public abstract void mouseClick(GuideBookGui gui, int pageRecipeIndex, int mouseX, int mouseY, int mouseButton);
    public void update() {}
}
