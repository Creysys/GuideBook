package com.creysys.guideBook.common.proxy;

import com.creysys.guideBook.common.PlayerEventHandler;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Creysys on 21 Mar 16.
 */
public class ProxyServer {
    public void registerKeyBinds() {};
    public void registerHandlers() {
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
    }
    public void registerModels(){}
}
