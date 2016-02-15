package com.BroadSight.labeledredstone.network;

import com.BroadSight.labeledredstone.tileentity.TELabeledRedstone;
import com.BroadSight.labeledredstone.util.LogHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketUpdateSign implements IMessage
{
    private int dimension;
    private int entityID;
    private BlockPos blockPos;
    private IChatComponent[] lines;
    private int rotation;

    public CPacketUpdateSign() {}

    public CPacketUpdateSign(EntityPlayer player, BlockPos blockPosIn, IChatComponent[] linesIn, int rotationIn)
    {
        this.dimension = player.dimension;
        this.entityID = player.getEntityId();
        this.blockPos = blockPosIn;
        this.lines = new IChatComponent[] {linesIn[0], linesIn[1], linesIn[2], linesIn[3]};
        this.rotation = rotationIn;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        dimension = buf.readInt();

        entityID = buf.readInt();

        blockPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());

        rotation = buf.readInt();

        for (int i = 0; i < 4; i++)
        {
            byte[] array = {};
            buf.readBytes(array);
            lines[i] = IChatComponent.Serializer.jsonToComponent(new String(array));
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(dimension);

        buf.writeInt(entityID);

        buf.writeInt(blockPos.getX());
        buf.writeInt(blockPos.getY());
        buf.writeInt(blockPos.getZ());

        buf.writeInt(rotation);

        for (int i = 0; i < 4; i++)
        {
            buf.writeBytes(IChatComponent.Serializer.componentToJson(lines[i]).getBytes());
        }
    }

    public static class Handler implements IMessageHandler<CPacketUpdateSign, IMessage>
    {
        @Override
        public IMessage onMessage(CPacketUpdateSign message, MessageContext ctx)
        {
            WorldServer server = MinecraftServer.getServer().worldServerForDimension(message.dimension);
            EntityPlayer player = (EntityPlayer) server.getEntityByID(message.entityID);

            server.addScheduledTask(() -> {
                if (server.isBlockLoaded(message.blockPos))
                {
                    TileEntity tile = server.getTileEntity(message.blockPos);

                    if (tile instanceof TELabeledRedstone)
                    {
                        TELabeledRedstone teLR = (TELabeledRedstone)tile;

                        if (!teLR.getIsEditable() || teLR.getPlayer() != player)
                        {
                            LogHelper.warn("Player " + player.getName() + " just tried to change a non-editable LR sign");
                            return;
                        }

                        IChatComponent [] lines = message.lines;

                        for (int i = 0; i < lines.length; i++)
                        {
                            teLR.signText[i] = new ChatComponentText(EnumChatFormatting.getTextWithoutFormattingCodes(lines[i].getUnformattedText()));
                        }

                        teLR.markDirty();
                        server.markBlockForUpdate(message.blockPos);
                    }
                }
            });

            return null;
        }
    }
}
