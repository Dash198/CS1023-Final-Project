package arbitraryarithmetic;

import java.util.InputMismatchException;

public class AFloat {

      /*
     * AFloat
     * 
     * This class aims to achieve infinitely precise float operations, capable of handling
     * an arbitrary amount of digits much bigger than the bounds of the usual float/double data
     * types in Java. This is achieved through storing the numbers as Strings, and then performing
     * the operations digit by digit to obtain the result String. Since there is no limit on the size
     * of a String, we can store as many digits as we want.
     * 
     */

    private String value;       // Private String to store the value of the current object.
    private int divPrecision = 1000;        // Store the max number of decimal places we want to go till during division.

    public AFloat(){
        // Default constructor which initiliases the value of the object to 0.0

        this.value = "0.0";
    }

    public AFloat(String s){
        // This constructor initialises the value in s to this object. The String s must be an integer or a decimal.

        if(s != null){
            int dec = 0;
            for(char c:s.toCharArray()){
                if(!(Character.isDigit(c) || c=='.' || dec<2)){
                    throw new InputMismatchException("Error: Expected input to be a decimal!");
                }
                if(c=='.'){
                    dec++;
                }
            }
            s = addZeroes(s);       // Adds a '.0' if s is an integer.
            s = trimZeroes(s);      // Trim any leading zeroes from the left or any trailing zeroes to the right.
            this.value = addZeroes(s);
        }
        else{
            throw new InputMismatchException("Error: Expected input to be a decimal!");
        }
            
    }

    public AFloat(AFloat other){
        // Copy constructor that initiliases the value of this object to the value of the other object.

        this.value = other.value;
    }

    private String addZeroes(String val){
        // Adds a '.0' to an integer String.

        if(!(val.contains("."))){
            val += ".0";
        }
        return val;
    }

    public static AFloat parse(String s){
        // This function returns an instance of AFloat with the value s.

        return new AFloat(s);
    }

    public String getValue(){
        // Accessor function to return the value of the object.

        return this.value;
    }

    private boolean isGreater(AFloat other){
        // Function that determines whether the value of this object is greater than the other object.

        // Store the integer and fractional parts separately.
        String[] x1 = this.value.split("\\.");
        String[] x2 = other.value.split("\\.");

        // Comparing the lengths of the integer parts, longer one is greater.
        if(x1[0].length()>x2[0].length()){
            return true;
        }
        else if(x1[0].length()<x2[0].length()){
            return false;
        }

        // Digit-wise comparison of the integer parts.
        for(int i = x1[0].length()-1;i>=0;i--){
            if(x1[0].charAt(i) > x2[0].charAt(i)){
                return true;
            }
            else if(x2[0].charAt(i) > x1[0].charAt(i)){
                return false;
            }
        }

        // If both the fractional parts are of different lengths, we will check the digits uptil the end of the smaller one.
        int min = (x1[1].length()<x2[1].length()?x1[1].length():x2[1].length());
        for(int i = 0;i<min;i++){
            if(x1[1].charAt(i) > x2[1].charAt(i)){
                return true;
            }
            else if(x2[1].charAt(i) > x1[1].charAt(i)){
                return false;
            }
        }
        
        // If the digits are equal till then, we will treat the number with greater number of fractional digits as the larger one. Equal case is treated as false.
        return (min==x1[1].length()?false:true);
    }

    private String trimIntZeroes(String s){
        // Function to trim the leading zeroes in an integer.

        if(s.equals("0") || s.equals("-0")){
            return "0";     // Return a zero directly if it's a simple 0.
        }
        else{
            // Check for negativity.
            boolean isNeg = false;
            if(s.charAt(0)=='-'){
                isNeg = true;
                s = s.substring(1);     // Start trimming from the next character.
            }

            int index=0;        // Variable to store the index of the first non-zero digit in the String from the left.
            while(index < s.length() && s.charAt(index)=='0'){
                index++;        // Keep incrementing the index until the end of the String or a non-zero digit is reached.
            }
            if(index==s.length()){
                return "0";     // If the end of the String is reached then the entire number must be zero.
            }
            else{
                return (isNeg?"-":"") + s.substring(index);     // Return the number starting from the first non-zero digit with appropriate signs.
            }
        }
    }

