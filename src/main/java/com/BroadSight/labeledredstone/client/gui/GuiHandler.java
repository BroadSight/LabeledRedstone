package com.BroadSight.labeledredstone.client.gui;

import com.BroadSight.labeledredstone.container.ContainerLabeledRedstone;
import com.BroadSight.labeledredstone.tileentity.TELabeledRedstone;
import com.BroadSight.labeledredstone.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiHandler implements IGuiHandler
{
    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        LogHelper.info("Proxy.getClientGuiElement");
        TELabeledRedstone te = (TELabeledRedstone) world.getTileEntity(new BlockPos(x,y,z));
        LogHelper.info("   " + te + " @" + x + "," + y + "," + z);

        if (te != null)
        {
            LogHelper.info("   return Gui");
            return new GuiLabeledRedstone(te);
        }

        LogHelper.info("   return null");
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        LogHelper.info("Proxy.getServerGuiElement");
        TELabeledRedstone te = (TELabeledRedstone) world.getTileEntity(new BlockPos(x, y, z));

        if (te != null)
        {
            LogHelper.info("   return Container");
            return new ContainerLabeledRedstone(player, te);
        }

        LogHelper.info("   return null");
        return null;
    }
}
