package com.lebastudios.sealcode.iobjects.fxextends.buttons;

import com.lebastudios.sealcode.iobjects.stages.main.MainStageController;
import javafx.event.ActionEvent;
import javafx.scene.control.SplitPane;

public class NotificationsButton extends ButtonManager
{
  @Override
  protected String iconID()
  {
    return "notificaciones.png";
  }

  @Override
  public void onAction(ActionEvent event)
  {
    final var notifications = MainStageController.getInstance().notificationsContainer;
    SplitPane codeTabPaneContainer = MainStageController.getInstance().codeTabPaneContainer;
    final var items = codeTabPaneContainer.getItems();

    if (!items.remove(notifications))
    {
      items.addLast(notifications);
      codeTabPaneContainer.setDividerPosition(items.indexOf(notifications), 0.80);
    }
  }
}