    private String trimZeroes(String s){
        // Function to trim the leading zeroes from the integer part and trailing zeroes from the fractional part in a float.
        
        String[] x = s.split("\\.");        // Store the integer and fractional parts separately,
        String result = "";

        boolean isNeg = false;  // Boolean to check if the number is negative.

        // Trim leading zeroes from the integer part (Not using trimIntZeros directly as it will return 0 for a number of the type -0.x).
        if(x[0].equals("0")){
            result = "0";           // Zero case.
        }
        else{
            // Check for negativity.
            if(x[0].charAt(0)=='-'){
                isNeg = true;
                x[0] = x[0].substring(1);       // Start trimming from the next character.
            }

            // Again, remove the leading zeroes.
            int index=0;
            while(index < x[0].length() && x[0].charAt(index)=='0'){
                index++;
            }
            if(index==x[0].length()){
                result = "0";
            }
            else{
                result = x[0].substring(index);
            }
        }

        result += ".";      // Add the decimal point.

        // Removing trailing zeroes from the fractional part.
        // Pretty much the same logic as removing leading zeroes in the integer part, but here we go right to left instead of left to right.
        if(x[1].equals("0")){
            result += "0";
        }
        else{
            int index=x[1].length()-1;
            while(index >=0 && x[1].charAt(index)=='0'){
                index--;
            }
            if(index==-1){
                result += "0";
            }
            else{
                result += x[1].substring(0,index+1);
            }
        }
        return (isNeg && !result.equals("0.0")?"-":"") + result;        // Negativity check.
    }

    private boolean isZero(){
        // Function to check if the value of the current object is zero.

        String s = trimZeroes(this.value);
        return (s.equals("0.0") || s.equals("-0.0"));
    }

    private boolean isNeg(){
        // Function to check if the value of the curent object is negative.

        return this.value.charAt(0) == '-';
    }

    private AFloat makePos(){
        // Function which removes the leading '-' from the string and returns a new AFloat.

        return AFloat.parse(this.value.substring(1));
    }

    private boolean firstIntIsGreater(String x1, String x2){
        // Another function to compare two integers lengthwise first and then digit-wise.

        if(x1.length() > x2.length()){
            return true;
        }
        else if(x1.length() < x2.length()){
            return false;
        }
        else{
            for(int i=0; i<x1.length();i++){
                if(x1.charAt(i) > x2.charAt(i)){
                    return true;
                }
                else if(x2.charAt(i) > x1.charAt(i)){
                    return false;
                }
            }
            return false;
        }
    }

    private String addIntString(String x1, String x2){
        // Adds two non-negative INTEGERS x1 and x2 and returns their non-negative sum.

        String result = "";     // Empty String where the sum will be stored.
        int n1 = x1.length();
        int n2 = x2.length();     
        int carry = 0;

        // Pad zeroes to the number with lesser digits.
        int diff = (n1<n2?n2-n1:n1-n2);
        String zeroes = "";
        for(int i=0;i<diff;i++){
            zeroes+="0";
        }
        if(n1<n2){
            x1 = zeroes + x1;
            n1 += diff;
        }
        else{
            x2 = zeroes + x2;
            n2 += diff;
        }

        // Digit-wise addition.
        for(int i = n1-1; i>=0; i--){
            int a1 = x1.charAt(i) - '0';
            int a2 = x2.charAt(i) - '0';

            int sum = a1 + a2 + carry;
            carry = sum/10;

            sum%=10;
            result = Integer.toString(sum) + result;
        }

        // Prepend the carry if it is positive.
        if(carry > 0){
            result = Integer.toString(carry) + result;
        }

        // Remove the leading zeroes.
        return trimIntZeroes(result);
    }

