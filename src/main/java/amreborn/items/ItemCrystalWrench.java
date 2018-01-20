package amreborn.items;

import amreborn.ArsMagicaReborn;
import amreborn.api.math.AMVector3;
import amreborn.api.power.IPowerNode;
import amreborn.particles.AMParticle;
import amreborn.particles.ParticleFadeOut;
import amreborn.particles.ParticleMoveOnHeading;
import amreborn.power.PowerNodeRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class ItemCrystalWrench extends ItemArsMagicaRotated{

	private static String KEY_PAIRLOC = "PAIRLOC";
	private static String HAB_PAIRLOC = "HABLOC";
	private static String KEEP_BINDING = "KEEPBINDING";
	private static String MODE = "WRENCHMODE";

	private static final int MODE_PAIR = 0;
	private static final int MODE_DISCONNECT = 1;

	public ItemCrystalWrench(){
		super();
	}
	
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

		TileEntity te = world.getTileEntity(pos);

		if (!player.getHeldItem(hand).hasTagCompound())
			player.getHeldItem(hand).setTagCompound(new NBTTagCompound());

		int cMode = getMode(player.getHeldItem(hand));

		if (te != null && te instanceof IPowerNode){
			if (cMode == MODE_DISCONNECT){
				doDisconnect((IPowerNode<?>)te, world, pos.getX() + hitX, pos.getY() + hitY, pos.getZ() + hitZ, player);
				return EnumActionResult.FAIL;
			}

			if (player.getHeldItem(hand).getTagCompound().hasKey(KEY_PAIRLOC)){
				doPairNodes(world, pos, player.getHeldItem(hand), player, hitX, hitY, hitZ, te);
			}else{
				storePairLocation(world, te, player.getHeldItem(hand), player, pos.getX() + hitX, pos.getY() + hitY, pos.getZ() + hitZ);
			}
		}else if (player.isSneaking()){
			handleModeChanges(player.getHeldItem(hand));

		}
		return EnumActionResult.PASS;
	}

	private void handleCMPair(ItemStack stack, World world, EntityPlayer player, TileEntity te, double hitX, double hitY, double hitZ){
		AMVector3 habLocation = AMVector3.readFromNBT(stack.getTagCompound().getCompoundTag(HAB_PAIRLOC));
		if (world.isRemote){
			spawnLinkParticles(world, hitX, hitY, hitZ);
		}else{
			if (!stack.getTagCompound().hasKey(KEEP_BINDING))
				stack.getTagCompound().removeTag(HAB_PAIRLOC);
		}
	}

	private void storePairLocation(World world, TileEntity te, ItemStack stack, EntityPlayer player, double hitX, double hitY, double hitZ){
		AMVector3 destination = new AMVector3(te);
		if (!world.isRemote){

				NBTTagCompound pairLoc = new NBTTagCompound();
				destination.writeToNBT(pairLoc);
				stack.getTagCompound().setTag(KEY_PAIRLOC, pairLoc);
			

			if (player.isSneaking()){
				stack.getTagCompound().setBoolean(KEEP_BINDING, true);
			}
		}else{
			spawnLinkParticles(world, hitX, hitY, hitZ);
		}
	}

	private void doPairNodes(World world, BlockPos pos, ItemStack stack, EntityPlayer player, double hitX, double hitY, double hitZ, TileEntity te){
		AMVector3 source = AMVector3.readFromNBT(stack.getTagCompound().getCompoundTag(KEY_PAIRLOC));
		TileEntity sourceTE = world.getTileEntity(source.toBlockPos());
		if (sourceTE != null && sourceTE instanceof IPowerNode && !world.isRemote){
			player.sendMessage(new TextComponentString(PowerNodeRegistry.For(world).tryPairNodes((IPowerNode<?>)sourceTE, (IPowerNode<?>)te)));
		}else if (world.isRemote){
			spawnLinkParticles(world, pos.getX() + hitX, pos.getY() + hitY, pos.getZ() + hitZ);
		}
		if (!stack.getTagCompound().hasKey(KEEP_BINDING))
			stack.getTagCompound().removeTag(KEY_PAIRLOC);
	}

	private void doDisconnect(IPowerNode<?> node, World world, double hitX, double hitY, double hitZ, EntityPlayer player){
		PowerNodeRegistry.For(world).tryDisconnectAllNodes(node);
		if (world.isRemote){
			spawnLinkParticles(player.world, hitX, hitY, hitZ, true);
		}else{
			player.sendMessage(new TextComponentString(I18n.translateToLocal("am2.tooltip.disconnectPower")));
		}
	}

	private void handleModeChanges(ItemStack stack){
		if (stack.getTagCompound().hasKey(KEEP_BINDING)){
			stack.getTagCompound().removeTag(KEEP_BINDING);

			if (stack.getTagCompound().hasKey(KEY_PAIRLOC))
				stack.getTagCompound().removeTag(KEY_PAIRLOC);

			if (stack.getTagCompound().hasKey(HAB_PAIRLOC))
				stack.getTagCompound().removeTag(HAB_PAIRLOC);
		}else{
			if (getMode(stack) == MODE_PAIR)
				stack.getTagCompound().setInteger(MODE, MODE_DISCONNECT);
			else
				stack.getTagCompound().setInteger(MODE, MODE_PAIR);

		}
	}

	public static int getMode(ItemStack stack){
		if (stack.getTagCompound().hasKey(MODE)){
			return stack.getTagCompound().getInteger(MODE);
		}
		return 0;
	}

	private void spawnLinkParticles(World world, double hitX, double hitY, double hitZ){
		spawnLinkParticles(world, hitX, hitY, hitZ, false);
	}

	private void spawnLinkParticles(World world, double hitX, double hitY, double hitZ, boolean disconnect){
		for (int i = 0; i < 10; ++i){
			AMParticle particle = (AMParticle)ArsMagicaReborn.proxy.particleManager.spawn(world, "none_hand", hitX, hitY, hitZ);
			if (particle != null){
				if (disconnect){
					particle.setRGBColorF(1, 0, 0);
					particle.addRandomOffset(0.5f, 0.5f, 0.5f);
				}
				particle.setMaxAge(10);
				particle.setParticleScale(0.1f);
				particle.AddParticleController(new ParticleMoveOnHeading(particle, world.rand.nextInt(360), world.rand.nextInt(360), world.rand.nextDouble() * 0.2, 1, false));
				particle.AddParticleController(new ParticleFadeOut(particle, 1, false).setFadeSpeed(0.1f));
			}
		}
	}

	@Override
	public boolean getShareTag(){
		return true;
	}

	@Override
	public int getItemStackLimit(){
		return 1;
	}

}
