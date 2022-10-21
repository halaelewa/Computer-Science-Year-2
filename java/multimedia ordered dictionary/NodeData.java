import java.util.ArrayList;

public class NodeData {

    String name;
    ArrayList<MultimediaItem> media;

    public NodeData(String newName) {
        name = newName;
        media = new ArrayList<>();
    }

    public void add(MultimediaItem newItem) {
        media.add(newItem);
    }

    public String getName() {
        return name;
    }

    public ArrayList<MultimediaItem> getMedia() {
        return media;
    }
}