    private String addString(String x1, String x2){
        // Adds two non-negative decimals x1 and x2 and returns their non-negative sum.


        // Split the number into its integer and fractional parts.
        String[] a = x1.split("\\.");
        String[] b = x2.split("\\.");

        String result = "";         // Empty String to store the final result.


        // Adding the fractional parts together
        String o1 = a[1];
        String o2 = b[1];

        int n1 = o1.length();
        int n2 = o2.length();

        int diff = (n1<n2?n2-n1:n1-n2);
        String zeroes = "";
        for(int i=0;i<diff;i++){
            zeroes += "0";
        }
        if(n1<n2){
            o1 += zeroes;
            n1 = n2;
        }
        else{
            o2 += zeroes;
            n2 = n1;
        }

        int carry = 0;
        for(int i=n1-1;i>=0;i--){
            int a1 = o1.charAt(i) - '0';
            int a2 = o2.charAt(i) - '0';

            int sum = a1 + a2 + carry;
            carry = sum/10;
            result = Integer.toString(sum%10) + result;
        }

        // Prepend the decimal point, and then add the integer parts together as well.
        result = "." + result;

        // Not using addIntString as the carry from the decimal part needs to be added too.
        // Now add the integer parts and prepend to result.
        o1 = a[0];
        o2 = b[0];

        n1 = o1.length();
        n2 = o2.length();

        diff = (n1<n2?n2-n1:n1-n2);
        zeroes = "";
        for(int i=0;i<diff;i++){
            zeroes += "0";
        }
        if(n1<n2){
            o1 = zeroes + o1;
            n1 = n2;
        }
        else{
            o2 = zeroes + o2;
            n2 = n1;
        }

        for(int i=n1-1;i>=0;i--){
            int a1 = o1.charAt(i) - '0';
            int a2 = o2.charAt(i) - '0';

            int sum = a1 + a2 + carry;
            carry = sum/10;
            result = Integer.toString(sum%10) + result;
        }

        if(carry > 0){
            result = Integer.toString(carry) + result;
        }

        // Trim leading and trailing zeroes from the result.
        return trimZeroes(result);
    }

    private String subtractIntString(String x1, String x2){
        // Subtracts two non-negative INTEGERS x1, x2 where x1 > x2 and returns their difference.

        // Logic is samw as the subtractString function from AInteger class.
        if(x1.equals(x2))
            return "0";     // 0 case.
        String result = "";

        int n1 = x1.length();
        int n2 = x2.length();

        // Pad zeroes to x2 if no. of digits in x2 < n1.
        int diff = n1-n2;
        String zeroes = "";

        for(int i=0;i<diff;i++){
            zeroes += "0";
        }

        x2 = zeroes + x2;
        n2 = n1;

        boolean given = false;

        // Digit-wise subtraction.
        for(int i = n1-1; i>=0; i--){
            int a1 = x1.charAt(i) - '0';
            int a2 = x2.charAt(i) - '0';

            if(given){
                a1 -= 1;
                given = false;
            }

            if(a1<a2 && i>0){
                given = true;
                a1 += 10;
            }

            int d = a1 - a2;
            result = Integer.toString(d)+result;
        }

        // Remove leading zeroes.
        return trimIntZeroes(result);
    }

    public String subtractString(String a, String b){
        // Subtracts two non-negative decimals a, b, where a > b and returns their non-negative difference.

        // Split the numbers' integer and fractional parts.
        String[] x1 = a.split("\\.");
        String[] x2 = b.split("\\.");

        // Start by subtracting the fractional parts digit-wise.
        String result = "";

        String o1 = x1[1];
        String o2 = x2[1];

        int n1 = o1.length();
        int n2 = o2.length();

        int diff = (n1<n2?n2-n1:n1-n2);
        String zeroes = "";
        for(int i=0;i<diff;i++){
            zeroes += "0";            
        }
        if(n1<n2){
            o1 += zeroes;
            n1 = n2;
        }
        else{
            o2 += zeroes;
            n2 = n1;
        }

        boolean given = false;
        for(int i=n1-1;i>=0;i--){
            int a1 = o1.charAt(i) - '0';
            int a2 = o2.charAt(i) - '0';

            if(given){
                a1--;
                given = false;
            }

            if(a1<a2){
                given = true;
                a1 += 10;
            }

            int dif = a1 - a2;
            result = Integer.toString(dif) + result;
        }

        // Add a decimal point and repeat for the integer part (borrowing carries here too).
        result = "." + result;

        o1 = x1[0];
        o2 = x2[0];

        n1 = o1.length();
        n2 = o2.length();

        diff = (n1<n2?n2-n1:n1-n2);
        zeroes = "";
        for(int i=0;i<diff;i++){
            zeroes += "0";
        }
        if(n1<n2){
            o1 = zeroes + o1;
            n1 = n2;
        }
        else{
            o2 = zeroes + o2;
            n2 = n1;
        }

        for(int i=n1-1;i>=0;i--){
            int a1 = o1.charAt(i) - '0';
            int a2 = o2.charAt(i) - '0';

            if(given){
                a1--;
                given = false;
            }

            if(a1<a2){
                given = true;
                a1 += 10;
            }

            int dif = a1 - a2;
            result = Integer.toString(dif) + result;
        }

        // Remove any leading and trailing zeroes from the final result.
        return trimZeroes(result);
    }

