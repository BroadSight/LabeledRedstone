package com.minercraftstyle.labeledredstone.client.gui;

import com.minercraftstyle.labeledredstone.client.renderer.TELabeledRedstoneRenderer;
import com.minercraftstyle.labeledredstone.container.ContainerLabeledRedstone;
import com.minercraftstyle.labeledredstone.tileentity.TELabeledRedstone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te = world.getTileEntity(new BlockPos(x,y,z));
        if (te != null && te instanceof TELabeledRedstone)
        {
            return new GuiLabeledRedstone((TELabeledRedstone) te);
        }
        else
        {
            return null;
        }
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        if (te != null && te instanceof TELabeledRedstone)
        {
            TELabeledRedstone telr = (TELabeledRedstone) te;
            return new ContainerLabeledRedstone(player, telr);
        }
        else
        {
            return null;
        }
    }
}
