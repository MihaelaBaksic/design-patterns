package editor;

public class Location implements Comparable<Location>{

    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int x;
    public int y;

    @Override
    public boolean equals(Object o){
        if( !(o instanceof Location) || o == null)
            return false;

        Location other = (Location) o;
        return other.x==this.x && other.y==this.y;
    }

    @Override
    public int compareTo(Location o) {
        if(this.y < o.y)
            return -1;
        else if(this.y > o.y)
            return 1;
        else {
            if(this.x < o.x)
                return -1;
            else if(this.x > o.x)
                return 1;
            else
                return 0;
        }
    }
}