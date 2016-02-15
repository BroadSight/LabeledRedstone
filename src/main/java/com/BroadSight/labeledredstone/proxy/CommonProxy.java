package com.BroadSight.labeledredstone.proxy;

import com.BroadSight.labeledredstone.client.gui.GuiLabeledRedstone;
import com.BroadSight.labeledredstone.container.ContainerLabeledRedstone;
import com.BroadSight.labeledredstone.tileentity.TELabeledRedstone;
import com.BroadSight.labeledredstone.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CommonProxy implements IProxy
{
    public void init()
    {

    }

    public boolean isClient()
    {
        return false;
    }
}
