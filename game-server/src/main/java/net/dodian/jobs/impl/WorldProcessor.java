package net.dodian.jobs.impl;

import net.dodian.uber.game.Server;
import net.dodian.uber.game.model.entity.player.Client;
import net.dodian.uber.game.model.entity.player.Player;
import net.dodian.uber.game.model.entity.player.PlayerHandler;
import net.dodian.uber.game.model.player.packets.outgoing.SendMessage;
import net.dodian.utilities.DbTables;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.ResultSet;
import java.sql.Statement;

import static net.dodian.utilities.DotEnvKt.getGameWorldId;
import static net.dodian.utilities.DatabaseKt.getDbConnection;

public class WorldProcessor implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
                if (getGameWorldId() == 1) {
                    try {
                        int players = PlayerHandler.getPlayerCount();
                        Statement statement = getDbConnection().createStatement();
                        String query = "UPDATE " + DbTables.GAME_WORLDS + " SET players = " + players + " WHERE id = " + getGameWorldId();
                        statement.executeUpdate(query);
                        statement.close();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                }
                // no thread table
//                try {
//                    String query = "SELECT * FROM thread WHERE (forumid = '98' || forumid = '99' || forumid = '101') AND visible = '1' ORDER BY threadid DESC";
//                    ResultSet results = getDbConnection().createStatement().executeQuery(query);
//                    if (results.next()) {
//                        int latestNews = results.getInt("threadid");
//                        for (int i = 0; i < PlayerHandler.players.length; i++) {
//                            Client c = ((Client) PlayerHandler.players[i]);
//                            Player p = PlayerHandler.players[i];
//                            if(p != null && c.loadingDone) { //If active player.
//                                if(p.latestNews != latestNews) {
//                                    p.latestNews = latestNews;
//                                    c.send(new SendMessage("[SERVER]: There is a new post on the homepage! type ::news"));
//                                }
//                            }
//                        }
//                    }
//                    results.close();
//                } catch (Exception e) {
//                    System.out.println("Error in checking sql!!" + e.getMessage() + ", " + e);
//                    e.printStackTrace();
//                }
            // no uber3_refunds table
//                try {
//                    for (Player p : PlayerHandler.players) {
//                        if(p != null) {
//                            Client c = ((Client) p);
//                            String query = "SELECT * FROM uber3_refunds WHERE receiver='"+c.dbId+"' AND message='0' AND claimed IS NULL ORDER BY date ASC";
//                            Statement stm = getDbConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
//                            boolean gotResult = stm.executeQuery(query).next();
//                            if(gotResult) {
//                                c.send(new SendMessage("<col=4C4B73>You have some unclaimed items to claim!"));
//                                stm.executeUpdate("UPDATE uber3_refunds SET message='1' where message='0'");
//                            }
//                            stm.close();
//                        }
//                    }
//                } catch (Exception e) {
//                    System.out.println("Error in checking sql!!" + e.getMessage() + ", " + e);
//                    e.printStackTrace();
//                }
                Server.chat.clear(); //Not sure what this do, but empty it just incase!
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("world process ended for some reason...");
            System.out.println(e.getMessage());
        }

    }

}