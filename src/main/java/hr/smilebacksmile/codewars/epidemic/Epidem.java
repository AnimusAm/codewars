package hr.smilebacksmile.codewars.epidemic;

public class Epidem {

    public static int epidemic(int tm, int n, int s0, int i0, double b, double a) {
        // your code
        double dt = (double)tm/n;
        final Compound start = new Compound();
        start.setS(s0);
        start.setI(i0);

        final Parameters parameters = new Parameters(a, b);

        final Compound end = calculate(1, n, dt, parameters, start);
        return (int) end.getCurrentMaxI();
    }

    private static double nextS(double dt, final Parameters parameters, final Compound previous) {
        return previous.getS() - dt*parameters.getB()*previous.getS()*previous.getI();
    }

    private static double nextI(double dt, final Parameters parameters, final Compound previous) {
        return previous.getI() + dt*previous.getI()*(parameters.getB()*previous.getS() - parameters.getA());
    }

    // S[k+1] = S[k] - dt * b * S[k] * I[k]
    // I[k+1] = I[k] + dt * (b * S[k] * I[k] - a * I[k])
    // R[k+1] = R[k] + dt * I[k] *a
    public static Compound calculate(int k, int n, double dt, final Parameters parameters, final Compound previous) {
        Compound current = new Compound();
        current.setS(nextS(dt, parameters, previous));
        current.setI(nextI(dt, parameters, previous));
        current.setNewMax(previous.getCurrentMaxI());

        if (k < n) {
            current = calculate(k+1, n, dt, parameters, current);
        }
        return current;

    }


    private static class Compound {

        double currentMaxI = 0;

        double s;
        double i;
        double r;

        public double getS() {
            return s;
        }

        public void setS(double s) {
            this.s = s;
        }

        public double getI() {
            return i;
        }

        public void setI(double i) {
            this.i = i;
        }

        public double getR() {
            return r;
        }

        public void setR(double r) {
            this.r = r;
        }

        public void setNewMax(double oldMax) {
            currentMaxI = i > oldMax ? i : oldMax;
        }

        public double getCurrentMaxI() {
            return currentMaxI;
        }

    }

    private static class Parameters {

        double a;
        double b;

        public Parameters(double a, double b) {
            this.a = a;
            this.b = b;
        }

        public double getA() {
            return a;
        }

        public double getB() {
            return b;
        }
    }
}