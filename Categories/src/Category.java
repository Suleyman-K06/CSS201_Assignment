import java.util.ArrayList;

public class Category implements Nameable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private ArrayList<Craft> crafts;

    public ArrayList<Craft> getCrafts() {
        return crafts;
    }

    public void addCrafts(ArrayList<Craft> crafts) {
        if (crafts.isEmpty()) return;
        
        for (Craft craft : crafts) {
            this.crafts.add(craft);
            craft.setCategory(this);
        }
    }

    public Category(String name, ArrayList<Craft> crafts) {
        this.name = name;
        this.crafts = new ArrayList<>();
        addCrafts(crafts);
    }
}
