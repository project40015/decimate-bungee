package decimate.bungee.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

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
	
	public void onEnable(){
		BungeeCord.getInstance().getPluginManager().registerListener(this, this);
		updateMotd();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPing(ProxyPingEvent event){
		ServerPing sp = event.getResponse();
		sp.setDescription(ChatColor.YELLOW + "DECIMATEPVP.COM" + ChatColor.GRAY + ":\n" + ChatColor.GRAY + motd);
		if(System.currentTimeMillis()-lastCheck >= 1000*60){
			updateMotd();
		}
	}
	
	public void updateMotd(){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(this.getDataFolder().getAbsolutePath() + "/motd.txt")));
			String line = br.readLine();
			if(line != null){
				motd = ChatColor.translateAlternateColorCodes('&', line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		lastCheck = System.currentTimeMillis();
	}
	
}
