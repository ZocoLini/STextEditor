package com.lebastudios.stexteditor.controllers;

import java.util.ArrayList;
import java.util.List;

public abstract class Controller
{
    private static final List<Controller> instanciatedControllers = new ArrayList<>();
    
    public Controller()
    {
        instanciatedControllers.add(this);
    }
}
