package amreborn.blocks.tileentity;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;

import amreborn.ArsMagicaReborn;
import amreborn.api.IMultiblockStructureController;
import amreborn.api.blocks.MultiblockGroup;
import amreborn.api.blocks.MultiblockStructureDefinition;
import amreborn.api.blocks.TypedMultiblockGroup;
import amreborn.blocks.BlockArsMagicaBlock;
import amreborn.blocks.BlockArsMagicaBlock.EnumBlockType;
import amreborn.buffs.BuffEffectManaRegen;
import amreborn.defs.BlockDefs;
import amreborn.defs.PotionEffectsDefs;
import amreborn.power.PowerNodeRegistry;
import amreborn.power.PowerTypes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TileEntityCelestialPrism extends TileEntityObelisk implements IMultiblockStructureController{

	private int particleCounter = 0;

	private boolean onlyChargeAtNight = false;

	
	@SuppressWarnings("unchecked")
	public TileEntityCelestialPrism(){
		super(2500);

		powerBase = 1.0f;

		structure = new MultiblockStructureDefinition("celestialprism_structure");
		
		capsGroup = new TypedMultiblockGroup("caps", Lists.newArrayList(
				createMap(Blocks.GLASS.getDefaultState()),
				createMap(Blocks.GOLD_BLOCK.getDefaultState()),
				createMap(Blocks.DIAMOND_BLOCK.getDefaultState()),
				createMap(BlockDefs.blocks.getDefaultState().withProperty(BlockArsMagicaBlock.BLOCK_TYPE, BlockArsMagicaBlock.EnumBlockType.MOONSTONE))
				), false);

		pillars = new MultiblockGroup("pillars", Lists.newArrayList(Blocks.QUARTZ_BLOCK.getDefaultState()), false);
		
		this.caps = new HashMap<IBlockState, Float>();
		this.caps.put(Blocks.GLASS.getDefaultState(), 1.1f);
		this.caps.put(Blocks.GOLD_BLOCK.getDefaultState(), 1.4f);
		this.caps.put(Blocks.DIAMOND_BLOCK.getDefaultState(), 2f);
		this.caps.put(BlockDefs.blocks.getDefaultState().withProperty(BlockArsMagicaBlock.BLOCK_TYPE, BlockArsMagicaBlock.EnumBlockType.MOONSTONE), 3f);
		
		MultiblockGroup prism = new MultiblockGroup("prism", Lists.newArrayList(BlockDefs.celestialPrism.getDefaultState()), true);
		prism.addBlock(BlockPos.ORIGIN);

		pillars.addBlock(new BlockPos(-2, 0, -2));
		pillars.addBlock(new BlockPos(-2, 1, -2));

		capsGroup.addBlock(new BlockPos(-2, 2, -2), 0);
		capsGroup.addBlock(new BlockPos(2, 2, -2), 0);
		capsGroup.addBlock(new BlockPos(-2, 2, 2), 0);
		capsGroup.addBlock(new BlockPos(2, 2, 2), 0);

		pillars.addBlock(new BlockPos(2, 0, -2));
		pillars.addBlock(new BlockPos(2, 1, -2));

		pillars.addBlock(new BlockPos(-2, 0, 2));
		pillars.addBlock(new BlockPos(-2, 1, 2));

		pillars.addBlock(new BlockPos(2, 0, 2));
		pillars.addBlock(new BlockPos(2, 1, 2));

		wizardChalkCircle = addWizChalkGroupToStructure(structure);
		structure.addGroup(pillars);
		structure.addGroup(capsGroup);
		structure.addGroup(wizardChalkCircle);
		structure.addGroup(prism);
	}

	@Override
	protected void checkNearbyBlockState(){
		List<MultiblockGroup> groups = structure.getMatchingGroups(world, pos);

		float capsLevel = 1;
		boolean pillarsFound = false;
		boolean wizChalkFound = false;
		boolean capsFound = false;

		for (MultiblockGroup group : groups){
			if (group == pillars)
				pillarsFound = true;
			else if (group == wizardChalkCircle)
				wizChalkFound = true;
			else if (group == capsGroup)
				capsFound = true;
		}
		
		if (pillarsFound && capsFound) {
			IBlockState capState = world.getBlockState(pos.add(2, 2, 2));
			
			for (IBlockState cap : caps.keySet()){
				if (capState == cap){
					capsLevel = caps.get(cap);
					if (cap.getBlock() == BlockDefs.blocks && cap.getValue(BlockArsMagicaBlock.BLOCK_TYPE) == EnumBlockType.MOONSTONE)
						onlyChargeAtNight = true;
					else
						onlyChargeAtNight = false;
					break;
				}
			}
		}

		powerMultiplier = 1;

		if (wizChalkFound)
			powerMultiplier = 1.25f;

		if (pillarsFound)
			powerMultiplier *= capsLevel;
	}

	private boolean isNight(){
		long ticks = world.getWorldTime() % 24000;
		return ticks >= 12500 && ticks <= 23500;
	}

	@Override
	public void update(){

		if (surroundingCheckTicks++ % 100 == 0){
			checkNearbyBlockState();
			surroundingCheckTicks = 1;
			if (!world.isRemote && PowerNodeRegistry.For(this.world).checkPower(this, this.capacity * 0.1f)){
				List<EntityPlayer> nearbyPlayers = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.pos.add(-2, 0, -2), pos.add(2, 3, 2)));
				for (EntityPlayer p : nearbyPlayers){
					if (p.isPotionActive(PotionEffectsDefs.manaRegen)) continue;
					p.addPotionEffect(new BuffEffectManaRegen(600, 0));
				}
			}
		}

		if (onlyChargeAtNight == isNight()){
			PowerNodeRegistry.For(this.world).insertPower(this, PowerTypes.MANA, 0.25f * powerMultiplier);
			if (world.isRemote){

				if (particleCounter++ % (ArsMagicaReborn.config.FullGFX() ? 60 : ArsMagicaReborn.config.NoGFX() ? 180 : 120) == 0){
					particleCounter = 1;
					ArsMagicaReborn.proxy.particleManager.RibbonFromPointToPoint(world,
							pos.getX() + world.rand.nextFloat(),
							pos.getY() + (world.rand.nextFloat() * 2),
							pos.getZ() + world.rand.nextFloat(),
							pos.getX() + world.rand.nextFloat(),
							pos.getY() + (world.rand.nextFloat() * 2),
							pos.getZ() + world.rand.nextFloat());
				}
			}
		}
		super.callSuperUpdate();
	}

	@Override
	public MultiblockStructureDefinition getDefinition(){
		return structure;
	}

	@Override
	public boolean canRequestPower(){
		return false;
	}

	@Override
	public boolean canProvidePower(PowerTypes type){
		return type == PowerTypes.MANA;
	}

	@Override
	public List<PowerTypes> getValidPowerTypes(){
		return Lists.newArrayList(PowerTypes.MANA);
	}

	@Override
	public int getSizeInventory(){
		return 0;
	}
}
