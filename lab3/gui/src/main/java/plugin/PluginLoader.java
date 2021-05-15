package plugin;

import java.util.ServiceLoader;

public class PluginLoader {

    public ServiceLoader<Plugin> load(){
        return ServiceLoader.load(Plugin.class);
    }

}
