package plugin;

import editor.UndoManager;
import editor.models.ClipboardStack;
import editor.models.TextEditorModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class PluginLoader {

    public static List<Plugin> loadPlugins(URLClassLoader loader) throws Exception{

        List<Plugin> plugins = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader("plugin_metadata.in"));

        reader.lines().forEach(
                l -> {
                    Plugin p = null;
                    try {
                        Class<Plugin> descriptor = (Class<Plugin>) loader.loadClass("plugin." + l);
                        p = (Plugin) descriptor.getConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    plugins.add(p);
                }
        );

        return plugins;
    }

}
