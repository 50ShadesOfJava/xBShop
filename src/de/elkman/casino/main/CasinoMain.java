package de.elkman.casino.main;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class CasinoMain {

	public static HashMap<UUID, Integer> inserts = new HashMap<>();
	public static HashMap<Player, CasinoGame> currentGame = new HashMap<>();
	
}
