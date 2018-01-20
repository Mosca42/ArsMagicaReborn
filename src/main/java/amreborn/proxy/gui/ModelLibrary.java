package amreborn.proxy.gui;

import amreborn.bosses.models.ModelPlantGuardianSickle;
import amreborn.bosses.models.ModelWinterGuardianArm;
import amreborn.entity.EntityBroom;
import amreborn.entity.models.ModelBroom;
import amreborn.models.ModelAirGuardianHoverball;
import amreborn.models.ModelArcaneGuardianSpellBook;
import amreborn.models.ModelCandle;
import amreborn.models.ModelEarthGuardianChest;
import amreborn.models.ModelFireGuardianEars;
import amreborn.models.ModelWaterGuardianOrbs;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelLibrary{

	public static final ModelLibrary instance = new ModelLibrary();

	private ModelLibrary(){
		dummyBroom = new EntityBroom(Minecraft.getMinecraft().world);
		sickle.setNoSpin();

		dummyArcaneSpellbook = new ModelArcaneGuardianSpellBook();
		winterGuardianArm = new ModelWinterGuardianArm();
		fireEars = new ModelFireGuardianEars();
		waterOrbs = new ModelWaterGuardianOrbs();
		earthArmor = new ModelEarthGuardianChest();
		airSled = new ModelAirGuardianHoverball();
		wardingCandle = new ModelCandle();
	}

	public final ModelPlantGuardianSickle sickle = new ModelPlantGuardianSickle();

	public final ModelBroom magicBroom = new ModelBroom();
	public final EntityBroom dummyBroom;
	public final ModelArcaneGuardianSpellBook dummyArcaneSpellbook;
	public final ModelWinterGuardianArm winterGuardianArm;

	public final ModelFireGuardianEars fireEars;
	public final ModelWaterGuardianOrbs waterOrbs;
	public final ModelEarthGuardianChest earthArmor;

	public final ModelAirGuardianHoverball airSled;

	public final ModelCandle wardingCandle;
}
