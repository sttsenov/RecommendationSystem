public class Collection {
    private String name;
    private String[] availability;
    private int rating;
    private String aired;
    private String type;
    private String brand;

    public Collection(String name, String[] availability, int rating, String aired, String type, String brand) {
        this.name = name;
        this.availability = availability;
        this.rating = rating;
        this.aired = aired;
        this.type = type;
        this.brand = brand;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getAvailability() {
        return availability;
    }

    public void setAvailability(String[] availability) {
        this.availability = availability;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getAired() {
        return aired;
    }

    public void setAired(String aired) {
        this.aired = aired;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
