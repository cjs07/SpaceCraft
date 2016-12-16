package com.deepwelldevelopment.spacecraft.common.lib.network.playerdata;

import com.deepwelldevelopment.spacecraft.common.lib.research.ResearchManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;

public class PacketSyncResearch implements IMessage, IMessageHandler<PacketSyncResearch, IMessage> {

    protected String name;
    protected ArrayList<String> data = new ArrayList<String>();
    protected HashMap<String, Byte> flags = new HashMap<String, Byte>();

    public PacketSyncResearch(){}

    public PacketSyncResearch(EntityPlayer player) {
        this.name = player.getName();
        this.data = ResearchManager.getResearchForPlayer(this.name);
        this.flags = ResearchManager.getResearchFlagsForPlayer(this.name);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, name);
        if (data != null && data.size() > 0) {
            buf.writeShort(data.size());
            for (String re : data) {
                if (re != null) {
                    ByteBufUtils.writeUTF8String(buf, re);
                    buf.writeByte(flags.get(re) != null ? this.flags.get(re) : 0);
                }
            }
        } else {
            buf.writeShort(0);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.name = ByteBufUtils.readUTF8String(buf);
        int size = buf.readShort();
        data = new ArrayList<String>();
        for (int i = 0; i < size; i++) {
            String s = ByteBufUtils.readUTF8String(buf);
            data.add(s);
            flags.put(s, buf.readByte());
        }
    }

    @Override
    @SideOnly(value= Side.CLIENT)
    public IMessage onMessage(PacketSyncResearch message, MessageContext context) {
        for (String key : message.data) {
            ResearchManager.completeResearchUnsaved(message.name, key, message.flags.get(key));
        }
        //TODO: ADD RESEARCH TO RESEARCH TREE TRACKER
        //TODO: ALSO IMPLEMENT RESEARCH TREE TRACKER
        return null;
    }
}