    private String multiplyString(String a, String b){
        // Multiplies two non-negative decimals a, b and returns their product.

        // Split the numbers' integer and fractional parts.
        String[] x1 = a.split("\\.");
        String[] x2 = b.split("\\.");

        // Calculate total number of decimal digits = sum of no. of decimal digits of a and b.
        int decimalPoints = x1[1].length() + x2[1].length();

        // Remove the decimal points and multiply them as integers.
        String o1 = x1[0] + x1[1];
        String o2 = x2[0] + x2[1];

        int n1 = o1.length();
        int n2 = o2.length();
        String result = "0";

        for(int i=n2-1;i>=0;i--){
            int a2 = o2.charAt(i) - '0';
            String currProduct = "";
            
            // Mutiply each digit of o1 with i'th digit of o2.
            int carry = 0;
            for(int j = n1-1; j>=0; j--){
                int a1 = o1.charAt(j) - '0';
                int product = a1 * a2 + carry;

                carry = product/10;
                currProduct = Integer.toString(product%10) + currProduct;
            }

            if(carry > 0){
                currProduct = Integer.toString(carry) + currProduct;
            }

            // Add zeroes to the product to adjust power of 10.
            for(int j=0;j<n2-1-i;j++){
                currProduct += "0";
            }

            // Add the product to the final result.
            result = addIntString(result, currProduct);
        }

        // If number of digits in the final product is less than the number of required decimal places,
        // then keep adding zeroes to the left of result until digits = 1 + number of required decimal places.
        while(result.length()<=decimalPoints){
            result = "0" + result;
        }
        int n = result.length();
        result = result.substring(0,n-decimalPoints) + "." + result.substring(n-decimalPoints);     // Insert the decimal point.

        // Remove any leading or trailing zeroes.
        return trimZeroes(result);
    }

    private String divideString(String a, String b){
        // Divides two positive decimals a and b, and returns the result with an arbitrary precision (1000 decimal places for now).

        // Split the numbers' integer and fractional parts.
        String[] x1 = a.split("\\.");
        String[] x2 = b.split("\\.");

        // Store the difference in number of decimal places of the numbers.
        int diff = x1[1].length() - x2[1].length();

        // Remove the decimal places and first divide the numbers are integers.
        String o1 = x1[0] + x1[1];
        String o2 = x2[0] + x2[1];

        // Removing any leading zeroes as it may break the method.
        o1 = trimIntZeroes(o1);
        o2 = trimIntZeroes(o2);

        int n1 = o1.length();

        String result = "0";
        String currDividend = "";

        // Division Loop.
        for(int i=0;i<n1;i++){

            currDividend += o1.charAt(i);       // Append the next digit to the dividend.
            currDividend = trimIntZeroes(currDividend);         // Trim any leading zeroes acquired from the previous iteration.

            // Repeatedly subtract o2 from the dividend untul dividen < o2.
            int q = 0;
            while(firstIntIsGreater(currDividend, o2) || currDividend.equals(o2)){
                currDividend = subtractIntString(currDividend, o2);
                currDividend = trimIntZeroes(currDividend);
                q++;
            }

            // Append the number of iterations.
            result += Integer.toString(q);
        }

        // Going for further precision:
        // Since we aim for say, p decimal places, the number of digits needed to be appended to
        // the result will be p - diff at max since there are already 'diff' decimal points.
        // This is worst case though, obviously the loop will stop if the dividen becomes zero, i.e,
        // the number is fully divided.

        // Next division loop.
        int index = 0;      // Stores the number of extra decimal places needed to go.
        for(; index<this.divPrecision-diff && !currDividend.equals("0");index++){

            // Same logic as the previous division loop, but instead of appending the next
            // digit, we append a zero to the dividend.
            currDividend += "0";
            currDividend = trimIntZeroes(currDividend);
            int q = 0;
            while(firstIntIsGreater(currDividend, o2) || currDividend.equals(o2)){
                currDividend = subtractIntString(currDividend, o2);
                currDividend = trimIntZeroes(currDividend);
                q++;
            }
            result += Integer.toString(q);   
        }

        int n = result.length();
        int decimalPoints = index + diff;       // Total number of decimal digits in the result.
        
        if(decimalPoints>0){
            result = result.substring(0,n-decimalPoints) + "." + result.substring(n-decimalPoints);    // Insert the decimal point.
            result = trimZeroes(result);        // Trim any leading or trailing zeroes.
        }
        else{

            // If number of decimal places is negative, that means we need to keep
            // multiplying the result by 10 until the no. of required decimal places is 0.
            while(decimalPoints < 0){
                result += "0";
                decimalPoints++;
            }
        }
        
        // Return the final result.
        return result;
    }

