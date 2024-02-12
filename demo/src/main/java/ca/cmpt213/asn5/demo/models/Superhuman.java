package ca.cmpt213.asn5.demo.models;

public class Superhuman {
    static private int index = 1;
    private long id;
    private String name;
    private double weight;
    private double height;
    private String pictureUrl;
    private String category;
    private int overallAbility;

    // Constructors
    public Superhuman(long id, String name, double weight, double height, String pictureUrl, String category, int overallAbility) {
        this.id = index++;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.pictureUrl = pictureUrl;
        this.category = category;
        this.overallAbility = overallAbility;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getOverallAbility() {
        return overallAbility;
    }

    public void setOverallAbility(int overallAbility) {
        this.overallAbility = overallAbility;
    }

    //Testing purposes
    @Override
    public String toString() {
        return "Superhuman{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", category='" + category + '\'' +
                ", overallAbility=" + overallAbility +
                '}';
    }
}

