package com.BroadSight.labeledredstone.init;

import com.BroadSight.labeledredstone.handler.ConfigurationHandler;
import com.BroadSight.labeledredstone.util.LogHelper;
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
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.labeled_button_wood, 1), new Object[]{Items.sign, Blocks.wooden_button});
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.labeled_button_stone, 1), new Object[]{Items.sign, Blocks.stone_button});

            LogHelper.info("Recipe Initialization Complete");
        }
        else
        {
            LogHelper.info("Recipes disabled.");
        }
    }
}
