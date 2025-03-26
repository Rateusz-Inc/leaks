/*    */ package timedcode.timedcodePowitania;
/*    */ 
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class PowitaniaCommand
/*    */   implements CommandExecutor {
/*    */   private final TimedcodePowitania plugin;
/*    */   
/*    */   public PowitaniaCommand(TimedcodePowitania plugin) {
/* 14 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
/* 19 */     if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
/* 20 */       if (sender instanceof org.bukkit.entity.Player && !sender.hasPermission("timedcode.powitania.reload")) {
/* 21 */         sender.sendMessage(String.valueOf(ChatColor.RED) + "Nie masz uprawnień do tej komendy!");
/* 22 */         return true;
/*    */       } 
/*    */       
/* 25 */       this.plugin.reloadPluginConfig();
/* 26 */       sender.sendMessage(String.valueOf(ChatColor.GREEN) + "Konfiguracja została przeładowana!");
/* 27 */       return true;
/*    */     } 
/* 29 */     sender.sendMessage(String.valueOf(ChatColor.RED) + "Użycie: /powitania reload");
/* 30 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\piotr\Documents\DEV\Projects\JAVA\.decompiled\timedcode.powitania-1.0.jar!\timedcode\timedcodePowitania\PowitaniaCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */