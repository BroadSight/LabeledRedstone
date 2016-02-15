package com.BroadSight.labeledredstone.item;

import com.BroadSight.labeledredstone.LabeledRedstone;
import com.BroadSight.labeledredstone.creativetab.CreativeTabLR;
import com.BroadSight.labeledredstone.network.SPacketSignEditorOpen;
import com.BroadSight.labeledredstone.reference.Reference;
import com.BroadSight.labeledredstone.tileentity.TELabeledRedstone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

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

    protected void createTileEntity(World worldIn, BlockPos pos, EntityPlayer playerIn, ItemStack stack)
    {
        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if (tileEntity instanceof TELabeledRedstone && !ItemBlock.setTileEntityNBT(worldIn, playerIn, pos, stack))
        {
            if (LabeledRedstone.proxy.isClient())
            {
                playerIn.openGui(LabeledRedstone.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
            else
            {
                ((TELabeledRedstone)tileEntity).setPlayer(playerIn);
                LabeledRedstone.channel.sendTo(new SPacketSignEditorOpen(playerIn.worldObj, pos), (EntityPlayerMP)playerIn);
            }
        }
    }
}
