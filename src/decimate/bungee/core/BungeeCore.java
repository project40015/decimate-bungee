package decimate.bungee.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class BungeeCore extends Plugin implements Listener {

	private String motd = "Innovating factions...";
	private long lastCheck;
	
	private long release;
	
	@SuppressWarnings("deprecation")
	public void onEnable(){
		BungeeCord.getInstance().getPluginManager().registerListener(this, this);
		release = (new Date(2017-1900, 9-1, 30, 9, 0)).getTime() - System.currentTimeMillis();
		updateMotd();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPing(ProxyPingEvent event){
		ServerPing sp = event.getResponse();
		sp.setDescription(ChatColor.YELLOW + "DECIMATEPVP.COM" + ChatColor.GRAY + ": \n" + ChatColor.GRAY + motd);
		if(System.currentTimeMillis()-lastCheck >= 1000*10){
			updateMotd();
		}
	}
	
	public String longToTime(long time){
		int seconds = (int) ((time / 1000) % 60);
		int minutes = (int) ((time / (1000*60)) % 60);
		int hours   = (int) ((time / (1000*60*60)) % 24);
		int days = (int) ((time / (1000*60*60*24)));
	
		return days + "d " + hours + "h " + minutes + "m " + seconds + "s";
	}
	
	public void updateMotd(){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(this.getDataFolder().getAbsolutePath() + "/motd.txt")));
			String line;
			List<String> options = new ArrayList<>();
			while((line = br.readLine()) != null){
				options.add(line);
			}
			if(options.size() >= 1){
				String ln = options.get((int)(Math.random()*options.size()));
				motd = ChatColor.translateAlternateColorCodes('&', ln);
				motd = motd.replaceAll("%seasonlaunch%", longToTime(release));
			}else{
				motd = ChatColor.translateAlternateColorCodes('&', "\n&7Minecraft Factions Network");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		lastCheck = System.currentTimeMillis();
	}
	
}
