package com.minercraftstyle.labeledredstone.tileentity;

import com.google.gson.JsonParseException;
import com.minercraftstyle.labeledredstone.LabeledRedstone;
import com.minercraftstyle.labeledredstone.network.message.LabeledRedstoneMessage;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TELabeledRedstone extends TileEntity
{
    public final IChatComponent[] signText = new IChatComponent[] {new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText("")};
    public int rotation = 0;
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

        compound.setInteger("Rotation", this.rotation);

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
                return TELabeledRedstone.this.pos;
            }
            public Vec3 getPositionVector()
            {
                return new Vec3((double)TELabeledRedstone.this.pos.getX() + 0.5D, (double)TELabeledRedstone.this.pos.getY() + 0.5D, (double)TELabeledRedstone.this.pos.getZ() + 0.5D);
            }
            public World getEntityWorld()
            {
                return TELabeledRedstone.this.worldObj;
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

        this.rotation = compound.getInteger("Rotation");

        this.commandResultStats.func_179668_a(compound);
    }

    public Packet getDescriptionPacket()
    {
        return LabeledRedstone.network.getPacketFrom(new LabeledRedstoneMessage(this));
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

    public boolean guiEventHandler(final EntityPlayer playerIn)
    {
        ICommandSender icommandsender = new ICommandSender()
        {
            private static final String __OBFID = "CL_00002038";
            public String getName()
            {
                return playerIn.getName();
            }
            public IChatComponent getDisplayName()
            {
                return playerIn.getDisplayName();
            }
            public void addChatMessage(IChatComponent component) {}
            public boolean canUseCommand(int permLevel, String commandName)
            {
                return true;
            }
            public BlockPos getPosition()
            {
                return TELabeledRedstone.this.pos;
            }
            public Vec3 getPositionVector()
            {
                return new Vec3((double)TELabeledRedstone.this.pos.getX() + 0.5D, (double)TELabeledRedstone.this.pos.getY() + 0.5D, (double)TELabeledRedstone.this.pos.getZ() + 0.5D);
            }
            public World getEntityWorld()
            {
                return playerIn.getEntityWorld();
            }
            public Entity getCommandSenderEntity()
            {
                return playerIn;
            }
            public boolean sendCommandFeedback()
            {
                return false;
            }
            public void setCommandStat(CommandResultStats.Type type, int amount)
            {
                TELabeledRedstone.this.commandResultStats.func_179672_a(this, type, amount);
            }
        };

        for (int i = 0; i < this.signText.length; ++i)
        {
            ChatStyle chatstyle = this.signText[i] == null ? null : this.signText[i].getChatStyle();

            if (chatstyle != null && chatstyle.getChatClickEvent() != null)
            {
                ClickEvent clickevent = chatstyle.getChatClickEvent();

                if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND)
                {
                    MinecraftServer.getServer().getCommandManager().executeCommand(icommandsender, clickevent.getValue());
                }
            }
        }

        this.rotation = MathHelper.floor_double((double)((playerIn.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;

        return true;
    }

    public CommandResultStats getCommandResultStats()
    {
        return this.commandResultStats;
    }
}
