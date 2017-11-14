package np.com.ravi.textables.model;

/**
 * Created by ravi on 11/14/17.
 */

public class Textable extends TextableCategory {
    String name;
    String art;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    @Override
    public String toString() {
        return name +'\n'+ art ;
    }
}
