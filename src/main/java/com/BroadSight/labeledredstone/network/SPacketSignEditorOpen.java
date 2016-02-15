package com.BroadSight.labeledredstone.network;

import com.BroadSight.labeledredstone.tileentity.TELabeledRedstone;
import com.BroadSight.labeledredstone.util.LogHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SPacketSignEditorOpen implements IMessage
{
    private World world;
    private BlockPos blockPos;

    public SPacketSignEditorOpen() {}

    public SPacketSignEditorOpen(World worldIn, BlockPos posIn)
    {
        this.world = worldIn;
        this.blockPos = posIn;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        blockPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(blockPos.getX());
        buf.writeInt(blockPos.getY());
        buf.writeInt(blockPos.getZ());
    }

    public static class Handler implements IMessageHandler<SPacketSignEditorOpen, IMessage>
    {
        @Override
        public IMessage onMessage(SPacketSignEditorOpen message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntity tile = message.world.getTileEntity(message.blockPos);

                if (!(tile instanceof TELabeledRedstone))
                {
                    tile = new TELabeledRedstone();
                    tile.setWorldObj(message.world);
                    tile.setPos(message.blockPos);
                }

                // Open Sign editing gui
                LogHelper.debug("Open LR Sign gui");
            });

            return null;
        }
    }
}
