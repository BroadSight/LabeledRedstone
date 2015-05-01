package com.BroadSight.labeledredstone.proxy;

import com.BroadSight.labeledredstone.client.gui.GuiLabeledRedstone;
import com.BroadSight.labeledredstone.client.renderer.TELabeledRedstoneRenderer;
import com.BroadSight.labeledredstone.init.Models;
import com.BroadSight.labeledredstone.tileentity.TELabeledRedstone;
import com.BroadSight.labeledredstone.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{
    public void init()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TELabeledRedstone.class, new TELabeledRedstoneRenderer());

        Models.init();
    }

    public EntityPlayer getClientPlayer()
    {
        return FMLClientHandler.instance().getClientPlayerEntity();
    }

    public World getClientWorld()
    {
        return Minecraft.getMinecraft().theWorld;
    }

    public boolean isClient()
    {
        return true;
    }

    public boolean isServer()
    {
        return false;
    }
}
