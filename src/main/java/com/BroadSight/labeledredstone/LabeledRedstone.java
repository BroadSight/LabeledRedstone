package com.BroadSight.labeledredstone;

import com.BroadSight.labeledredstone.client.gui.GuiHandler;
import com.BroadSight.labeledredstone.init.ModBlocks;
import com.BroadSight.labeledredstone.init.ModItems;
import com.BroadSight.labeledredstone.init.ModTileEntities;
import com.BroadSight.labeledredstone.init.Recipes;
import com.BroadSight.labeledredstone.network.CPacketUpdateSign;
import com.BroadSight.labeledredstone.network.SPacketSignEditorOpen;
import com.BroadSight.labeledredstone.network.SPacketUpdateSign;
import com.BroadSight.labeledredstone.proxy.IProxy;
import com.BroadSight.labeledredstone.reference.Reference;
import com.BroadSight.labeledredstone.util.LogHelper;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class LabeledRedstone
{
    @Mod.Instance(Reference.MOD_ID)
    public static LabeledRedstone instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    public static Configuration config;

    public static SimpleNetworkWrapper channel;

    public static GuiHandler guiHandler;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        channel = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

        int messageNo = 0;
        channel.registerMessage(CPacketUpdateSign.Handler.class, CPacketUpdateSign.class, messageNo++, Side.SERVER);
        channel.registerMessage(SPacketUpdateSign.Handler.class, SPacketUpdateSign.class, messageNo++, Side.CLIENT);
        channel.registerMessage(SPacketSignEditorOpen.Handler.class, SPacketSignEditorOpen.class, messageNo++, Side.CLIENT);
        LogHelper.debug("Final message number: " + messageNo);

        guiHandler = new GuiHandler();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);

        ModItems.init();
        ModBlocks.init();
        ModTileEntities.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        Recipes.init();

        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
