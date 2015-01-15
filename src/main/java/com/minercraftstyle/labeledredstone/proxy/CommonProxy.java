package com.minercraftstyle.labeledredstone.proxy;

import com.minercraftstyle.labeledredstone.client.gui.GuiLabeledRedstone;
import com.minercraftstyle.labeledredstone.container.ContainerLabeledRedstone;
import com.minercraftstyle.labeledredstone.tileentity.TELabeledRedstone;
import com.minercraftstyle.labeledredstone.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CommonProxy implements IGuiHandler
{
    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        LogHelper.info("Proxy.getClientGuiElement");
        TELabeledRedstone te = (TELabeledRedstone) world.getTileEntity(new BlockPos(x,y,z));
        if (te != null)
        {
            return new GuiLabeledRedstone(te);
        }

        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        LogHelper.info("Proxy.getServerGuiElement");
        TELabeledRedstone te = (TELabeledRedstone) world.getTileEntity(new BlockPos(x, y, z));

        if (te != null)
        {
            return new ContainerLabeledRedstone(player, te);
        }

        return null;
    }

    public EntityPlayer getClientPlayer()
    {
        return null;
    }

    public World getClientWorld()
    {
        return null;
    }

    public boolean isClient()
    {
        return false;
    }

    public boolean isServer()
    {
        return true;
    }

    public void init()
    {

    }
}
