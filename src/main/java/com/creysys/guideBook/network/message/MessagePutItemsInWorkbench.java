package com.creysys.guideBook.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Creysys on 07 Apr 16.
 */
public class MessagePutItemsInWorkbench implements IMessage {
    private Integer[] used;

    public MessagePutItemsInWorkbench(){}
    public MessagePutItemsInWorkbench(Integer[] used) {
        this.used = used;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int length = buf.readInt();
        used = new Integer[length];
        for(int i = 0; i < length; i++) {
            int read = buf.readInt();
            if(read == -1) used[i] = null;
            else used[i] = read;
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(used.length);
        for (Integer integer : used) {
            if(integer == null) buf.writeInt(-1);
            else buf.writeInt(integer);
        }
    }

    public static class Handler implements IMessageHandler<MessagePutItemsInWorkbench, IMessage> {
        private static BlockPos findNearbyWorkbench(EntityPlayer player) {
            World world = player.worldObj;

            int range = 4;
            int posX = (int)Math.round(player.posX - .5d);
            int posY = (int)Math.round(player.posY - .5d);
            int posZ = (int)Math.round(player.posZ - .5d);

            for(int x = posX - range; x <= posX + range; x++)
                for(int y = posY - range; y <= posY + range; y++)
                    for(int z = posZ - range; z <= posZ + range; z++) {
                        BlockPos pos = new BlockPos(x, y, z);
                        if(world.getBlockState(pos).getBlock() == Blocks.crafting_table) return pos;
                    }

            return null;
        }

        private static class MyInterfaceCraftingTable implements IInteractionObject {
            private final World world;
            private final BlockPos position;
            private Integer[] used;

            public MyInterfaceCraftingTable(World worldIn, BlockPos pos, Integer[] used) {
                this.world = worldIn;
                this.position = pos;
                this.used = used;
            }

            @Override
            public Container createContainer(InventoryPlayer playerInventory, EntityPlayer entityPlayer) {
                ContainerWorkbench container = new ContainerWorkbench(playerInventory, this.world, this.position);

                for(int i = 0; i < used.length; i++) {
                    if(used[i] == null) continue;

                    ItemStack playerStack = entityPlayer.inventory.mainInventory[used[i]];
                    if(playerStack != null && playerStack.stackSize > 0){
                        ItemStack stackUsed = playerStack.copy();
                        stackUsed.stackSize = 1;
                        playerStack.stackSize--;
                        if(playerStack.stackSize == 0) entityPlayer.inventory.mainInventory[used[i]] = null;

                        container.craftMatrix.setInventorySlotContents(i, stackUsed);
                    }
                }

                return container;
            }

            @Override
            public String getGuiID() {
                return "minecraft:crafting_table";
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public boolean hasCustomName() {
                return false;
            }

            @Override
            public ITextComponent getDisplayName() {
                return new TextComponentTranslation(Blocks.crafting_table.getUnlocalizedName() + ".name");
            }
        }

        @Override
        public IMessage onMessage(final MessagePutItemsInWorkbench message, final MessageContext ctx) {
            final EntityPlayer player = ctx.getServerHandler().playerEntity;

            final WorldServer mainThread = (WorldServer)player.worldObj;
            mainThread.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    BlockPos pos = findNearbyWorkbench(player);

                    if(pos != null) {
                        player.closeScreen();
                        player.displayGui(new MyInterfaceCraftingTable(mainThread, pos, message.used));
                        player.addStat(StatList.craftingTableInteraction);
                    }
                }
            });

            return null;
        }
    }
}
