/*     */ package timedcode.timedcodeVanish;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.PluginCommand;
/*     */ import org.bukkit.command.TabCompleter;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public class VanishPlugin extends JavaPlugin implements CommandExecutor, TabCompleter {
/*  24 */   private final Set<Player> vanishedPlayers = new HashSet<>();
/*     */   
/*     */   private String licenseKey;
/*     */   
/*     */   private String language;
/*     */   
/*     */   private boolean autoVanish;
/*     */   
/*     */   private boolean isPremium;
/*     */   
/*     */   public void onEnable() {
/*  35 */     saveDefaultConfig();
/*  36 */     FileConfiguration fileConfiguration = getConfig();
/*  37 */     this.licenseKey = fileConfiguration.getString("license-key", "FREE");
/*  38 */     this.language = fileConfiguration.getString("language", "en");
/*  39 */     this.autoVanish = fileConfiguration.getBoolean("auto-vanish", false);
/*  40 */     this.isPremium = true;
/*  41 */     PluginCommand pluginCommand = getCommand("vanish");
/*  42 */     if (pluginCommand != null) {
/*  43 */       pluginCommand.setExecutor(this);
/*  44 */       pluginCommand.setTabCompleter(this);
/*     */     } 
/*  46 */     if (this.isPremium) {
/*  47 */       PluginCommand pluginCommand1 = getCommand("vanishspeed");
/*  48 */       PluginCommand pluginCommand2 = getCommand("vanishfly");
/*  49 */       if (pluginCommand1 != null)
/*  50 */         pluginCommand1.setExecutor(this); 
/*  51 */       if (pluginCommand2 != null)
/*  52 */         pluginCommand2.setExecutor(this); 
/*     */     } 
/*  54 */     getLogger().info(getMessage("plugin-enabled"));
/*     */   }
/*     */   
/*     */   public void onDisable() {
/*  58 */     getLogger().info(getMessage("plugin-disabled"));
/*     */   }
/*     */   
/*     */   public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString) {
/*  62 */     if (!(paramCommandSender instanceof Player)) {
/*  63 */       paramCommandSender.sendMessage(String.valueOf(ChatColor.RED) + String.valueOf(ChatColor.RED));
/*  64 */       return true;
/*     */     } 
/*  66 */     Player player = (Player)paramCommandSender;
/*  67 */     if (paramCommand.getName().equalsIgnoreCase("vanish")) {
/*  68 */       if (!player.hasPermission("timedcode.vanish")) {
/*  69 */         player.sendMessage(String.valueOf(ChatColor.RED) + String.valueOf(ChatColor.RED));
/*  70 */         return true;
/*     */       } 
/*  72 */       if (this.vanishedPlayers.contains(player)) {
/*  73 */         for (Player player1 : Bukkit.getOnlinePlayers()) {
/*  74 */           player1.showPlayer((Plugin)this, player);
/*  75 */           if (!player1.hasPermission("timedcode.vanish.show"))
/*  76 */             player1.sendMessage(String.valueOf(ChatColor.GRAY) + String.valueOf(ChatColor.GRAY) + " " + player.getName()); 
/*     */         } 
/*  78 */         this.vanishedPlayers.remove(player);
/*  79 */         player.setGameMode(GameMode.SURVIVAL);
/*  80 */         player.setAllowFlight(false);
/*  81 */         player.sendMessage(String.valueOf(ChatColor.GREEN) + String.valueOf(ChatColor.GREEN));
/*  82 */         logVanishEvent(player, "became visible");
/*     */       } else {
/*  84 */         for (Player player1 : Bukkit.getOnlinePlayers()) {
/*  85 */           if (!player1.hasPermission("timedcode.vanish.show")) {
/*  86 */             player1.hidePlayer((Plugin)this, player);
/*  87 */             player1.sendMessage(String.valueOf(ChatColor.GRAY) + String.valueOf(ChatColor.GRAY) + " " + player.getName());
/*     */           } 
/*     */         } 
/*  90 */         this.vanishedPlayers.add(player);
/*  91 */         player.setGameMode(GameMode.SPECTATOR);
/*  92 */         player.sendMessage(String.valueOf(ChatColor.YELLOW) + String.valueOf(ChatColor.YELLOW));
/*  93 */         logVanishEvent(player, "vanished");
/*  94 */         if (this.isPremium) {
/*  95 */           player.setHealth(20.0D);
/*  96 */           player.setFoodLevel(20);
/*  97 */           player.sendMessage(String.valueOf(ChatColor.AQUA) + String.valueOf(ChatColor.AQUA));
/*     */         } 
/*     */       } 
/* 100 */       return true;
/*     */     } 
/* 102 */     if (this.isPremium) {
/* 103 */       if (paramCommand.getName().equalsIgnoreCase("vanishspeed")) {
/* 104 */         if (paramArrayOfString.length != 1) {
/* 105 */           player.sendMessage(String.valueOf(ChatColor.RED) + String.valueOf(ChatColor.RED));
/* 106 */           return true;
/*     */         } 
/*     */         try {
/* 109 */           float f = Float.parseFloat(paramArrayOfString[0]);
/* 110 */           if (f < 1.0F || f > 10.0F) {
/* 111 */             player.sendMessage(String.valueOf(ChatColor.RED) + String.valueOf(ChatColor.RED));
/* 112 */             return true;
/*     */           } 
/* 114 */           player.setWalkSpeed(f / 10.0F);
/* 115 */           player.sendMessage(String.valueOf(ChatColor.AQUA) + String.valueOf(ChatColor.AQUA) + getMessage("vanishspeed-set"));
/* 116 */         } catch (NumberFormatException numberFormatException) {
/* 117 */           player.sendMessage(String.valueOf(ChatColor.RED) + String.valueOf(ChatColor.RED));
/*     */         } 
/* 119 */         return true;
/*     */       } 
/* 121 */       if (paramCommand.getName().equalsIgnoreCase("vanishfly")) {
/* 122 */         boolean bool = !player.getAllowFlight() ? true : false;
/* 123 */         player.setAllowFlight(bool);
/* 124 */         player.sendMessage(String.valueOf(ChatColor.AQUA) + String.valueOf(ChatColor.AQUA));
/* 125 */         return true;
/*     */       } 
/*     */     } 
/* 128 */     return false;
/*     */   }
/*     */   
/*     */   private void logVanishEvent(Player paramPlayer, String paramString) {
/*     */     try {
/* 133 */       FileWriter fileWriter = new FileWriter(new File(getDataFolder(), "vanish.log"), true);
/*     */       try {
/* 135 */         String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " " + paramPlayer.getName() + "\n";
/* 136 */         fileWriter.write(str);
/* 137 */         fileWriter.close();
/* 138 */       } catch (Throwable throwable) {
/*     */         try {
/* 140 */           fileWriter.close();
/* 141 */         } catch (Throwable throwable1) {
/* 142 */           throwable.addSuppressed(throwable1);
/*     */         } 
/* 144 */         throw throwable;
/*     */       } 
/* 146 */     } catch (IOException iOException) {
/* 147 */       getLogger().severe("Failed to log vanish event: " + iOException.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getMessage(String paramString) {
/* 152 */     switch (this.language.toLowerCase()) {
/*     */       case "pl":
/* 154 */         switch (paramString) {
/*     */         
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 184 */         return "Nieznana wiadomoĹ›Ä‡.";
/*     */     } 
/*     */ 
/*     */     
/* 188 */     switch (paramString) {
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 204 */     return "Unknown message.";
/*     */   }
/*     */ }


/* Location:              C:\Users\piotr\Documents\DEV\Projects\JAVA\.decompiled\timedcode vanish-1.0-SNAPSHOT.jar!\timedcode\timedcodeVanish\VanishPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */