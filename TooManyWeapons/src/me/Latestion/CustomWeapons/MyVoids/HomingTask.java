package me.Latestion.CustomWeapons.MyVoids;

import org.bukkit.Effect;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.Latestion.CustomWeapons.Main;

public class HomingTask extends BukkitRunnable {
	
	@SuppressWarnings("unused")
	private double MaxRotationAngle = 0.12D;
	@SuppressWarnings("unused")
	private double TargetSpeed = 1.4D;
	Arrow arrow;
	LivingEntity target;
 
	public HomingTask(Arrow arrow, LivingEntity target, Main plugin) {
		this.arrow = arrow;
		this.target = target;
		runTaskTimer(plugin, 1L, 1L);
	}
 
	public void run() {
		double speed = this.arrow.getVelocity().length();
		if ((this.arrow.isOnGround()) || (this.arrow.isDead()) || (this.target.isDead())) {
			cancel();
			return;
		}
		
		if (arrow.getNearbyEntities(10.0D, 10.0D, 10.0D).contains(this.target)) {
			Vector toTarget = this.target.getLocation().clone().add(new Vector(0.0D, 0.5D, 0.0D))
					.subtract(this.arrow.getLocation()).toVector();		 
			Vector dirVelocity = this.arrow.getVelocity().clone().normalize();
			Vector dirToTarget = toTarget.clone().normalize();
			double angle = dirVelocity.angle(dirToTarget);	 
			double newSpeed = 0.9D * speed + 0.14D;
			if (((this.target instanceof Player)) && (this.arrow.getLocation().distance(this.target.getLocation()) < 10.0D)) {
				Player player = (Player)this.target;
				if (player.isBlocking()) {
					newSpeed = speed * 0.6D;
				}
			}
			Vector newVelocity;
			if (angle < 0.48D) {
				newVelocity = dirVelocity.clone().multiply(newSpeed);
			}
			else {
				Vector newDir = dirVelocity.clone().multiply((angle - 0.48D) / angle).add(dirToTarget.clone().multiply(0.48D / angle));
				newDir.normalize();
				newVelocity = newDir.clone().multiply(newSpeed);
			}
			this.arrow.setVelocity(newVelocity.add(new Vector(0.0D, 0.03D, 0.0D)));
			this.arrow.getWorld().playEffect(this.arrow.getLocation(), Effect.SMOKE, 0);
		}
	}
}
