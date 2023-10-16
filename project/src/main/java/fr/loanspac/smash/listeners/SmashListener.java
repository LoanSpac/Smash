package fr.loanspac.smash.listeners;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.game.GameType;
import fr.loanspac.smash.game.champion.Champion;
import fr.loanspac.smash.game.champion.bario.spell.BarioSpell;
import fr.loanspac.smash.game.champion.marte.Marte;
import fr.loanspac.smash.game.champion.marte.spell.MarteSpell;
import fr.loanspac.smash.game.team.Team;
import fr.loanspac.smash.guis.ChampionGUI;
import fr.loanspac.smash.guis.TeamGUI;
import fr.loanspac.smash.utils.ItemManager;
import fr.loanspac.smash.utils.RepeatingTask;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class SmashListener implements Listener  {

    private MarteSpell marte = new MarteSpell();
    private BarioSpell bario = new BarioSpell();

    /*
    HandlerList.unregisterAll(this); -> Permet d'enregister/désenregister un listener (on/off)
     */

    @EventHandler
    public void onJump(PlayerToggleFlightEvent event) {
        if(!(GameType.getCurrentState().equals(GameType.SMASH))) return;
        Player player = event.getPlayer();
        new RepeatingTask(Smash.instance(), 0, 1) {

            int ticks = 0;
            @Override
            public void run() {
                ticks++;
                if(ticks == 1) {
                    player.setAllowFlight(false);

                    double weightModifier = 1;
                    double jumpModifier = 0.7 / weightModifier;

                    Vector jump = player.getLocation().getDirection().multiply(0.5).setY(jumpModifier);
                    player.setVelocity(jump);
                    player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1f, 0.85f);
                }

                if(player.isOnGround()) {
                    player.setAllowFlight(true);
                    cancel();
                }
            }
        };
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if(!(GameType.getCurrentState().equals(GameType.SMASH))) return;
        Player player = event.getPlayer();
        Action action = event.getAction();

        if(action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) player.getInventory().setHeldItemSlot(0);
    }


    @EventHandler
    public void onSpell(PlayerInteractEvent event) {
        if(!(GameType.getCurrentState().equals(GameType.SMASH))) return;
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Action action = event.getAction();
        Champion champ = Smash.champion.get(player);

        if(item == null) return;
        if((action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) || (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK))) {
            switch(player.getInventory().getHeldItemSlot()) {
                case 1:
                    champ.applyOffensif(player, item);
                    break;
                case 2:
                    champ.applyUtilitaire(player, item);
                    break;
                case 3:
                    champ.applyRecovery(player, item);
                    break;
                default:
                    break;
            }
        }
    }

    /*
    @EventHandler
    public void onHeldSwitch(PlayerItemHeldEvent event) {
        if(!(EGames.getCurrentState().equals(EGames.SMASH))) return;
        Player player = event.getPlayer();

        if (event.getNewSlot() == 1) {
            ChampionSpell spell = Smash.instance().getChampionDistributor().getSpellOfPlayer(player, ChampionSpellType.OFFENSIVE);
            if (spell instanceof ChampionActiveSpell) {
                ChampionActiveSpell activeSpell = (ChampionActiveSpell) spell;
                activeSpell.play(player);
            }
        }

        event.setCancelled(true);
    }
     */

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if(!(GameType.getCurrentState().equals(GameType.SMASH))) return;

        if(event.getDamager() instanceof  Fireball) {
            Fireball fireball = (Fireball) event.getDamager();
            if(event.getEntity().equals(fireball.getShooter())) {
                event.setCancelled(true);
                return;
            }
            if(event.getEntity() instanceof Player) {
                Player damaged = (Player) event.getEntity();

                double damage = 8;
                Smash.player.get(damaged).setPercent(Smash.player.get(damaged).getPercent() + damage);

                double health = Smash.player.get(damaged).getPercent();
                //double weightMod = Smash.player.get(damaged).getWeight();
                double weightMod = 1;
                double kbMod =  Smash.player.get(damaged).getKbmod();

                double multiplier = Math.min(10, damage / 7.5 + (Math.exp(0.0072 * (health - 950)) * (Math.pow(damage, 0.59) * Math.pow(health, 1.3))) * kbMod / (weightMod * 1.1));

                Vector direction = fireball.getDirection();

                direction.setY(((direction.getY() >= 0.45 || direction.getY() <= -0.5) ? direction.getY() : 0.20))
                        .normalize()
                        .setX(direction.getX() * multiplier)
                        .setZ(direction.getZ() * multiplier)
                        .setY(direction.getY() * (multiplier / 1.5));

                damaged.setVelocity(direction);

                return;
            }
        }

        Entity damagedE = event.getEntity();
        Entity damagerE = event.getDamager();

        if(!(damagedE instanceof Player)) return;

        Player damaged = (Player) event.getEntity();

        if(!(damagerE instanceof Player)) return;

        Player damager = (Player) event.getDamager();

        ///////////////////

        //double damage = Math.round(6.5*Smash.player.get(damager).getWeight());
        //double damage = Math.round(6.5*1);

        double damage = 6.5*1;

        if(Smash.champion.get(damager).equals(new Marte())) {
            if(damager.getLocation().distance(damaged.getLocation()) <= 1) {
                damage = 6;
            }
            if(damager.getLocation().distance(damaged.getLocation()) > 1 && damager.getLocation().distance(damaged.getLocation()) <= 2) {
                damage = 6.5;
            }
            if(damager.getLocation().distance(damaged.getLocation()) > 2) {
                damage = 7;
            }
        }

        //System.out.println(damager.getLocation().distance(damaged.getLocation()));
        //double damage = 6.5*1;

        if(bario.getPlanner()) {
            bario.setPlanner(false);
        }

        if(!(marte.getContre())) {
            Smash.player.get(damaged).setPercent(Smash.player.get(damaged).getPercent() + damage);
            smashKb(damaged, damager, damage);
        } else {
            Smash.player.get(damager).setPercent(Smash.player.get(damager).getPercent() + damage);
            smashKb(damager, damager, damage);
            marte.setContre(false);
        }
    }

    private void smashKb(Player damaged, Player damager, Double damage) {
        double health = Smash.player.get(damaged).getPercent();
        //double weightMod = Smash.player.get(damaged).getWeight();
        double weightMod = 1;
        double kbMod =  Smash.player.get(damaged).getKbmod();

        double multiplier = Math.min(10, damage / 7.5 + (Math.exp(0.0072 * (health - 950)) * (Math.pow(damage, 0.59) * Math.pow(health, 1.3))) * kbMod / (weightMod * 1.1));

        Vector direction = damager.getEyeLocation().getDirection();

        if(marte.getContre()) {
            direction = damager.getEyeLocation().getDirection().multiply(-1);
        }

        direction.setY(((direction.getY() >= 0.45 || direction.getY() <= -0.5) ? direction.getY() : 0.20))
                .normalize()
                .setX(direction.getX() * multiplier)
                .setZ(direction.getZ() * multiplier)
                .setY(direction.getY() * (multiplier / 1.5));

        damaged.setVelocity(direction);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(!(GameType.getCurrentState().equals(GameType.SMASH))) return;
        event.setDamage(0);
    }







    @EventHandler
    public void onFireTick(EntityDamageEvent event){
        if(!(GameType.getCurrentState().equals(GameType.WAITING))) return;
        if(event.getCause().equals(EntityDamageEvent.DamageCause.FIRE) || event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)){
            event.setCancelled(true);
        }
    }

    /*
    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(!(EGames.getCurrentState().equals(EGames.WAITING))) return;
        event.setCancelled(true);
    }
     */

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(!(GameType.getCurrentState().equals(GameType.WAITING))) return;
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Action action = event.getAction();
        if(item == null) return;
        boolean rightClick = (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK));

        if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§4Retour Lobby") && rightClick) {
            player.kickPlayer("§x§1§f§f§b§f§4§l§nS§x§2§1§e§d§f§5§l§nk§x§2§3§d§e§f§6§l§ni§x§2§5§d§0§f§6§l§nb§x§2§6§c§2§f§7§l§ni§x§2§8§b§4§f§8§l§nd§x§2§a§a§5§f§9§l§ni §x§2§c§9§7§f§a§l§nS§x§2§e§8§9§f§b§l§nk§x§3§0§7§a§f§b§l§ni§x§3§2§6§c§f§c§l§nb§x§3§3§5§e§f§d§l§ni§x§3§5§4§f§f§e§l§nd§x§3§7§4§1§f§f§l§ni §x§3§d§3§c§f§e§l§nS§x§4§7§3§f§f§c§l§nk§x§5§1§4§3§f§a§l§ni§x§5§b§4§6§f§8§l§nb§x§6§5§4§a§f§6§l§ni§x§6§f§4§e§f§4§l§nd§x§7§9§5§1§f§2§l§ni §x§8§4§5§5§e§f§l§nS§x§8§e§5§8§e§d§l§nk§x§9§8§5§c§e§b§l§ni§x§a§2§5§f§e§9§l§nb§x§a§c§6§3§e§7§l§ni§x§b§6§6§6§e§5§l§nd§x§c§0§6§a§e§3§l§ni");
            //HEX 1 : #1FFBF4
            //HEX 2 : #383AFF
            //HEX 3 : #C06AE3
        }
        if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§6Main") && rightClick) {
            new ChampionGUI().renderGUI(player);
        }
        if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§6Team") && rightClick) {
            new TeamGUI().renderGUI(player);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) throws InstantiationException, IllegalAccessException {
        if(!(GameType.getCurrentState().equals(GameType.WAITING))) return;
        Player player = (Player)event.getWhoClicked();
        Inventory inv = event.getInventory();
        ItemStack current = event.getCurrentItem();
        if(current == null) return;

        if(inv.getName().equalsIgnoreCase("§8Sélection de champion")) {
            new ChampionGUI().clickGUI(player, current);
        }
        if(inv.getName().equalsIgnoreCase("§8Team")) {
            new TeamGUI().clickGUI(player, current);
        }
    }

    @EventHandler
    public static void onConnect(PlayerJoinEvent event){
        if(!(GameType.getCurrentState().equals(GameType.WAITING))) return;
        Player player = event.getPlayer();

        Smash.team.put(player, Team.NEUTRAL);

        ItemStack mainMenu = ItemManager.getItem(Material.NETHER_STAR, "§6Main", false, false);
        player.getInventory().setItem(0, mainMenu);

        ItemStack team = ItemManager.getItem(Material.WOOL, "§6Team", false, false);
        player.getInventory().setItem(4, team);

        ItemStack lobby = ItemManager.getItem(Material.DARK_OAK_DOOR_ITEM, "§4Retour Lobby", false, false);
        player.getInventory().setItem(8, lobby);

        player.updateInventory();
    }
}
