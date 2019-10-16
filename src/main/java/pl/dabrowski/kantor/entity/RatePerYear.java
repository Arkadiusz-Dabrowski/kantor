package pl.dabrowski.kantor.entity;

import org.springframework.data.annotation.Id;

import java.util.Date;


public class RatePerYear {

    @Id
    int id;

    String year;

    double rateForYear;

    public RatePerYear() {
    }

    public RatePerYear(int id, String year, double averageForYear) {
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
