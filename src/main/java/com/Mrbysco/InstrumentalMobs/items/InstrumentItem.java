package com.mrbysco.instrumentalmobs.items;

import com.mrbysco.instrumentalmobs.InstrumentalMobs;
import com.mrbysco.instrumentalmobs.Reference;
import com.mrbysco.instrumentalmobs.config.InstrumentalConfigGen;
import com.mrbysco.instrumentalmobs.utils.InstrumentHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class InstrumentItem extends Item{
	
	private SoundEvent sound;
	private int cooldown;
	private int useDuration;

	public InstrumentItem(String registryName, SoundEvent soundIn, int cooldown, int maxDamage, int duration) {
		setCreativeTab(InstrumentalMobs.instrumentalTab);
		this.setTranslationKey(Reference.MOD_PREFIX + registryName.replaceAll("_", ""));
		this.setRegistryName(registryName);
		maxStackSize = 1;
		this.setMaxDamage(maxDamage);
		
		this.cooldown = cooldown;
		sound = soundIn;
		useDuration = duration;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
        
        if(this.cooldown != 0)
		{
			playerIn.getCooldownTracker().setCooldown(this, this.cooldown);
		}
        
		playerIn.playSound(this.sound, 1F, 1F);
		if(InstrumentalConfigGen.general.mobsReact)
		{
			InstrumentHelper.instrumentDamage(worldIn, playerIn);
		}
		itemstack.damageItem(1, playerIn);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) 
	{
		return useDuration;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) 
	{
		return EnumAction.DRINK;
	}
}
