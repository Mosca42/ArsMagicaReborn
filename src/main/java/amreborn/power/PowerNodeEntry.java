package amreborn.power;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import amreborn.ArsMagicaReborn;
import amreborn.api.power.IPowerNode;
import amreborn.utils.NBTUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.Constants;

public class PowerNodeEntry{
	HashMap<PowerTypes, Float> powerAmounts;
	private HashMap<PowerTypes, ArrayList<LinkedList<Vec3d>>> nodePaths;

	public PowerNodeEntry(){
		powerAmounts = new HashMap<PowerTypes, Float>();
		nodePaths = new HashMap<PowerTypes, ArrayList<LinkedList<Vec3d>>>();
	}

	public void clearNodePaths(){
		for (PowerTypes type : nodePaths.keySet()){
			nodePaths.get(type).clear();
		}
	}

	public void registerNodePath(PowerTypes type, LinkedList<Vec3d> path){
		ArrayList<LinkedList<Vec3d>> paths = nodePaths.get(type);
		if (paths == null){
			paths = new ArrayList<LinkedList<Vec3d>>();
			nodePaths.put(type, paths);
		}

		//do we already have a path that ends here?
		Iterator<LinkedList<Vec3d>> it = paths.iterator();
		while (it.hasNext()){
			if (it.next().getLast().equals(path.getLast())){
				it.remove();
				break;
			}
		}

		paths.add(path);
	}

	public float requestPower(World world, PowerTypes type, float amount, float capacity){
		if (getPower(type) >= capacity)
			return 0f;
		ArrayList<LinkedList<Vec3d>> paths = nodePaths.get(type);
		if (paths == null || paths.size() == 0){
			//AMCore.log.info("No Paths!");
			return 0;
		}

		//AMCore.log.info("Path Exists");

		if (powerAmounts.containsKey(type) && powerAmounts.get(type) + amount > capacity){
			amount = capacity - powerAmounts.get(type);
		}

		float requested = 0f;
		for (LinkedList<Vec3d> path : paths){
			requested += requestPowerFrom(world, path, type, amount - requested);
			if (requested >= amount)
				break;
		}
		return requested;
	}

	private boolean validatePath(World world, LinkedList<Vec3d> path){
		for (Vec3d vec : path){
			//power can't transfer through unloaded chunks!
			Chunk chunk = world.getChunkFromBlockCoords(new BlockPos(vec));
			if (!chunk.isLoaded())
				return false;
			TileEntity te = world.getTileEntity(new BlockPos(vec));
			//if valid, continue the loop, otherwise return false.
			if (te != null && te instanceof IPowerNode)
				continue;

			//set a marker block to say that a conduit or other power relay of some sort was here and is now not
			if (!world.isRemote && world.isAirBlock(new BlockPos(vec))){
//				world.setBlock(new BlockPos(vec), BlocksCommonProxy.brokenLinkBlock);
			}

			return false;
		}
		//if we're here, then all locations checked out
		return true;
	}

	private float requestPowerFrom(World world, LinkedList<Vec3d> path, PowerTypes type, float amount){
		if (!validatePath(world, path))
			return 0f;
		Vec3d end = path.getLast();
		TileEntity te = world.getTileEntity(new BlockPos(end));
		if (te != null && te instanceof IPowerNode){
			if (((IPowerNode<?>)te).canProvidePower(type)){
				return PowerNodeRegistry.For(world).consumePower(((IPowerNode<?>)te), type, amount);
			}
		}
		return 0f;
	}

	public PowerTypes getHighestPowerType(){
		float highest = 0;
		PowerTypes hType = PowerTypes.NONE;
		for (PowerTypes type : powerAmounts.keySet()){
			if (powerAmounts.get(type) > highest){
				highest = powerAmounts.get(type);
				hType = type;
			}
		}
		return hType;
	}

	public float getHighestPower(){
		float highest = 0;
		for (PowerTypes type : powerAmounts.keySet()){
			if (powerAmounts.get(type) > highest){
				highest = powerAmounts.get(type);
			}
		}
		return highest;
	}

	public float getPower(PowerTypes type){
		Float f = powerAmounts.get(type);
		return f == null ? 0 : f;
	}

	public void setPower(PowerTypes type, float amt){
		if (type != null)
			powerAmounts.put(type, amt);
	}

