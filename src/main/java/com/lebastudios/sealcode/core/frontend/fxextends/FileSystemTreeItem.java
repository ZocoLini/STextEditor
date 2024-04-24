package com.lebastudios.sealcode.core.frontend.fxextends;

import com.lebastudios.sealcode.config.Session;

import java.io.File;

public final class FileSystemTreeItem extends IconTreeItem<String>
{
    public FileSystemTreeItem(File file)
    {
        super();

        this.setValue(file.getName());
        
        String iconName = file.isDirectory()
                ? "fileDirectory.png"
                : "file" + file.getName().substring(file.getName().lastIndexOf('.') + 1) + ".png";
        this.setIconName(iconName);
    }

    public File getRepresentingFile()
    {
        return new File(concatenatePathWithFather(this.getValue()));
    }

    private String concatenatePathWithFather(String buildedPath)
    {
        if (this.getParent() == null)
        {
            return Session.getStaticInstance().proyectDirectory;
        }

        if (this.getParent().getParent() == null)
        {
            return Session.getStaticInstance().proyectDirectory + "/" + buildedPath;
        }

        return ((FileSystemTreeItem) this.getParent()).concatenatePathWithFather(
                this.getParent().getValue() + "/" + buildedPath
        );
    }

    public void reload()
    {
        this.getChildren().clear();
        this.getChildren().addAll(FileSystemTreeView.createTreeView(getRepresentingFile()).getChildren());
    }
    
    /* OLD FILE SYSTEM MONITORING
    private boolean monitorear = false;
    
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
            if (file.getName().equals(".git") && GlobalConfig.getStaticInstance().userPrefs.ignoreGitDir) continue;

            if (!isRepresented(file))
            {
                // Retraso de la adicción porque no detecta los hijos tan rápido como se detecta el cambio
                try
                {
                    Thread.sleep(1000);
                } catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }

                FileSystemController.getInstance().reloadFileSystem();
            }
        }

        // Eliminamos los archivos o direcotrios que ya no existan
        children.removeIf(childrenTreeItem -> !((FileSystemTreeItem) childrenTreeItem).getRepresentingFile().exists());
    }

    private final IEventMethod monitorearHijos = new IEventMethod()
    {
        final FileSystemTreeItem treeItem = FileSystemTreeItem.this;

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
    
    private boolean isRepresented(File file)
    {
        for (var childrenTreeItem : this.getChildren())
        {
            if (((FileSystemTreeItem) childrenTreeItem).getRepresentingFile().equals(file))
            {
                return true;
            }
        }

        return false;
    }
*/
}
