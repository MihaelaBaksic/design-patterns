package plugin;

import editor.UndoManager;
import editor.models.ClipboardStack;
import editor.models.TextEditorModel;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class PluginLoader {

    public static List<Plugin> loadPlugins() throws Exception{

        List<Plugin> plugins = new ArrayList<>();

        Class<Plugin> descriptor = (Class<Plugin>) Class.forName("plugin.Statistics");
        Plugin p = (Plugin) descriptor.getConstructor().newInstance();
        plugins.add(p);

        descriptor = (Class<Plugin>) Class.forName("plugin.FirstCapitalized");
        p = (Plugin) descriptor.getConstructor().newInstance();
        plugins.add(p);

        return plugins;
    }

    public static ServiceLoader<Plugin> load(){
        return ServiceLoader.load(Plugin.class);
    }

}
