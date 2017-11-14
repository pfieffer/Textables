package np.com.ravi.textables.model;

/**
 * Created by ravi on 11/14/17.
 */

public class TextableCategory {
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return category;
    }
}
