/*    */ package timedcode.timedcodeVanish;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ import org.bukkit.event.player.PlayerQuitEvent;
/*    */ 
/*    */ public class VanishListener
/*    */   implements Listener
/*    */ {
/*    */   @EventHandler
/*    */   public void onPlayerJoin(PlayerJoinEvent event) {
/* 14 */     Player player = event.getPlayer();
/* 15 */     if (player.hasPermission("timedcode.vanish.auto")) {
/* 16 */       player.performCommand("vanish");
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPlayerQuit(PlayerQuitEvent event) {
/* 22 */     Player player = event.getPlayer();
/*    */   }
/*    */ }


/* Location:              C:\Users\piotr\Documents\DEV\Projects\JAVA\.decompiled\timedcode vanish-1.0-SNAPSHOT.jar!\timedcode\timedcodeVanish\VanishListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */