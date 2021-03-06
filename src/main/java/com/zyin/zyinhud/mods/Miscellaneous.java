package com.zyin.zyinhud.mods;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.item.Item;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.GuiOpenEvent;

import com.zyin.zyinhud.util.InventoryUtil;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * The Miscellaneous mod has other functionality not relating to anything specific.
 */
public class Miscellaneous extends ZyinHUDModBase
{
	public static final Miscellaneous instance = new Miscellaneous();

	public static boolean UseEnhancedMiddleClick;
	public static boolean UseQuickPlaceSign;
	

    @SubscribeEvent
    public void GuiOpenEvent(GuiOpenEvent event)
	{
    	if(UseQuickPlaceSign && event.gui instanceof GuiEditSign && mc.thePlayer.isSneaking())
    	{
    		event.setCanceled(true);
    	}
	}
	
	/**
	 * When the player middle clicks
	 */
	public static void OnMiddleClick()
	{
		if(UseEnhancedMiddleClick)
			MoveMouseoveredBlockIntoHotbar();
	}
	
	
	/**
	 * Enhanced select block functionality (middle click). If the block exists in your inventory then
	 * it will put it into the hotbar, instead of it only working if the block is on your hotbar.
	 */
	public static void MoveMouseoveredBlockIntoHotbar()
	{
        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
        {
            Block block = mc.theWorld.getBlock(mc.objectMouseOver.blockX, mc.objectMouseOver.blockY, mc.objectMouseOver.blockZ);
            Item blockItem = Item.getItemFromBlock(block);
            
    		//first, scan the hotbar to see if the mouseovered block already exists on the hotbar
            int itemIndexInHotbar = InventoryUtil.GetItemIndexFromHotbar(blockItem);
            if(itemIndexInHotbar > 0)
            {
            	//if it does then do nothing since Minecraft takes care of it already
            }
            else
            {
            	//if it is not on the hotbar, check to see if it is in our inventory
            	int itemIndexInInventory = InventoryUtil.GetItemIndexFromInventory(blockItem);
            	if(itemIndexInInventory > 0)
            	{
            		//if it is in our inventory, swap it out to the hotbar
            		InventoryUtil.Swap(InventoryUtil.GetCurrentlySelectedItemInventoryIndex(), itemIndexInInventory);
            	}
            }
        }
	}
	
	

    /**
     * Toggles improving the middle click functionality to work with blocks in your inventory
     * @return 
     */
    public static boolean ToggleUseEnchancedMiddleClick()
    {
    	return UseEnhancedMiddleClick = !UseEnhancedMiddleClick;
    }
    
    /**
     * Toggles improving the middle click functionality to work with blocks in your inventory
     * @return 
     */
    public static boolean ToggleUseQuickPlaceSign()
    {
    	return UseQuickPlaceSign = !UseQuickPlaceSign;
    }
}
