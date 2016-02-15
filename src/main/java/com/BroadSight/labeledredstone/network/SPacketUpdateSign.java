package com.BroadSight.labeledredstone.network;

import com.BroadSight.labeledredstone.tileentity.TELabeledRedstone;
import com.BroadSight.labeledredstone.util.LogHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

public class SPacketUpdateSign implements IMessage
{
    private World world;
    private BlockPos blockPos;
    private IChatComponent[] lines;
    private int rotation;

    public SPacketUpdateSign() {}

    public SPacketUpdateSign(World worldIn, BlockPos blockPosIn, IChatComponent[] linesIn, int rotationIn)
    {
        this.world = worldIn;
        this.blockPos = blockPosIn;
        this.lines = new IChatComponent[] {linesIn[0], linesIn[1], linesIn[2], linesIn[3]};
        this.rotation = rotationIn;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        PacketBuffer buffer = new PacketBuffer(buf);

        blockPos = buffer.readBlockPos();

        rotation = buffer.readInt();

        lines = new IChatComponent[4];

        for (int i = 0; i < 4; i++)
        {
            try
            {
                lines[i] = buffer.readChatComponent();
            }
            catch (IOException e)
            {
                LogHelper.error(e);
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        PacketBuffer buffer = new PacketBuffer(buf);

        buffer.writeBlockPos(blockPos);

        buf.writeInt(rotation);

        for (int i = 0; i < 4; i++)
        {
            try
            {
                buffer.writeChatComponent(this.lines[i]);
            }
            catch (IOException e)
            {
                LogHelper.error(e);
            }
        }
    }

    public static class Handler implements IMessageHandler<SPacketUpdateSign, IMessage>
    {
        @Override
        public IMessage onMessage(SPacketUpdateSign message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                boolean flag = false;

                if (message.world.isBlockLoaded(message.blockPos))
                {
                    TileEntity tile = message.world.getTileEntity(message.blockPos);

                    if (tile instanceof TELabeledRedstone)
                    {
                        TELabeledRedstone teLR = (TELabeledRedstone)tile;

                        if (teLR.getIsEditable())
                        {
                            System.arraycopy(message.lines, 0, teLR.signText, 0, 4);
                            teLR.markDirty();
                        }

                        flag = true;
                    }
                }

                if (!flag)
                {
                    LogHelper.warn("Unable to located labeled redstone at " + message.blockPos.getX() + " " + message.blockPos.getY() + " " + message.blockPos.getZ());
                }
            });

            return null;
        }
    }
}
