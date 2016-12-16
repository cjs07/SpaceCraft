package com.deepwelldevelopment.spacecraft.common.lib.network;

import com.deepwelldevelopment.spacecraft.common.SpaceCraft;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class SpaceCraftPacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(SpaceCraft.modId.toLowerCase());
}
