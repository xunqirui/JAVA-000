package homework.one.entity;

/**
 * FiboCalculate
 * 计算斐波那契数列
 *
 * @author qrXun on 2020/11/9
 */
public class FiboCalculate {

    private int result;

    private final int number;

    public FiboCalculate(int number) {
        this.number = number;
    }

    private int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

    public void calculate(){
        result = fibo(number);
    }

    public int getResult() {
        return result;
    }
}
