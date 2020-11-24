import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class NumIsPrime {

    private static BigInteger minusOne = BigInteger.valueOf(-1);
    private static BigInteger zero = BigInteger.valueOf(0);
    private static BigInteger one = BigInteger.valueOf(1);
    private static BigInteger two = BigInteger.valueOf(2);
    private static BigInteger three = BigInteger.valueOf(3);
    private static BigInteger four = BigInteger.valueOf(4);
    private static BigInteger seven = BigInteger.valueOf(7);
    private static BigInteger eight = BigInteger.valueOf(8);

    private BigInteger testValue = new BigInteger("11");

    private BigInteger firstValue = new BigInteger("127");
    private BigInteger secondValue = new BigInteger("229");
    private BigInteger thirdValue = new BigInteger("1068730636009508351594502543756146938933");
    private BigInteger fourthValue = new BigInteger("36827344265891170462785183768451485148527466669216513489484442921278408525639699");

    private BigInteger firstCarmichael = new BigInteger("349407515342287435050603204719587201");
    private BigInteger secondCarmichael = new BigInteger("12758106140074522771498516740500829830401");


    public NumIsPrime()
    {
        //RubinMiller(testValue);

        System.out.println("    First test value:");
        //FermaTest(firstValue);
        SolovayShtrassenTest(firstValue);
        //RubinMiller(firstValue);

        /*System.out.println("    FermaTest");
        FermaTest(firstCarmichael);
        System.out.println("    SolovayShtrassenTest");
        SolovayShtrassenTest(firstCarmichael);
        System.out.println("    RubinMillerTest");
        RubinMiller(firstCarmichael);*/

        //System.out.println("-----------------------------------------------------------");

        System.out.println("    Second test value:");
        //FermaTest(secondValue);
        SolovayShtrassenTest(secondValue);
        //RubinMiller(secondValue);

        /*System.out.println("    FermaTest");
        FermaTest(secondCarmichael);
        System.out.println("    SolovayShtrassenTest");
        SolovayShtrassenTest(secondCarmichael);
        System.out.println("    RubinMillerTest");
        RubinMiller(secondCarmichael);*/

        //System.out.println("    Third test value:");
        //FermaTest(thirdValue);
        //SolovayShtrassenTest(thirdValue);
        //RubinMiller(thirdValue);

        //System.out.println("    Fourth test value:");
        //FermaTest(fourthValue);
        //SolovayShtrassenTest(fourthValue);
        //RubinMiller(fourthValue);
    }

    public static void main(String[] args) {
        NumIsPrime numIsPrime = new NumIsPrime();
    }

    public BigInteger getRandomBigInteger(BigInteger maxLimit, BigInteger minLimit) {
        BigInteger bigInteger = maxLimit.subtract(minLimit);
        Random randNum = new Random();
        int len = maxLimit.bitLength();
        BigInteger resultBigInteger = new BigInteger(len, randNum);
        if (resultBigInteger.compareTo(minLimit) < 0)
            resultBigInteger = resultBigInteger.add(minLimit);
        if (resultBigInteger.compareTo(bigInteger) >= 0)
            resultBigInteger = resultBigInteger.mod(bigInteger).add(minLimit);
        return resultBigInteger;
    }

    public BigInteger Jacobi(BigInteger a, BigInteger b) {
        if (a.equals(zero)) return zero;
        if (a.equals(one)) return one;

        BigInteger k = zero;
        BigInteger s;

        while (a.mod(two).equals(zero)) {
            k = k.add(one);
            a = a.shiftRight(1);
        }

        if (k.mod(two).equals(zero))//если четное
            s = one;
        else {
            if (b.mod(eight).equals(one) || b.mod(eight).equals(seven))//7 - -1 mod 8
                s = one;
            else s = minusOne;
        }

        if (a.mod(four).equals(three) && b.mod(four).equals(three))
            s = s.multiply(minusOne);

        b = b.mod(a);
        if (a.equals(one))
            return s;
        else return s.multiply(Jacobi(b, a));
    }

    /*public BigInteger Jacobi(BigInteger a, BigInteger n)
    {
        //1
        BigInteger g = one;
        while (true) {
            //2
            if (a.equals(zero)) return zero;
            //3
            if (a.equals(one)) return g;

            //4
            BigInteger k = zero;
            BigInteger s = one;

            while (a.mod(two).equals(zero)) {
                k = k.add(one);
                a = a.shiftRight(1);
            }

            //5
            if (k.mod(two).equals(zero))
                s = one;
            else {
                if (n.equals(one.mod(eight)) || n.equals(seven.mod(eight)))//7 - -1 mod 8
                    s = one;
                else if (n.equals(three.mod(eight)) || n.equals(five.mod(eight)))//5 - -3 mod 8
                    s = minusOne;
            }

            //6
            if (a.equals(one)) return s.multiply(g);

            //7
            if (a.equals(three.mod(four)) && n.equals(three.mod(four)))
                s = s.multiply(minusOne);

            //8
            BigInteger a1 = a;
            a = n.mod(a1);
            n = a1;
            g = g.multiply(s);
        }
    }*/

    public boolean FermaTest(BigInteger number) {

        number = number.abs();
        if(number.equals(two) || number.equals(one))
        {
            System.out.println("Number is prime.");
            return true;
        }

        if(number.mod(two).equals(zero))
        {
            System.out.println("Number isn`t prime.");
            return false;
        }

        int counter = 5;
        ArrayList baseMas = new ArrayList<>();

        while (counter > 0) {

            BigInteger a = getRandomBigInteger(number.subtract(two), two);
            while (baseMas.contains(a))
                a = getRandomBigInteger(number.subtract(two), two);
            BigInteger base = a;
            a = a.modPow(number.subtract(one), number);
            if (a.equals(one))
                baseMas.add(base);
            else {
                System.out.println("Condition violated: pow(a,n-1)modn!=1. Number isn`t prime");
                System.out.println("Base: " + base);
                return false;
            }
            counter--;
        }

        System.out.println("Maybe number is prime.");
        for (Object iterator : baseMas)
        {
            System.out.println("Base: " + iterator);
        }
        return true;
    }

    public boolean SolovayShtrassenTest(BigInteger number){

        number = number.abs();
        if(number.equals(two) || number.equals(one))
        {
            System.out.println("Number is prime.");
            return true;
        }

        if(number.mod(two).equals(zero))
        {
            System.out.println("Number isn`t prime.");
            return false;
        }

        int counter = 5;
        ArrayList baseMas = new ArrayList<>();

        while (counter > 0) {

            BigInteger a = getRandomBigInteger(number.subtract(two), two);
            while (baseMas.contains(a))
                a = getRandomBigInteger(number.subtract(two), two);
            BigInteger base = a;

            a = a.modPow(number.subtract(one).shiftRight(1), number);
            if (!a.equals(one) && !a.equals(number.subtract(one)))
            {
                System.out.println("Condition violated: a != 1 и a != n-1. Number isn`t prime");
                System.out.println("Base: " + base);
                return false;
            }

            BigInteger jacobi = Jacobi(base, number);
            if (jacobi.equals(minusOne))
                    jacobi = number.subtract(one);

            if (!a.equals(jacobi.mod(number))){

                System.out.println("Condition violated: a != Jacobian. Number isn`t prime.");
                System.out.println("Base: " + base);
                return false;
            }
            else baseMas.add(base);

            counter--;
        }

        System.out.println("Maybe number is prime.");
        for (Object iterator : baseMas)
        {
            System.out.println("Base: " + iterator);
        }
        return true;
    }

    public boolean RubinMiller(BigInteger number){

        //1
        BigInteger s = zero;
        BigInteger r = number.subtract(one);

        while (r.mod(two).equals(zero))
        {
            s = s.add(one);
            r = r.shiftRight(1);
        }

        //2
        int counter = 5;
        ArrayList baseMas = new ArrayList<>();

        while (counter > 0) {

            BigInteger a = getRandomBigInteger(number.subtract(two), two);
            while (baseMas.contains(a))
                a = getRandomBigInteger(number.subtract(two), two);
            BigInteger base = a;

            //3
            BigInteger y = a.modPow(r, number);

            //4
            if (!y.equals(one) && !y.equals(number.subtract(one)))
            {
                //4.1
                BigInteger j = one;
                //4.2
                if (j.compareTo(s.subtract(one)) <= 0 && !y.equals(number.subtract(one)))
                {
                    //4.2.1
                    y = y.modPow(two, number);
                    //4.2.2
                    if (y.equals(one))
                    {
                        System.out.println("Condition violated: y == 1. Number isn`t prime");
                        System.out.println("Base: " + base);
                        return false;
                    }
                    //4.2.3
                    j = j.add(one);
                }
                //4.3
                if (!y.equals(number.subtract(one)))
                {
                    System.out.println("Condition violated: y != n-1. Number isn`t prime");
                    System.out.println("Base: " + base);
                    return false;
                }
            }

            baseMas.add(base);
            counter--;
        }

        System.out.println("Maybe number is prime.");
        for (Object iterator : baseMas)
        {
            System.out.println("Base: " + iterator);
        }
        return true;
    }

    /*public boolean RubinMiller(BigInteger number){

        //1
        BigInteger s = zero;
        BigInteger r = number.subtract(one);

        while (r.mod(two).equals(zero))
        {
            s = s.add(one);
            r = r.shiftRight(1);
        }

        //2
        int counter = 5;
        ArrayList baseMas = new ArrayList<>();

        while (counter > 0) {

            BigInteger a = getRandomBigInteger(number.subtract(two), two);
            while (baseMas.contains(a))
                a = getRandomBigInteger(number.subtract(two), two);
            BigInteger base = a;

            //3
            BigInteger y = a.modPow(r, number);

            if (y.equals(one) && y.equals(number.subtract(one)))
            {
                baseMas.add(base);
                continue;
            }

            for (int i = 1; i < s.intValue(); i++)
            {
               y = y.modPow(two, number);

               if (y.equals(one))
               {
                   System.out.println("Number isn`t prime");
                   return false;
               }

               if (y.equals(number.subtract(one)))
                   break;
            }

            if (!y.equals(number.subtract(one)))
            {
                System.out.println("Number isn`t prime");
                return false;
            }
            counter--;
            baseMas.add(base);
        }

        System.out.println("Maybe number is prime.");
        for (Object iterator : baseMas)
        {
            System.out.println("Base: " + iterator);
        }
        return true;
    }*/

}