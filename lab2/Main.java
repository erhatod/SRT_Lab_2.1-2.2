package lab2;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import java.util.Random;

public class Main {
    static final int n = 10;
    static final int N = 256;
    static final int w = 900;
    static final int deltaW = w/n;
    static final Random random = new Random();

    public static void main(String[] args) {
        double[] arrayN = new double[N];
        for (int i = 0; i < N; i++) {
            arrayN[i] = i;
        }
        double[] signal = genRandomSignal();

        ComplexNum[] complexNums1 = dft(signal, N);
        ComplexNum[] complexNums2 = fft(signal, N);

        double[] module1 = new double[N];
        double[] module2 = new double[N];
        for (int i = 0; i < module1.length; i++) {
            module1[i] = complexNums1[i].getModule();
            module2[i] = complexNums2[i].getModule();
        }

        XYChart chart = QuickChart.getChart("Lab2.1 signal", "N", "x", "x(t)", arrayN, signal);
        new SwingWrapper(chart).displayChart();

        XYChart chart1 = QuickChart.getChart("Lab2.1 DFT", "N", "A", "A(t)", arrayN, module1);
        new SwingWrapper(chart1).displayChart();

        XYChart chart2 = QuickChart.getChart("Lab2.2 FFT", "N", "A", "A(t)", arrayN, module2);
        new SwingWrapper(chart2).displayChart();
    }

    static ComplexNum[] dft(double[] signal, int len) {
        ComplexNum[] complexNums = new ComplexNum[len];
        for (int k = 0; k < len; k++) {
            complexNums[k] = new ComplexNum(0, 0);
            for (int n = 0; n < len; n++) {
                double angle = 2 * Math.PI * n * k / len;
                complexNums[k].add(
                        new ComplexNum(signal[n] * Math.cos(angle),
                                -signal[n] * Math.sin(angle))
                );
            }
        }
        return complexNums;
    }

    static double[] genRandomSignal() {
        double[] x = new double[N];
        for (int i = 0; i < n; i++) {
            int A = random.nextInt(100);
            int q = random.nextInt(100);
            for (int j = 0; j < N; j++) {
                final double sinusoid = A * Math.sin(deltaW * (i + 1) * j + q);
                x[j] += sinusoid;
            }
        }
        return x;
    }

    static ComplexNum[] fft(double[] signal, int len) {
        ComplexNum[] f = new ComplexNum[len];
        for (int k = 0; k < len / 2; k++) {
            ComplexNum f1 = new ComplexNum(0, 0);
            ComplexNum f2 = new ComplexNum(0, 0);
            for (int m = 0; m < len / 2; m++) {
                double angle = 2.0 * Math.PI * m * k / (len / 2.0);
                f1.add(
                        new ComplexNum(signal[2*m] * Math.cos(angle),
                                -signal[2*m] * Math.sin(angle))
                );
                f2.add(
                        new ComplexNum(signal[2*m+1] * Math.cos(angle),
                                -signal[2*m+1] * Math.sin(angle))
                );
            }

            double angle = 2.0 * Math.PI * k / len;
            ComplexNum factor = new ComplexNum(Math.cos(angle), -Math.sin(angle));

            // F(k) = F1(k) + w * F2(k)
            f[k] = ComplexNum.sum(f1, ComplexNum.mul(factor, f2));

            // F(k) = F1(k) - w * F2(k)
            f[k + len/2] = ComplexNum.diff(f1, ComplexNum.mul(factor, f2));
        }
        return f;
    }
}