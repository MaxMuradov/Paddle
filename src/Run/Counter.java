package Run;

/**
 * Simple counter class.
 */
public class Counter {
    private int number;

    /**
     * Constructor.
     */
    public Counter() {
        this.number = 0;
    }

    /**
     * increase by one.
     */
    public void increase() {
        ++number;
    }

    /**
     * increase by given num.
     * @param num
     */
    public void increase(int num) {
        number += num;
    }

    /**
     * decrease by one.
     */
    public void decrease() {
        --number;
    }

    /**
     * decrease by given number.
     * @param num
     */
    public void decrease(int num) {
        number -= num;
    }
    /**
     * get current count.
     * @return current count
     */
    public int getValue() {
        return number;
    }
}