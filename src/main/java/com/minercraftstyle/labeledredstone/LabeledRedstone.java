package com.minercraftstyle.labeledredstone;

import com.minercraftstyle.labeledredstone.handler.ConfigurationHandler;
import com.minercraftstyle.labeledredstone.init.*;
import com.minercraftstyle.labeledredstone.network.message.LabeledRedstoneMessage;
import com.minercraftstyle.labeledredstone.proxy.CommonProxy;
import com.minercraftstyle.labeledredstone.reference.Reference;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

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

        network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID + ":Channel");
        network.registerMessage(LabeledRedstoneMessage.ClientHandler.class, LabeledRedstoneMessage.class, 0, Side.CLIENT);
        network.registerMessage(LabeledRedstoneMessage.ServerHandler.class, LabeledRedstoneMessage.class, 1, Side.SERVER);

        ModItems.init();
        ModBlocks.init();
        ModTileEntities.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        Models.init();
        Recipes.init();

        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
