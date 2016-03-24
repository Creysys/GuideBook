package com.creysys.guideBook.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * Created by Creysys on 20 Mar 16.
 */
public class GuiBookContainer extends Container {
    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return true;
    }
}
