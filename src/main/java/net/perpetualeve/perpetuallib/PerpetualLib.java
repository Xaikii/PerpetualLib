package net.perpetualeve.perpetuallib;

import java.util.Random;

import org.slf4j.Logger;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.mojang.logging.LogUtils;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.perpetualeve.perpetuallib.handler.EventHandler;
import net.perpetualeve.perpetuallib.misc.StatisticHelper;

@Mod(PerpetualLib.MODID)
public class PerpetualLib {
	public static final String MODID = "perpetuallib";
	public static final DoubleEvaluator eval = new DoubleEvaluator();
	public static Random rand = new Random();

	public static final Logger LOGGER = LogUtils.getLogger();
	
	public Attribute UNBREAKING;
	
	public PerpetualLib() {
		StatisticHelper.fillMap();
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(EventHandler.INSTANCE);
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void register(RegisterEvent event) {
		if(event.getRegistryKey().equals(ForgeRegistries.Keys.ATTRIBUTES)) {
//			event.getForgeRegistry().register("attribute.name.generic.unbreaking", UNBREAKING);
		}
	}
}
