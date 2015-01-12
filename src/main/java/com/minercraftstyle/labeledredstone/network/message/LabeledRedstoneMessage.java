package com.minercraftstyle.labeledredstone.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LabeledRedstoneMessage implements IMessage
{
    private BlockPos pos;
    private IChatComponent[] lines;

    public LabeledRedstoneMessage()
    {

    }

    @SideOnly(Side.CLIENT)
    public LabeledRedstoneMessage(BlockPos pos, IChatComponent[] lines)
    {
        this.pos = pos;
        this.lines = new IChatComponent[] {lines[0], lines[1], lines[2], lines[3]};
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        lines = new IChatComponent[] {new ChatComponentText(ByteBufUtils.readUTF8String(buf)),
                new ChatComponentText(ByteBufUtils.readUTF8String(buf)),
                new ChatComponentText(ByteBufUtils.readUTF8String(buf)),
                new ChatComponentText(ByteBufUtils.readUTF8String(buf))};
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        ByteBufUtils.writeUTF8String(buf, lines[0].getUnformattedText());
        ByteBufUtils.writeUTF8String(buf, lines[1].getUnformattedText());
        ByteBufUtils.writeUTF8String(buf, lines[2].getUnformattedText());
        ByteBufUtils.writeUTF8String(buf, lines[3].getUnformattedText());
    }

    public static class ClientHandler implements IMessageHandler<LabeledRedstoneMessage, IMessage>
    {

        @Override
        public IMessage onMessage(LabeledRedstoneMessage message, MessageContext ctx)
        {
            return null;
        }
    }

    public static class ServerHandler implements IMessageHandler<LabeledRedstoneMessage, IMessage>
    {
        @Override
        public IMessage onMessage(LabeledRedstoneMessage message, MessageContext ctx)
        {
            return null;
        }
    }
}
