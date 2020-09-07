package me.Latestion.CustomWeapons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Latestion.CustomWeapons.Executor.Commands;
import me.Latestion.CustomWeapons.Executor.TabC;
import me.Latestion.CustomWeapons.Files.InfiniteManager;
import me.Latestion.CustomWeapons.Files.NetheriteManager;
import me.Latestion.CustomWeapons.Files.TotemManager;
import me.Latestion.CustomWeapons.InfiniteItems.Items;
import me.Latestion.CustomWeapons.MyEvents.Bow;
import me.Latestion.CustomWeapons.MyEvents.EntityDamageEntity;
import me.Latestion.CustomWeapons.MyEvents.EntityDeath;
import me.Latestion.CustomWeapons.MyEvents.EntityExplode;
import me.Latestion.CustomWeapons.MyEvents.Interact;
import me.Latestion.CustomWeapons.MyEvents.PlayerJoin;
import me.Latestion.CustomWeapons.MyVoids.NetheriteBowTargetUtil;
import me.Latestion.CustomWeapons.MyVoids.NetheriteWandRechargeUtils;
import me.Latestion.CustomWeapons.MyVoids.Util;
import me.Latestion.CustomWeapons.Totems.TotemCheck;
import me.Latestion.CustomWeapons.Totems.StormsTotem.Storm;
import me.Latestion.CustomWeapons.Totems.StormsTotem.StormEvents;
import me.Latestion.CustomWeapons.Totems.TimeTotem.Time;
import me.Latestion.CustomWeapons.Totems.TimeTotem.TimeEvents;
import me.Latestion.CustomWeapons.Totems.VoidTotem.VoidEvents;

public class Main extends JavaPlugin {
	
	public NetheriteManager netherite;
	public InfiniteManager infinite;
	public TotemManager totem;
	public Util utils;
	public NetheriteWandRechargeUtils reUtil;
	public NetheriteBowTargetUtil nBowUtil;
	
	public List<ItemStack> allTotems = new ArrayList<ItemStack>();
	
	public List<Player> fireballCooldown = new ArrayList<>();
	public Map<Player, Integer> guage = new HashMap<Player, Integer>();
	public Map<Player, BossBar> guageBar = new HashMap<Player, BossBar>();				
	public Map<Player, LivingEntity> target = new HashMap<Player, LivingEntity>();
	public List<Fireball> dontExplode = new ArrayList<Fireball>();
	
	public Map<UUID, BossBar> freezeBar = new HashMap<UUID, BossBar>(); // Time
	public Map<UUID, BossBar> astralBar = new HashMap<UUID, BossBar>(); // Time
	public List<UUID> astralCooldown = new ArrayList<UUID>(); // Time
	public List<UUID> freezeCooldown = new ArrayList<UUID>(); // Time
	
	public List<UUID> weatherCooldown = new ArrayList<UUID>(); // Storm
	public List<UUID> strikeCooldown = new ArrayList<UUID>(); // Storm
	public Map<UUID, BossBar> weatherBar = new HashMap<UUID, BossBar>(); // Storm
	public Map<UUID, BossBar> strikeBar = new HashMap<UUID, BossBar>(); // Storm
	
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		nether();
		infinites();
		events();
		totems();
		registerCommands();
	}
	
	@Override
	public void onDisable() {
		
	}
	
	private void nether() {
		this.netherite = new NetheriteManager(this);
		this.utils = new Util(this);
		utils.run();
		this.reUtil = new NetheriteWandRechargeUtils(this);
		reUtil.cast();
		this.nBowUtil = new NetheriteBowTargetUtil(this);
		nBowUtil.run();
	}
	
	private void events() {
		PluginManager manager = this.getServer().getPluginManager();
		manager.registerEvents(new Bow(this), this);
		manager.registerEvents(new Interact(this), this);
		manager.registerEvents(new Items(this), this);
		manager.registerEvents(new PlayerJoin(this), this);
		manager.registerEvents(new EntityDamageEntity(this), this);
		manager.registerEvents(new EntityDeath(this), this);
		manager.registerEvents(new EntityExplode(this), this);
	}
	
	private void infinites() {
		this.infinite = new InfiniteManager(this);
	}
	
	private void registerCommands() {
		this.getCommand("tmw").setExecutor(new Commands(this));
		this.getCommand("tmw").setTabCompleter(new TabC());
	}
	
	private void totems() {
		this.totem = new TotemManager(this);
		Time t = new Time(this);
		t.cast();
		this.getServer().getPluginManager().registerEvents(new TimeEvents(this), this);
		Storm s = new Storm(this);
		s.cast();
		this.getServer().getPluginManager().registerEvents(new StormEvents(this), this);
		TotemCheck check = new TotemCheck(this);
		check.cast();
		
		
		this.getServer().getPluginManager().registerEvents(new VoidEvents(this), this);
	}
	
}

