package com.minercraftstyle.labeledredstone.network.message;

import com.minercraftstyle.labeledredstone.tileentity.TELabeledRedstone;
import com.minercraftstyle.labeledredstone.util.LogHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.server.FMLServerHandler;

public class LabeledRedstoneMessage implements IMessage
{
    NBTTagCompound data;
    private BlockPos pos;

    public LabeledRedstoneMessage()
    {

    }

    public LabeledRedstoneMessage(TELabeledRedstone tile)
    {
        LogHelper.info("Message constructor");
        data = new NBTTagCompound();
        tile.writeToNBT(data);

        pos = tile.getPos();
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        LogHelper.info("Message.fromBytes");
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        LogHelper.info("Message.toBytes");
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        ByteBufUtils.writeTag(buf, data);
    }

    public static class ClientHandler implements IMessageHandler<LabeledRedstoneMessage, IMessage>
    {
        @Override
        public IMessage onMessage(LabeledRedstoneMessage message, MessageContext ctx)
        {
            LogHelper.info("Message.Client.onMessage");
            try
            {
                LogHelper.info("   try get TE");
                TELabeledRedstone te = (TELabeledRedstone) FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.pos);

                if (te != null)
                {
                    LogHelper.info("   te.readFromNBT");
                    te.readFromNBT(message.data);
                }
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }

            return null;
        }
    }

    public static class ServerHandler implements IMessageHandler<LabeledRedstoneMessage, IMessage>
    {
        @Override
        public IMessage onMessage(LabeledRedstoneMessage message, MessageContext ctx)
        {
            try
            {
                TELabeledRedstone te = (TELabeledRedstone) FMLServerHandler.instance().getServer().getEntityWorld().getTileEntity(message.pos);

                if (te != null)
                {
                    te.readFromNBT(message.data);
                    te.markDirty();
                }
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }

            return null;
        }
    }
}
