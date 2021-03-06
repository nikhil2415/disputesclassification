package com.abc.common.utils.math.kernels;

import static com.abc.common.utils.MLUtils.dot;

import java.io.Serializable;

/**
 * The polynomial kernel. k(u, v) = (&gamma; u<sup>T</sup>v - &lambda;)<sup>d</sup>,
 * where &gamma; is the scale of the used inner product, &lambda; the offset of
 * the used inner product, and <i>d</i> the order of the polynomial kernel.
 * 
 * @author Haifeng Li
 */
public class PolynomialKernel implements MercerKernel<double[]>, Serializable {
    private static final long serialVersionUID = 1L;

    private int degree;
    private double scale;
    private double offset;

    /**
     * Constructor with scale 1 and bias 0.
     */
    public PolynomialKernel(int degree) {
        this(degree, 1.0, 0.0);
    }

    /**
     * Constructor.
     */
    public PolynomialKernel(int degree, double scale, double offset) {
        if (degree <= 0) {
            throw new IllegalArgumentException("Non-positive polynomial degree.");
        }

        if (offset < 0.0) {
            throw new IllegalArgumentException("Negative offset: the kernel does not satisfy Mercer's condition.");
        }
        
        this.degree = degree;
        this.scale = scale;
        this.offset = offset;
    }

    @Override
    public String toString() {
        return String.format("Polynomial Kernel (scale = %.4f, offset = %.4f)", scale, offset);
    }

    @Override
    public double k(double[] x, double[] y) {
        if (x.length != y.length)
            throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));

        return Math.pow(scale * dot(x, y) + offset, degree);
    }
}
