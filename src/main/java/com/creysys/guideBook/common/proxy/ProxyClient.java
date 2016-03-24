package com.creysys.guideBook.common.proxy;

import com.creysys.guideBook.GuideBookMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

/**
 * Created by Creysys on 21 Mar 16.
 */
public class ProxyClient extends ProxyServer {
    @Override
    public void registerModels() {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(GuideBookMod.guideBook, 0, new ModelResourceLocation("guidebook:guideBook", "inventory"));
    }
}