    public AFloat add(AFloat other){
        // Adds two AFloat objects to give another AFloat sum.

        if(this.isZero() && other.isZero()){
            return new AFloat();                // Zero case - return 0.
        }
        else if(!(this.isNeg() || other.isNeg())){
            return AFloat.parse(addString(this.value, other.value));        // a + b case, a,b >= 0.
        }
        else if(this.isNeg() && !other.isNeg()){
            return other.subtract(this.makePos());              // (-a) + b case.
        }
        else if(!this.isNeg()){
            return this.subtract(other.makePos());              // a + (-b) case.
        }
        else{
            return AFloat.parse("-" + this.makePos().add(other.makePos()).value);       // (-a) + (-b) case.
        }
    }

    public AFloat subtract(AFloat other){
        // Subtracts two AFloat objects to give thir AFloat difference.

        if(this.isZero() && other.isZero()){
            return new AFloat();                // Zero case - a = b = 0.
        }
        else if(!(this.isNeg() || other.isNeg())){      // Both positive case.

            if(this.isGreater(other)){
                return AFloat.parse(subtractString(this.value, other.value));       // a > b : return a - b.
            }
            else{
                return AFloat.parse("-" + subtractString(other.value, this.value));     // b > a : return -(b - a).
            }
        }
        else if(this.isNeg() && !other.isNeg()){
            return AFloat.parse("-" + this.makePos().add(other).value);     // (-a) - b case.
        }
        else if(!this.isNeg()){
            return this.add(other.makePos());           // a - (-b) case.
        }
        else{
            return other.makePos().subtract(this.makePos());        // (-a) - (-b) case.
        }
    
    }

    public AFloat multiply(AFloat other){
        // Multiplies two AFloat objects and returns their AFloat product.

        if(this.isZero() || other.isZero()){
            return new AFloat();        // Zero case - return 0 if either a or b are zero.
        }
        else if(!(this.isNeg() || other.isNeg())){
            return AFloat.parse(multiplyString(this.value, other.value));       // a * b case.
        }
        else if(this.isNeg() && !other.isNeg()){
            return AFloat.parse("-" + this.makePos().multiply(other).value);    // (-a) * b case.
        }
        else if(!this.isNeg()){
            return AFloat.parse("-" + this.multiply(other.makePos()).value);       // a * (-b) case.
        }
        else{
            return this.makePos().multiply(other.makePos());        // (-a) * (-b) case.
        }
    }

    public AFloat divide(AFloat other){
        // Divides the two AFloat objects and returns their AFloat result.

        if(this.isZero() || other.isZero()){
            if(other.isZero()){
                throw new ArithmeticException("Error: Division by zero.");      // if b = 0, then throw an exception.
            }
            else{
                return new AFloat();        // if a = 0, then return 0.
            }
        }
        else if(!(this.isNeg() || other.isNeg())){
            return AFloat.parse(divideString(this.value, other.value));     // a / b case.
        }
        else if(this.isNeg() && !other.isNeg()){
            return AFloat.parse("-" + this.makePos().divide(other).value);      // (-a) / b case.
        }
        else if(!this.isNeg()){
            return AFloat.parse("-" + this.divide(other.makePos()).value);      // a / (-b) case.
        }
        else{
            return this.makePos().divide(other.makePos());      // (-a) / (-b) case.
        }
    }
}
