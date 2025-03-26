/*    */ package timedcode.timedcodePowitania;
/*    */ 
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ import org.bukkit.event.player.PlayerQuitEvent;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ public final class TimedcodePowitania
/*    */   extends JavaPlugin implements Listener {
/*    */   private FileConfiguration config;
/*    */   
/*    */   public void onEnable() {
/* 18 */     saveDefaultConfig();
/* 19 */     this.config = getConfig();
/* 20 */     getLogger().info(String.valueOf(ChatColor.GREEN) + "PLUGIN ZOSTAŁ POMYŚLNIE ZAŁADOWANY");
/* 21 */     getServer().getPluginManager().registerEvents(this, (Plugin)this);
/* 22 */     getCommand("powitania").setExecutor(new PowitaniaCommand(this));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 27 */     getLogger().info(String.valueOf(ChatColor.RED) + "PLUGIN ZOSTAŁ WYŁĄCZONY");
/*    */   }
/*    */   
/*    */   public void reloadPluginConfig() {
/* 31 */     reloadConfig();
/* 32 */     this.config = getConfig();
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPlayerJoin(PlayerJoinEvent e) {
/* 37 */     Player p = e.getPlayer();
/* 38 */     sendMessage(p, "messages.join", "&aWitaj &e%player% &ana serwerze!");
/*    */     
/* 40 */     if (this.config.getBoolean("settings.log-joins", true)) {
/* 41 */       getLogger().info(p.getName() + " dołączył do serwera.");
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPlayerQuit(PlayerQuitEvent e) {
/* 47 */     Player p = e.getPlayer();
/* 48 */     sendMessage(p, "messages.quit", "&c%player% &copuścił serwer. Do zobaczenia!");
/*    */     
/* 50 */     if (this.config.getBoolean("settings.log-quits", true)) {
/* 51 */       getLogger().info(p.getName() + " opuścił serwera.");
/*    */     }
/*    */   }
/*    */   
/*    */   private void sendMessage(Player player, String configPath, String defaultMessage) {
/* 56 */     String message = this.config.getString(configPath, defaultMessage);
/* 57 */     if (message != null)
/* 58 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("%player%", player.getName()))); 
/*    */   }
/*    */ }


/* Location:              C:\Users\piotr\Documents\DEV\Projects\JAVA\.decompiled\timedcode.powitania-1.0.jar!\timedcode\timedcodePowitania\TimedcodePowitania.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */