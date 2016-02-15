package com.BroadSight.labeledredstone.item;

import com.BroadSight.labeledredstone.LabeledRedstone;
import com.BroadSight.labeledredstone.block.BlockLabeledButton;
import com.BroadSight.labeledredstone.init.ModBlocks;
import com.BroadSight.labeledredstone.tileentity.TELabeledRedstone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemLabeledButtonWood extends ItemLR
{
    public ItemLabeledButtonWood()
    {
        super("labeledButtonWood");
        this.setMaxStackSize(64);
        this.setContainerItem(Items.sign);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.getBlockState(pos).getBlock().getMaterial().isSolid())
        {
            return false;
        }
        else
        {
            pos = pos.offset(side);

            if (!playerIn.canPlayerEdit(pos, side, stack))
            {
                return false;
            }else if (!ModBlocks.block_labeled_button_wood.canPlaceBlockAt(worldIn, pos))
            {
                return false;
            }
            else if (worldIn.isRemote)
            {
                return false;
            }
            else
            {
                worldIn.setBlockState(pos, ModBlocks.block_labeled_button_wood.getDefaultState().withProperty(BlockLabeledButton.FACING, side), 3);

                --stack.stackSize;
                createTileEntity(worldIn, pos, playerIn, stack);

                return true;
            }
        }
    }
}
