package pl.dabrowski.kantor.entity;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;


public class RatePerYear {

    @Indexed(unique=true, direction= IndexDirection.DESCENDING, dropDups = true)
    private int id;

    private String year;

    private double rateForYear;

    public RatePerYear() {
    }

    public RatePerYear(int id, double averageForYear) {
        this.id = id;
        this.year = String.valueOf(id);
        this.rateForYear = averageForYear;
    }
    public RatePerYear(int id,String year, double averageForYear) {
        this.id = id;
        this.year = year;
        this.rateForYear = averageForYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public double getAverageForYear() {
        return rateForYear;
    }

    public void setAverageForYear(double averageForYear) {
        this.rateForYear = averageForYear;
    }
}
