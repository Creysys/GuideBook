package com.creysys.guideBook.common;

import com.creysys.guideBook.GuideBookMod;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * Created by Creysys on 21 Mar 16.
 */
public class PlayerEventHandler {
    @SubscribeEvent
    public void firstJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.player.worldObj.isRemote) return;
        if(!event.player.getEntityData().getBoolean("joinedBefore")) {
            event.player.getEntityData().setBoolean("joinedBefore", true);
            event.player.inventory.addItemStackToInventory(new ItemStack(GuideBookMod.guideBook));
        }
    }
}
