package com.minercraftstyle.labeledredstone.item;

import com.minercraftstyle.labeledredstone.creativetab.CreativeTabLR;
import com.minercraftstyle.labeledredstone.reference.Reference;
import net.minecraft.client.renderer.texture.IIconCreator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLR extends Item
{
    public ItemLR()
    {
        super();
        this.setCreativeTab(CreativeTabLR.LR_TAB);
    }

    public ItemLR(String name)
    {
        super();
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabLR.LR_TAB);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s:%s", Reference.MOD_ID.toLowerCase(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s:%s", Reference.MOD_ID.toLowerCase(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    public static void registerItems()
    {

    }
}
