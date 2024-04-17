package com.lebastudios.sealcode.frontend.fxextends;

import com.lebastudios.sealcode.applogic.AppLoop;
import com.lebastudios.sealcode.applogic.events.IEventMethod;
import javafx.scene.control.TreeItem;
import javafx.stage.WindowEvent;

import java.io.File;

public final class ProyectTreeItem extends TreeItem<ProyectTreeCellContent>
{
    private boolean monitorear = false;

    public ProyectTreeItem(ProyectTreeCellContent content)
    {
        super(content);

        addEventHandlers();
    }

    private void addEventHandlers()
    {
        this.addEventHandler(WindowEvent.WINDOW_HIDING, (event) -> monitorear = false);

        // Buscamos desactivar el monitoreo cuando el objeto es colapsado. Nos podemos permitir dejar de monitorearlo
        // porque, en el momento que se puede colapsar es que toiene elementos dentro, entonces se podrá expandir 
        // posteriormente a alguna edición, y después de esta expansión se actualizará.
        this.addEventHandler(TreeItem.branchCollapsedEvent(), (event) ->
        {
            if (!event.getTreeItem().equals(this)) return;

            detenerMonitoreo();
        });

        // Buscamos activar el monitoreo cuando el propio objeto es expandido.
        this.addEventHandler(TreeItem.branchExpandedEvent(), (event) ->
        {
            if (!event.getTreeItem().equals(this)) return;

            comenzarMonitoreo();
        });

        // Buscamos activar el monitoreo cuando el padre también está extendido
        AppLoop.waitAndExecute(() ->
        {
            if (this.getParent() == null) return;

            this.getParent().addEventHandler(TreeItem.branchExpandedEvent(), (event) ->
            {
                if (!event.getTreeItem().equals(this.getParent())) return;

                this.comenzarMonitoreo();
            });

            this.getParent().addEventHandler(TreeItem.branchCollapsedEvent(), (event) ->
            {
                if (!event.getTreeItem().equals(this.getParent())) return;

                this.setExpanded(false);
            });

        }, 1000);
    }

    public void detenerMonitoreo()
    {
        monitorear = false;
        AppLoop.unsuscribe(monitorearHijos);
    }

    public void comenzarMonitoreo()
    {
        if (monitorear) return;

        monitorear = true;
        AppLoop.subscribe(monitorearHijos);
    }

    public void actualizarHijos()
    {
        if (monitorear) return;

        actualizar();
    }

    private void actualizar()
    {
        final var representingFile = getRepresentingFile();
        final var children = this.getChildren();

        // Solo comprobamos cambios si se ha modificado el número de hijos. Posibles problemas por parte de esto
        //  si se borrasen y creasen la misma cantidad entre comprobaciones
        if (children.size() == representingFile.listFiles().length) return;

        // Añadimos los archivos o directorios que no esten representados
        for (File file : representingFile.listFiles())
        {
            if (!file.getName().equals(".seal") && file.getName().charAt(0) == '.') continue;

            if (!isRepresented(file))
            {
                // Retraso de la adicción porque no detecta los hijos tan rápido como se detecta el cambio
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }

                children.add(FileSystemTreeView.createTreeView(file));
            }
        }

        // Eliminamos los archivos o direcotrios que ya no existan
        children.removeIf(childrenTreeItem -> !childrenTreeItem.getValue().getRepresentingFile().exists());
    }    private final IEventMethod monitorearHijos = new IEventMethod()
    {
        final ProyectTreeItem treeItem = ProyectTreeItem.this;

        @Override
        public void invoke()
        {
            // Esta comprobación detiene el monitoreo si se cumple que este objeto y el padre están colapsados.
            if (!(treeItem.isExpanded() || treeItem.getParent().isExpanded()))
            {
                detenerMonitoreo();
                return;
            }

            final var representingFile = getRepresentingFile();

            // Si no es direcotrio no continua monitoreando los hijos porque no tiene
            if (!representingFile.isDirectory()) return;

            actualizar();
        }
    };

    public File getRepresentingFile()
    {
        return this.getValue().getRepresentingFile();
    }

    private boolean isRepresented(File file)
    {
        for (var childrenTreeItem : this.getChildren())
        {
            if (childrenTreeItem.getValue().getRepresentingFile().equals(file))
            {
                return true;
            }
        }

        return false;
    }


}
