package com.lebastudios.stexteditor;

import com.lebastudios.stexteditor.events.AppEvent;
import com.lebastudios.stexteditor.events.IEventMethod;
import javafx.stage.WindowEvent;

public class AppLoop
{
    private static class OnUpdate extends AppEvent {}

    private static final OnUpdate onUpdate = new OnUpdate();

    private static boolean looping;

    static void startLoop()
    {
        if (looping)
        {
            System.err.println("AppLoop is already running");
            return;
        }

        looping = true;
        new Thread(AppLoop::loop).start();
    }

    private static void loop()
    {
        TextEditorApplication.getStage().addEventHandler(WindowEvent.WINDOW_HIDING, (event) -> looping = false);

        while (looping)
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
