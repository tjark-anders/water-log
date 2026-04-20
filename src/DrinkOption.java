import javax.swing.ImageIcon;

public class DrinkOption {

    private String name;
    private int size;
    private int waterP;
    private ImageIcon icon;

    public DrinkOption(String name, ImageIcon icon, int size, int waterP) {
        this.name = name;
        this.size = size;
        this.waterP = waterP;
        this.icon = icon;
    }

    // Getter & Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getWaterP() {
        return waterP;
    }

    public void setWaterP(int waterP) {
        this.waterP = waterP;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
}
