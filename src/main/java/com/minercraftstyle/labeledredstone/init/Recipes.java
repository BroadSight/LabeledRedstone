package com.minercraftstyle.labeledredstone.init;

import com.minercraftstyle.labeledredstone.handler.ConfigurationHandler;
import com.minercraftstyle.labeledredstone.util.LogHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Recipes
{
    public static void init()
    {
        if (ConfigurationHandler.craftable)
        {
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.labeled_lever, 1), new Object[]{Items.sign, Blocks.lever});

            LogHelper.info("Recipe Initialization Complete");
        }
        else
        {
            LogHelper.info("Recipes disabled.");
        }
    }
}
