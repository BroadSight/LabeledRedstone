package com.BroadSight.labeledredstone.network;

import com.google.common.collect.Maps;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.BroadSight.labeledredstone.util.LogHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import net.minecraftforge.fml.common.network.FMLIndexedMessageToMessageCodec;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Constructor;
import java.util.EnumMap;

@ChannelHandler.Sharable
public class PacketManager
{
    private static final EnumMap<Side, FMLEmbeddedChannel> channels = Maps.newEnumMap(Side.class);

    public static void init()
    {
        if (!channels.isEmpty())
            return;

        Codec codec = new Codec();

        codec.addDiscriminator(0, LRPacket.class);

        channels.putAll(NetworkRegistry.INSTANCE.newChannel("LabeledRedstone", codec, new HandlerServer()));

        if (FMLCommonHandler.instance().getSide().isClient())
        {
            FMLEmbeddedChannel channel = channels.get(Side.CLIENT);
            String codecName = channel.findChannelHandlerNameForType(Codec.class);
            channel.pipeline().addAfter(codecName, "ClientHandler", new HandlerClient());
        }
    }

    public static void sendToServer(LRPacket packet)
    {
        channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        channels.get(Side.CLIENT).writeAndFlush(packet);
    }

    public static void sendToPlayer(LRPacket packet, EntityPlayer player)
    {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        channels.get(Side.SERVER).writeAndFlush(packet);
    }

    public static void sendToAllAround(LRPacket packet, NetworkRegistry.TargetPoint point)
    {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
        channels.get(Side.SERVER).writeAndFlush(packet);
    }

    public static void sendToDimension(LRPacket packet, int dimension)
    {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimension);
        channels.get(Side.SERVER).writeAndFlush(packet);
    }

    public static void sendToAll(LRPacket packet)
    {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        channels.get(Side.SERVER).writeAndFlush(packet);
    }

    public static Packet toMcPacket(LRPacket packet)
    {
        return channels.get(FMLCommonHandler.instance().getEffectiveSide()).generatePacketFrom(packet);
    }

    private static final class Codec extends FMLIndexedMessageToMessageCodec<LRPacket>
    {
        @Override
        public void encodeInto(ChannelHandlerContext ctx, LRPacket packet, ByteBuf target) throws Exception
        {
            ByteArrayDataOutput output = ByteStreams.newDataOutput();
            packet.encode(output);
            target.writeBytes(output.toByteArray());
        }

        @Override
        public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, LRPacket packet)
        {
            ByteArrayDataInput input = ByteStreams.newDataInput(source.array());
            input.skipBytes(1);
            packet.decode(input);
        }

        @Override
        public FMLIndexedMessageToMessageCodec<LRPacket> addDiscriminator(int discriminator, Class<? extends LRPacket> type)
        {
            if (!hasEmptyConstructor(type))
            {
                LogHelper.fatal(type.getName() + " does not have an empty constructor!");
            }

            return super.addDiscriminator(discriminator, type);
        }

        @SuppressWarnings("rawtypes")
        private static boolean hasEmptyConstructor(Class type)
        {
            try
            {
                for (Constructor c : type.getConstructors())
                {
                    if (c.getParameterTypes().length == 0)
                    {
                        return true;
                    }
                }
            }
            catch (SecurityException e)
            {
                //empty
            }

            return false;
        }
    }

    @ChannelHandler.Sharable
    @SideOnly(Side.CLIENT)
    private static final class HandlerClient extends SimpleChannelInboundHandler<LRPacket>
    {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, LRPacket packet) throws Exception
        {
            Minecraft mc = Minecraft.getMinecraft();
            packet.actionClient(mc.theWorld, mc.thePlayer);
        }
    }

    @ChannelHandler.Sharable
    private static final class HandlerServer extends SimpleChannelInboundHandler<LRPacket>
    {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, LRPacket packet) throws Exception
        {
            if (FMLCommonHandler.instance().getEffectiveSide().isClient())
            {
                return;
            }

            EntityPlayerMP player = ((NetHandlerPlayServer) ctx.channel().attr(NetworkRegistry.NET_HANDLER).get()).playerEntity;
            packet.actionServer(player.worldObj, player);
        }
    }
}
