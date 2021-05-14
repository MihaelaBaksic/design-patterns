package editor;


import java.rmi.MarshalException;

public class LocationRange{

    public LocationRange(){
        start = new Location(0,0);
        end = new Location(0, 0);
    }

    public Location start;
    public Location end;

    public boolean isSelected(){
        return !start.equals(end);
    }

    public void unselect(){
        start = new Location(0, 0);
        end = new Location(0, 0);
    }

    public int getRowDistance(){
        return Math.abs(start.y - end.y);
    }

    public Location getMin(){
        return start.compareTo(end) < 0 ? start : end;
    }
    public Location getMax(){
        return start.compareTo(end) >= 0 ? start : end;
    }
}