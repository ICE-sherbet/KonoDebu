package white.ices.konoa77kg;

public class CFat implements IKonoFat{
    public static int thickness = 0;

    public int getThickness() {
        return thickness;
    }

    public void addThickness(int levelToAdd) {
        if (thickness < 80)
            thickness += levelToAdd;
    }

    public void removeThickness(int levelToRemove) {
        if (thickness <= 70 && levelToRemove == -10) {
            thickness -= levelToRemove;
        } else if (thickness <= 75 && levelToRemove == -5) {
            thickness -= levelToRemove;
        } else if (thickness <= 80 && thickness > 70 && (levelToRemove == -5 || levelToRemove == -10)) {
            thickness = 80;
        } else if (thickness <= 80 && levelToRemove > 0) {
            thickness -= levelToRemove;
        }
    }

    public void setThickness(int levelToSet) {
        if (levelToSet <= 80) {
            thickness = levelToSet;
        } else {
            thickness = 80;
        }

    }
}
