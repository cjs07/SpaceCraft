package com.deepwelldevelopment.spacecraft.common.lib.network.playerdata;

import com.deepwelldevelopment.spacecraft.common.SpaceCraft;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.concurrent.ConcurrentHashMap;

public class PacketResearchComplete implements IMessage, IMessageHandler<PacketResearchComplete, IMessage> {

    private static ConcurrentHashMap<String, Long> spam = new ConcurrentHashMap<String, Long>();
    byte flags;
    private String key;

    public PacketResearchComplete() {
    }

    public PacketResearchComplete(String key, byte flags) {
        this.key = key;
        this.flags = flags;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, key);
        buf.writeByte(flags);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        key = ByteBufUtils.readUTF8String(buf);
        flags = buf.readByte();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(final PacketResearchComplete message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(new Runnable() {
            @Override
            public void run() {
                PacketResearchComplete.this.processMessage(message);
            }
        });
        return null;
    }

    @SideOnly(Side.CLIENT)
    void processMessage(PacketResearchComplete message) {
        if (message.key != null && message.key.length() > 0) {
            SpaceCraft.proxy.getResearchManager().completeResearch(Minecraft.getMinecraft().thePlayer, message.key, message.flags);
            
        }
    }
}
