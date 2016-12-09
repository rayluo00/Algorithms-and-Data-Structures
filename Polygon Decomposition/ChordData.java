public class ChordData {
    public int i;
    public int j;
    public int k;
    public double val;

    public void initChord () {
        this.i = -1;
        this.j = -1;
        this.k = -1;
        this.val = 0.00;
    }

    public void setI (int inputI) {
        this.i = inputI;
    }

    public void setJ (int inputJ) {
        this.j = inputJ;
    }

    public void setK (int inputK) {
        this.k = inputK;
    }

    public void setVal (double inputVal) {
        this.val = inputVal;
    }
}
