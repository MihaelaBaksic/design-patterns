package editor;

import java.io.File;

public class FileManager {

    private File file;

    private static FileManager instance;

    public static FileManager getInstance(){
        if(instance==null){
            instance = new FileManager();
        }
        return instance;
    }

    private FileManager(){}

    public boolean fileOpened(){
        return file != null;
    }

    public File getFile(){ return file;}
    public void setFile(File f) { this.file = f;}

}
