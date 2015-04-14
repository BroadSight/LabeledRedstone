package com.BroadSight.labeledredstone;

import com.BroadSight.labeledredstone.handler.ConfigurationHandler;
import com.BroadSight.labeledredstone.init.*;
import com.BroadSight.labeledredstone.network.PacketManager;
import com.BroadSight.labeledredstone.proxy.CommonProxy;
import com.BroadSight.labeledredstone.reference.Reference;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class LabeledRedstone
{
    @Mod.Instance(Reference.MOD_ID)
    public static LabeledRedstone instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static SimpleNetworkWrapper network;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new CommonProxy());

        ModItems.init();
        ModBlocks.init();
        ModTileEntities.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        Models.init();
        Recipes.init();

        PacketManager.init();

        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
