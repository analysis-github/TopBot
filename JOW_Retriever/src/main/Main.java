package main;

import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.ScriptCategory;
import org.tbot.methods.Bank;
import org.tbot.methods.NPCChat;
import org.tbot.methods.Npcs;
import org.tbot.methods.Random;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.methods.web.Web;
import org.tbot.methods.web.actions.NPCAction;
import org.tbot.methods.web.banks.WebBanks;
import org.tbot.wrappers.NPC;
import org.tbot.wrappers.Tile;

import util.Paint;

@Manifest(name = "JOW Retriever", 
authors = "Analysis", 
description = "Retrieves jugs of water from Hassan.", 
version = 0.1,
category = ScriptCategory.MONEY_MAKING)

public class Main extends AbstractScript {

	private NPC hassan; 

	private static final Tile PALACE_TILE = new Tile(3293, 3163);

	private Web web = new Web(); 
	
	@Override
	public boolean onStart() {
		new Paint();
		return true;
	}

	/*
	 * Main logic of the script.
	 */
	@Override
	public int loop() {
		if (!Inventory.isFull()) {
			retrieve();
		} else {
			bank();
		}

		return Random.nextInt(150, 225);
	}

	/*
	 * Walk to Hassan and retrieve jugs of water.
	 */
	private void retrieve() {
		hassan = Npcs.getNearest("Hassan");

		if (!Bank.isOpen()) {
			if (hassan != null && Walking.canReach(hassan.getLocation())) {
				NPCChat chat = new NPCChat(true, "It's just too hot here. How can you stand it?");
				NPCAction action = new NPCAction("Hassan", "Talk-to", chat);

				action.traverse();
				Time.sleep(Random.nextInt(650, 900));
			} else {
				Path p = Walking.findPath(PALACE_TILE);

				if (p != null) {
					p.traverse();
					Time.sleep(900, 1250);
				}
			}
		} else {
			Bank.close();
			Time.sleep(300, 500);
		}
	}

	/*
	 * Walk to the bank and deposit everything in inventory.
	 */
	private void bank() {
		if (!Bank.isOpen()) {
			web.openBank(WebBanks.AL_KHARID_BANK);
			Time.sleep(800, 1600);
		} else {
			Bank.depositAll();
			Time.sleep(500, 1050);
		}
	}
}
