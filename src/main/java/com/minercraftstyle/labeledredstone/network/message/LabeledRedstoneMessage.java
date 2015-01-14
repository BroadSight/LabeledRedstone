package com.minercraftstyle.labeledredstone.network.message;

import com.minercraftstyle.labeledredstone.tileentity.TELabeledRedstone;
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

public class LabeledRedstoneMessage implements IMessage
{
    NBTTagCompound data;
    private BlockPos pos;

    public LabeledRedstoneMessage()
    {

    }

    public LabeledRedstoneMessage(TELabeledRedstone tile)
    {
        data = new NBTTagCompound();
        tile.writeToNBT(data);

        pos = tile.getPos();
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
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
            try
            {
                TELabeledRedstone te = (TELabeledRedstone) FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.pos);

                if (te != null)
                {
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
            return null;
        }
    }
}
