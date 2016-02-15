package com.BroadSight.labeledredstone.item;

import com.BroadSight.labeledredstone.LabeledRedstone;
import com.BroadSight.labeledredstone.block.BlockLabeledLever;
import com.BroadSight.labeledredstone.init.ModBlocks;
import com.BroadSight.labeledredstone.network.SPacketSignEditorOpen;
import com.BroadSight.labeledredstone.tileentity.TELabeledRedstone;
import com.BroadSight.labeledredstone.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ItemLabeledLever extends ItemLR
{
    public ItemLabeledLever()
    {
        super("labeledLever");
        this.maxStackSize = 16;
        this.setContainerItem(Items.sign);
    }

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
            }
            else if (!ModBlocks.block_labeled_lever.canPlaceBlockAt(worldIn, pos))
            {
                return false;
            }
            else if (worldIn.isRemote)
            {
                return true;
            }
            else
            {
                worldIn.setBlockState(pos, ModBlocks.block_labeled_lever.getDefaultState()
                        .withProperty(BlockLabeledLever.FACING_PROP, BlockLabeledLever.EnumOrientation.getState(side, playerIn.getHorizontalFacing(), playerIn.isSneaking()))
                        , 3);

                --stack.stackSize;
                createTileEntity(worldIn, pos, playerIn, stack);

                return true;
            }
        }
    }
}
