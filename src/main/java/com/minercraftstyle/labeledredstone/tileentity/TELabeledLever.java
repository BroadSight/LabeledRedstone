package com.minercraftstyle.labeledredstone.tileentity;

import com.google.gson.JsonParseException;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TELabeledLever extends TileEntity
{
    public final IChatComponent[] signText = new IChatComponent[] {new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText("")};
    public final int rotation = 0;
    public int lineBeingEdited = -1;
    private boolean isEditable = true;
    private EntityPlayer player;
    private final CommandResultStats commandResultStats = new CommandResultStats();

    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        for (int i = 0; i < 4; ++i)
        {
            String s = IChatComponent.Serializer.componentToJson(this.signText[i]);
            compound.setString("Text" + (i + 1), s);
        }

        this.commandResultStats.func_179670_b(compound);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        this.isEditable = false;
        super.readFromNBT(compound);
        ICommandSender iCommandSender = new ICommandSender()
        {
            public String getName()
            {
                return "LabeledLever";
            }
            public IChatComponent getDisplayName()
            {
                return new ChatComponentText(this.getName());
            }
            public void addChatMessage(IChatComponent message) {}
            public boolean canUseCommand(int permLevel, String command)
            {
                return true;
            }
            public BlockPos getPosition()
            {
                return TELabeledLever.this.pos;
            }
            public Vec3 getPositionVector()
            {
                return new Vec3((double)TELabeledLever.this.pos.getX() + 0.5D, (double)TELabeledLever.this.pos.getY() + 0.5D, (double)TELabeledLever.this.pos.getZ() + 0.5D);
            }
            public World getEntityWorld()
            {
                return TELabeledLever.this.worldObj;
            }
            public Entity getCommandSenderEntity()
            {
                return null;
            }
            public boolean sendCommandFeedback()
            {
                return false;
            }
            public void setCommandStat(CommandResultStats.Type type, int amount) {}
        };

        for (int i = 0; i < 4; ++i)
        {
            String s = compound.getString("Text" + (i + 1));

            try
            {
                IChatComponent iChatComponent = IChatComponent.Serializer.jsonToComponent(s);

                try
                {
                    this.signText[i] = ChatComponentProcessor.func_179985_a(iCommandSender, iChatComponent, (Entity)null);
                }
                catch (CommandException commandException)
                {
                    this.signText[i] = iChatComponent;
                }
            }
            catch (JsonParseException jsonParseException)
            {
                this.signText[i] = new ChatComponentText(s);
            }
        }

        this.commandResultStats.func_179668_a(compound);
    }

    public Packet getDescriptionPacket()
    {
        IChatComponent[] aichatcomponent = new IChatComponent[4];
        System.arraycopy(this.signText, 0, aichatcomponent, 0, 4);
        //return new PacketUpdateLLever(this.worldObj, this.pos, aichatcomponent);
        return (Packet)null;
    }

    public boolean getIsEditable()
    {
        return this.isEditable;
    }

    @SideOnly(Side.CLIENT)
    public void setEditable(boolean editable)
    {
        this.isEditable = editable;

        if (!editable)
        {
            this.player = null;
        }
    }

    public void setPlayer(EntityPlayer player)
    {
        this.player = player;
    }

    public EntityPlayer getPlayer()
    {
        return this.player;
    }

    public int getRotation()
    {
        return rotation;
    }

    public boolean guiEventHandler(EntityPlayer playerIn)
    {
        //stuff i dont get right now...
        //net.minecraft.tileentity.TileEntitySign.func_174882_b(EntityPlayer player)

        return true;
    }

    public CommandResultStats getCommandResultStats()
    {
        return this.commandResultStats;
    }
}
