package com.zh.cargohub;

public class BotRunner implements Runnable {

    private BotSpec bot;
    private SimulationManager manager;

    public BotRunner(BotSpec bot, SimulationManager manager) {
        this.bot = bot;
        this.manager = manager;
    }

    @Override
    public void run() {
        Hub currentHub = bot.getStartHub();
        boolean insideHub = false;

        try {
            manager.addLog(bot.getId() + " started");

            currentHub.enter(bot.getId(), manager);
            insideHub = true;

            for (Corridor corridor : bot.getRoute()) {
                if (!manager.isRunning()) {
                    break;
                }

                currentHub.leave(bot.getId(), manager);
                insideHub = false;

                manager.addLog(bot.getId() + " travelling on corridor " + corridor.getName());

                Thread.sleep(corridor.getTravelTimeMillis());

                currentHub = corridor.getTo();
                currentHub.enter(bot.getId(), manager);
                insideHub = true;
            }

            manager.addLog(bot.getId() + " finished");

        } catch (InterruptedException e) {
            manager.addLog(bot.getId() + " stopped");
        } finally {
            if (insideHub) {
                currentHub.leave(bot.getId(), manager);
            }

            manager.botFinished();
        }
    }
}