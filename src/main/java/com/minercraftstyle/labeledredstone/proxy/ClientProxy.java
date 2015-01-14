package com.minercraftstyle.labeledredstone.proxy;

import com.minercraftstyle.labeledredstone.client.renderer.TELabeledRedstoneRenderer;
import com.minercraftstyle.labeledredstone.tileentity.TELabeledRedstone;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{
    public void registerTileEntitySpecialRenderer()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TELabeledRedstone.class, new TELabeledRedstoneRenderer());
    }
}
