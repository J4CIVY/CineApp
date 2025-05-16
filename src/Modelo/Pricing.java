package Modelo;

// Clase Precios que demuestra polimorfismo
public class Pricing {
    private double standardPrice;
    private double seniorPrice;
    private double studentPrice;
    private double holidayPrice;

    public Pricing(double standardPrice, double seniorPrice, double studentPrice, double holidayPrice) {
        this.standardPrice = standardPrice;
        this.seniorPrice = seniorPrice;
        this.studentPrice = studentPrice;
        this.holidayPrice = holidayPrice;
    }

    // Método polimórfico para obtener precio según tipo de día
    public double getPrice(String dayType) {
        switch (dayType.toLowerCase()) {
            case "senior":
                return seniorPrice;
            case "student":
                return studentPrice;
            case "holiday":
                return holidayPrice;
            default:
                return standardPrice;
        }
    }

    public double getStandardPrice() {
        return standardPrice;
    }

    public double getSeniorPrice() {
        return seniorPrice;
    }

    public double getStudentPrice() {
        return studentPrice;
    }

    public double getHolidayPrice() {
        return holidayPrice;
    }
}
