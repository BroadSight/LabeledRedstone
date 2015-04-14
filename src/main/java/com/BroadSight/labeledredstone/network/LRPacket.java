package com.BroadSight.labeledredstone.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.BroadSight.labeledredstone.tileentity.TELabeledRedstone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

public class LRPacket
{
    public BlockPos pos;
    public NBTTagCompound nbtTag;

    public LRPacket()
    {
    }

    public LRPacket(TELabeledRedstone tile)
    {
        pos = tile.getPos();

        nbtTag = new NBTTagCompound();
        tile.writeToNBT(nbtTag);
    }

    public void encode(ByteArrayDataOutput output)
    {
        output.writeInt(pos.getX());
        output.writeInt(pos.getY());
        output.writeInt(pos.getZ());

        try
        {
            CompressedStreamTools.write(nbtTag, output);
        }
        catch (IOException e)
        {
            //empty
        }
    }

    public void decode(ByteArrayDataInput input)
    {
        pos = new BlockPos(input.readInt(), input.readInt(), input.readInt());

        try
        {
            nbtTag = CompressedStreamTools.read(input, NBTSizeTracker.INFINITE);
        }
        catch (IOException e)
        {
            //empty
        }
    }

    @SideOnly(Side.CLIENT)
    public void actionClient(World world, EntityPlayer player)
    {
        if (world == null)
            return;

        TELabeledRedstone tile = (TELabeledRedstone) world.getTileEntity(pos);

        if (tile == null)
            return;

        tile.readFromNBT(nbtTag);
    }

    public void actionServer(World world, EntityPlayerMP player)
    {
        if (world == null)
            return;

        TELabeledRedstone tile = (TELabeledRedstone) world.getTileEntity(pos);

        if (tile == null)
            return;

        tile.readFromNBT(nbtTag);

        PacketManager.sendToDimension(this, world.provider.getDimensionId());
    }
}
