package com.creysys.guideBook.client;

import com.creysys.guideBook.GuideBookMod;
import com.creysys.guideBook.api.RecipeManager;
import com.creysys.guideBook.common.proxy.ProxyClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

/**
 * Created by Creysys on 25 Mar 16.
 */
public class ClientPlayerHandler {

    public boolean playerHasBook() {
        InventoryPlayer inventory = Minecraft.getMinecraft().thePlayer.inventory;
        if(inventory.player.isCreative() || inventory.player.getEntityData().getBoolean("doesntNeedGuideBook")) return true;
        if(inventory.offHandInventory[0] != null && inventory.offHandInventory[0].getItem() == GuideBookMod.guideBook) return true;

        for (ItemStack stack : inventory.mainInventory) {
            if(stack != null && stack.getItem() == GuideBookMod.guideBook) return true;
        }

        return false;
    }

    @SubscribeEvent
    public void preKeyboardInput(GuiScreenEvent.KeyboardInputEvent.Pre event) {
        if (event.getGui() instanceof GuiContainer && playerHasBook()) {
            GuiContainer gui = (GuiContainer) event.getGui();
            Slot slot = gui.getSlotUnderMouse();
            if (slot != null && slot.getHasStack()) {
                if (Keyboard.getEventKey() == ProxyClient.recipeKey.getKeyCode() && RecipeManager.hasRecipes(slot.getStack())) {
                    GuideBookGui.onOpenCmd = "recipe";
                    GuideBookGui.onOpenArg = slot.getStack();
                    Minecraft.getMinecraft().thePlayer.openGui(GuideBookMod.instance, 0, Minecraft.getMinecraft().theWorld, 0, 0, 0);
                }
                else if(Keyboard.getEventKey() == ProxyClient.usageKey.getKeyCode() && RecipeManager.hasUsages(slot.getStack())) {
                    GuideBookGui.onOpenCmd = "usage";
                    GuideBookGui.onOpenArg = slot.getStack();
                    Minecraft.getMinecraft().thePlayer.openGui(GuideBookMod.instance, 0, Minecraft.getMinecraft().theWorld, 0, 0, 0);
                }
            }
        }
    }
}
