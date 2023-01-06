package io.wurmatron.celestial.common;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mod.EventBusSubscriber
public class Registry {

	public static List <Item> items = new ArrayList <> ();
	public static List <Block> blocks = new ArrayList <> ();
	public static HashMap <Block, Item> blockItems = new HashMap <> ();
	public static List <Potion> potions = new ArrayList <> ();

	public static void registerItem (Item item,String registryName) {
		item.setRegistryName (registryName);
		item.setUnlocalizedName (registryName);
		items.add (item);
	}

	public static void registerBlock (Block block,String registryName) {
		block.setRegistryName (registryName);
		block.setUnlocalizedName (registryName);
		ItemBlock itemBlock = new ItemBlock (block);
		itemBlock.setUnlocalizedName (registryName);
		itemBlock.setRegistryName (registryName);
		blocks.add (block);
		blockItems.put (block,itemBlock);
	}

	public static void registerPotion (Potion potion) {
		potions.add (potion);
	}

	private static void createModel (Block block) {
		ModelLoader.setCustomModelResourceLocation (Registry.blockItems.get (block),0,new ModelResourceLocation (block.getRegistryName ().toString (),"inventory"));
	}

	private static void createModel (Item item) {
		ModelLoader.setCustomModelResourceLocation (item,0,new ModelResourceLocation (item.getRegistryName ().toString (),"inventory"));
	}

	@SubscribeEvent
	public void registerBlocks (RegistryEvent.Register <Block> e) {
		e.getRegistry ().registerAll (blocks.toArray (new Block[0]));
	}

	@SubscribeEvent
	public void registerItems (RegistryEvent.Register <Item> e) {
		e.getRegistry ().registerAll (items.toArray (new Item[0]));
		e.getRegistry ().registerAll (blockItems.values ().toArray (new Item[0]));
	}

	@SubscribeEvent
	public void loadModel (ModelRegistryEvent e) {
		for (Block block : Registry.blocks)
			createModel (block);
		for (Item item : Registry.items)
			createModel (item);
		for (Item item : Registry.blockItems.values ())
			createModel (item);
	}

	@SubscribeEvent
	public static void registerPotions (RegistryEvent.Register <Potion> event) {
		event.getRegistry ().registerAll (potions.toArray (new Potion[0]));
	}
}
