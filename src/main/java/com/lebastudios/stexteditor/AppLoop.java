package com.lebastudios.stexteditor;

import com.lebastudios.stexteditor.events.AppEvent;
import com.lebastudios.stexteditor.events.IEventMethod;
import javafx.stage.WindowEvent;

public class AppLoop
{
    private static class OnUpdate extends AppEvent {}

    private static final OnUpdate onUpdate = new OnUpdate();
    private static Thread loopThread;

    static void startLoop()
    {
        if (loopThread != null && loopThread.isAlive())
        {
            System.err.println("AppLoop is already running");
            return;
        }

        loopThread = new Thread(AppLoop::loop);
        loopThread.setDaemon(true);
        loopThread.start();
    }

    private static void loop()
    {
        while (true)
        {
            onUpdate.invoke();

            try
            {
                Thread.sleep(1000 / 12);
            }
            catch (Exception ignored) {}
        }
    }

    public static void subscribe(IEventMethod eventMethod)
    {
        onUpdate.addListener(eventMethod);
    }

    public static void unsuscribe(IEventMethod eventMethod)
    {
        onUpdate.removeListener(eventMethod);
    }

    public static void waitAndExecute(IEventMethod eventMethod, long timeToWaitInMiliSecs)
    {
        new Thread(() ->
        {
            try
            {
                Thread.sleep(timeToWaitInMiliSecs);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }

            eventMethod.invoke();
        }).start();
    }
}
