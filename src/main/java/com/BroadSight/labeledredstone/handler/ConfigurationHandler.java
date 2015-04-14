package com.BroadSight.labeledredstone.handler;

import com.BroadSight.labeledredstone.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigurationHandler
{
    public static Configuration configuration;
    
    //Configuration values:
    public static boolean craftable = true;

    public static void init(File configFile)
    {
        //create the config object from the given config file
        if (configuration == null)
        {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equalsIgnoreCase(Reference.MOD_ID))
        {
            loadConfiguration();
        }
    }

    private static void loadConfiguration()
    {
        craftable = configuration.getBoolean("Craftable", Configuration.CATEGORY_GENERAL, true, "Recipes are enabled.");

        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }
}
