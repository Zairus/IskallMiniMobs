package zairus.iskallminimobs.util.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import zairus.iskallminimobs.tileentity.TileEntityMMNamingStation;
import cpw.mods.fml.common.network.ByteBufUtils;

public class MMNamingStationPacket
	extends AbstractPacket
{
	private int x, y, z;
	private String itemName;
	
	public MMNamingStationPacket()
	{
	}
	
	public MMNamingStationPacket(int x, int y, int z, String name)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.itemName = name;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
		ByteBufUtils.writeUTF8String(buffer, this.itemName);
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		this.itemName = ByteBufUtils.readUTF8String(buffer);
	}
	
	@Override
	public void handleClientSide(EntityPlayer player)
	{
	}
	
	@Override
	public void handleServerSide(EntityPlayer player)
	{
		World world = player.worldObj;
		TileEntity te = world.getTileEntity(x, y, z);

		if (te instanceof TileEntityMMNamingStation)
		{
			((TileEntityMMNamingStation) te).setItemName(this.itemName);
		}
	}
}
