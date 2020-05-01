package lab2;

public class ComplexNum {
    private double real;
    private double imaginary;

    ComplexNum(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    double getModule() {
        return Math.hypot(real, imaginary);
    }

    void add(ComplexNum c) {
        this.real += c.real;
        this.imaginary += c.imaginary;
    }

    static ComplexNum diff(ComplexNum a, ComplexNum b) {
        double real = a.real - b.real;
        double imaginary = a.imaginary - b.imaginary;
        return new ComplexNum(real, imaginary);
    }

    static ComplexNum mul(ComplexNum a, ComplexNum b) {
        double real = a.real * b.real - a.imaginary * b.imaginary;
        double imaginary = a.real * b.imaginary + a.imaginary * b.real;
        return new ComplexNum(real, imaginary);
    }

    static ComplexNum sum(ComplexNum a, ComplexNum b) {
        double real = a.real + b.real;
        double imaginary = a.imaginary + b.imaginary;
        return new ComplexNum(real, imaginary);
    }
}