	public NBTTagCompound saveToNBT(){
		NBTTagCompound compound = new NBTTagCompound();

		//power amounts
		//list of entries containing power type IDs and the associated amount
		NBTTagList powerAmountStore = new NBTTagList();
		for (PowerTypes type : this.powerAmounts.keySet()){
			if (type == null) //sanity check
				continue;
			//individual power type/amount entry
			NBTTagCompound powerType = new NBTTagCompound();
			//set power type ID
			powerType.setInteger("powerType", type.ID());
			//set power amount
			powerType.setFloat("powerAmount", powerAmounts.get(type));
			//attach the power node to the list
			powerAmountStore.appendTag(powerType);
		}
		//append list to output compound
		compound.setTag("powerAmounts", powerAmountStore);

		//power paths
		NBTTagList powerPathList = new NBTTagList();

		for (PowerTypes type : nodePaths.keySet()){

			//This is the actual entry in the power path list
			NBTTagCompound powerPathEntry = new NBTTagCompound();

			ArrayList<LinkedList<Vec3d>> paths = nodePaths.get(type);
			//This stores each path individually for a given power type
			NBTTagList pathsForType = new NBTTagList();
			for (LinkedList<Vec3d> path : paths){
				//This stores each individual node in the given path
				NBTTagList pathNodes = new NBTTagList();
				for (Vec3d pathNode : path){
					//This stores one individual node in the given path
					NBTTagCompound node = new NBTTagCompound();
					NBTUtils.writeVecToNBT(pathNode, node);
					//Append individual node to path
					pathNodes.appendTag(node);
				}
				//Append path to list of paths for the power type
				pathsForType.appendTag(pathNodes);
			}

			//set the power type that this list of paths is for
			powerPathEntry.setInteger("powerType", type.ID());
			//append the list of paths to the entry in the power path list
			powerPathEntry.setTag("nodePaths", pathsForType);

			//AMCore.log.info("Saved %d node paths for %s etherium.", nodePaths.get(type).size(), type.name());

			//append this entry in the power path list to the list of power path entries
			powerPathList.appendTag(powerPathEntry);
		}
		//append the entire power path list to the saved compound
		compound.setTag("powerPathList", powerPathList);

		return compound;
	}

	public void readFromNBT(NBTTagCompound compound){
		//power amounts
		//locate the list of power amounts
		NBTTagList powerAmountStore = compound.getTagList("powerAmounts", Constants.NBT.TAG_COMPOUND);
		//sanity check
		if (powerAmountStore != null){
			//spin through nodes
			for (int i = 0; i < powerAmountStore.tagCount(); ++i){
				//reference current node
				NBTTagCompound powerType = (NBTTagCompound)powerAmountStore.getCompoundTagAt(i);
				//resolve power type
				PowerTypes type = PowerTypes.getByID(powerType.getInteger("powerType"));
				//resolve power amount
				float powerAmount = powerType.getFloat("powerAmount");
				//register entry
				powerAmounts.put(type, powerAmount);
			}
		}

		//power paths
		//locate list of power paths
		NBTTagList powerPathList = compound.getTagList("powerPathList", Constants.NBT.TAG_COMPOUND);
		if (powerPathList != null){
			for (int i = 0; i < powerPathList.tagCount(); ++i){
				NBTTagCompound powerPathEntry = (NBTTagCompound)powerPathList.getCompoundTagAt(i);
				PowerTypes type = PowerTypes.getByID(powerPathEntry.getInteger("powerType"));
				NBTTagList pathNodes = powerPathEntry.getTagList("nodePaths", Constants.NBT.TAG_LIST);
				ArrayList<LinkedList<Vec3d>> pathsList = new ArrayList<LinkedList<Vec3d>>();
				if (pathNodes != null){
					for (int j = 0; j < pathNodes.tagCount(); j++){
						NBTTagList nodeList = (NBTTagList)pathNodes.get(j);
						LinkedList<Vec3d> powerPath = new LinkedList<Vec3d>();
						if (nodeList != null){
							for (int b = 0; b < nodeList.tagCount(); ++b){
								NBTTagCompound node = (NBTTagCompound)nodeList.getCompoundTagAt(b);
								Vec3d nodeLocation = NBTUtils.readVecFromNBT(node);
								powerPath.add(nodeLocation);
							}
							pathsList.add(powerPath);
						}
					}
					nodePaths.put(type, pathsList);
					//ArsMagicaReborn.LOGGER.info(String.format("Loaded %d node paths for %s etherium.", pathsList.size(), type.name()));
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public HashMap<PowerTypes, ArrayList<LinkedList<Vec3d>>> getNodePaths(){
		return (HashMap<PowerTypes, ArrayList<LinkedList<Vec3d>>>)nodePaths.clone();
	}
}