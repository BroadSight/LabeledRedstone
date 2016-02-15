package com.BroadSight.labeledredstone.tileentity;

import com.BroadSight.labeledredstone.LabeledRedstone;
import com.BroadSight.labeledredstone.network.SPacketUpdateSign;
import com.BroadSight.labeledredstone.util.LogHelper;
import com.google.gson.JsonParseException;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S33PacketUpdateSign;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TELabeledRedstone extends TileEntity
{
    public final IChatComponent[] signText = new IChatComponent[] {new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText("")};

    public int rotation = 0;
    public int lineBeingEdited = -1;
    private final CommandResultStats commandResultStats = new CommandResultStats();
    private boolean isEditable = true;
    private EntityPlayer player;

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        for (int i = 0; i < 4; ++i)
        {
            String s = IChatComponent.Serializer.componentToJson(this.signText[i]);
            compound.setString("Text" + (i + 1), s);
        }

        compound.setInteger("Rotation", this.rotation);

        this.commandResultStats.writeStatsToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        setIsEditable(false);
        super.readFromNBT(compound);

        ICommandSender icommandsender = new ICommandSender()
        {
            /**
             * Get the name of this object. For players this returns their username
             */
            public String getName()
            {
                return "Sign";
            }
            /**
             * Get the formatted ChatComponent that will be used for the sender's username in chat
             */
            public IChatComponent getDisplayName()
            {
                return new ChatComponentText(this.getName());
            }
            /**
             * Send a chat message to the CommandSender
             */
            public void addChatMessage(IChatComponent component)
            {
            }
            /**
             * Returns {@code true} if the CommandSender is allowed to execute the command, {@code false} if not
             */
            public boolean canCommandSenderUseCommand(int permLevel, String commandName)
            {
                return permLevel <= 2; //Forge: Fixes  MC-75630 - Exploit with signs and command blocks
            }
            /**
             * Get the position in the world. <b>{@code null} is not allowed!</b> If you are not an entity in the world,
             * return the coordinates 0, 0, 0
             */
            public BlockPos getPosition()
            {
                return TELabeledRedstone.this.pos;
            }
            /**
             * Get the position vector. <b>{@code null} is not allowed!</b> If you are not an entity in the world,
             * return 0.0D, 0.0D, 0.0D
             */
            public Vec3 getPositionVector()
            {
                return new Vec3((double)TELabeledRedstone.this.pos.getX() + 0.5D, (double)TELabeledRedstone.this.pos.getY() + 0.5D, (double)TELabeledRedstone.this.pos.getZ() + 0.5D);
            }
            /**
             * Get the world, if available. <b>{@code null} is not allowed!</b> If you are not an entity in the world,
             * return the overworld
             */
            public World getEntityWorld()
            {
                return TELabeledRedstone.this.worldObj;
            }
            /**
             * Returns the entity associated with the command sender. MAY BE NULL!
             */
            public Entity getCommandSenderEntity()
            {
                return null;
            }
            /**
             * Returns true if the command sender should be sent feedback about executed commands
             */
            public boolean sendCommandFeedback()
            {
                return false;
            }
            public void setCommandStat(CommandResultStats.Type type, int amount)
            {
            }
        };

        for (int i = 0; i < 4; ++i)
        {
            String s = compound.getString("Text" + (i + 1));

            try
            {
                IChatComponent ichatcomponent = IChatComponent.Serializer.jsonToComponent(s);

                try
                {
                    this.signText[i] = ChatComponentProcessor.processComponent(icommandsender, ichatcomponent, (Entity)null);
                }
                catch (CommandException var7)
                {
                    this.signText[i] = ichatcomponent;
                }
            }
            catch (JsonParseException var8)
            {
                this.signText[i] = new ChatComponentText(s);
            }
        }

        this.rotation = compound.getInteger("Rotation");

        this.commandResultStats.readStatsFromNBT(compound);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        IChatComponent[] aichatcomponent = new IChatComponent[4];
        System.arraycopy(this.signText, 0, aichatcomponent, 0, 4);
        return LabeledRedstone.channel.getPacketFrom(new SPacketUpdateSign(this.worldObj, this.pos, aichatcomponent, this.rotation));
    }

    public boolean executeCommand(final EntityPlayer playerIn)
    {
        ICommandSender icommandsender = new ICommandSender()
        {
            /**
             * Get the name of this object. For players this returns their username
             */
            public String getName()
            {
                return playerIn.getName();
            }
            /**
             * Get the formatted ChatComponent that will be used for the sender's username in chat
             */
            public IChatComponent getDisplayName()
            {
                return playerIn.getDisplayName();
            }
            /**
             * Send a chat message to the CommandSender
             */
            public void addChatMessage(IChatComponent component)
            {
            }
            /**
             * Returns {@code true} if the CommandSender is allowed to execute the command, {@code false} if not
             */
            public boolean canCommandSenderUseCommand(int permLevel, String commandName)
            {
                return permLevel <= 2;
            }
            /**
             * Get the position in the world. <b>{@code null} is not allowed!</b> If you are not an entity in the world,
             * return the coordinates 0, 0, 0
             */
            public BlockPos getPosition()
            {
                return TELabeledRedstone.this.pos;
            }
            /**
             * Get the position vector. <b>{@code null} is not allowed!</b> If you are not an entity in the world,
             * return 0.0D, 0.0D, 0.0D
             */
            public Vec3 getPositionVector()
            {
                return new Vec3((double)TELabeledRedstone.this.pos.getX() + 0.5D, (double)TELabeledRedstone.this.pos.getY() + 0.5D, (double)TELabeledRedstone.this.pos.getZ() + 0.5D);
            }
            /**
             * Get the world, if available. <b>{@code null} is not allowed!</b> If you are not an entity in the world,
             * return the overworld
             */
            public World getEntityWorld()
            {
                return playerIn.getEntityWorld();
            }
            /**
             * Returns the entity associated with the command sender. MAY BE NULL!
             */
            public Entity getCommandSenderEntity()
            {
                return playerIn;
            }
            /**
             * Returns true if the command sender should be sent feedback about executed commands
             */
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

        return true;
    }

    public int getRotation()
    {
        return rotation;
    }

    public void setRotation(int rotation)
    {
        this.rotation = rotation;
    }

    public boolean getIsEditable()
    {
        return this.isEditable;
    }

    @SideOnly(Side.CLIENT)
    public void setIsEditable(boolean isEditableIn)
    {
        this.isEditable = isEditableIn;

        if (!isEditableIn)
        {
            this.player = null;
        }
    }

    public void setPlayer(EntityPlayer playerIn)
    {
        this.player = playerIn;
    }

    public EntityPlayer getPlayer()
    {
        return this.player;
    }

    public CommandResultStats getStats()
    {
        return this.commandResultStats;
    }
}
