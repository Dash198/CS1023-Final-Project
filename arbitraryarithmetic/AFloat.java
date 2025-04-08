package arbitraryarithmetic;

public class AFloat {
    private String value;

    public AFloat(){
        this.value = "0.0";
    }

    public AFloat(String s){
        this.value = addZeroes(s);
    }

    public AFloat(AFloat other){
        this.value = other.value;
    }

    private String addZeroes(String val){
        if(!(val.contains("."))){
            val += ".0";
        }
        return val;
    }

    public static AFloat parse(String s){
        return new AFloat(s);
    }

    public String getValue(){
        return this.value;
    }

    private boolean isGreater(AFloat other){
        String[] x1 = this.value.split("\\.");
        String[] x2 = other.value.split("\\.");

        if(x1[0].length()>x2[0].length()){
            return true;
        }

        int min = (x1[1].length()<x2[1].length()?x1[1].length():x2[1].length());
        for(int i = x1[0].length()-1;i>=0;i--){
            if(x1[0].charAt(i) > x2[0].charAt(i)){
                return true;
            }
            else if(x2[0].charAt(i) > x1[0].charAt(i)){
                return false;
            }
        }
        for(int i = 0;i<min;i++){
            if(x1[1].charAt(i) > x2[1].charAt(i)){
                return true;
            }
            else if(x2[1].charAt(i) > x1[1].charAt(i)){
                return false;
            }
        }
        
        return (min==x1[1].length()?false:true);
    }

    private String trimZeroes(String s){
        
        String[] x = s.split("\\.");
        String result="";
        if(x[0].equals("0")||x[0].equals("-0")){
            result = "0";
        }
        else{
            int n = x[0].length();
            int index = 0;
            while(index<n && x[0].charAt(index)=='0'){
                index++;
            }
            if(index==x[0].length()){
                result = "0";
            }
            else{
                result = x[0].substring(index);
            }
        }

        result += ".";

        if(x[1].equals("0")){
            result += "0";
        }
        else{
            int n = x[1].length();
            int index = n-1;
            while(index>0 && x[1].charAt(index)=='0'){
                index--;
            }
            if(index==0){
                result += "0";
            }
            else{
                result += x[1].substring(0, index+1);
            }
        }

        return result;
    }

    private boolean isZero(){
        String s = trimZeroes(this.value);
        return (s.equals("0.0") || s.equals("-0.0"));
    }

    private boolean isNeg(){
        return this.value.charAt(0) == '-';
    }

    private AFloat makePos(){
        return AFloat.parse(this.value.substring(1));
    }

    private String addString(String x1, String x2){
        String[] a = x1.split("\\.");
        String[] b = x2.split("\\.");

        String result = "";

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

        result = "." + result;

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

        return trimZeroes(result);
    }

    public String subtractString(String a, String b){
        String[] x1 = a.split("\\.");
        String[] x2 = b.split("\\.");

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

        boolean carrying = false;
        for(int i=n1-1;i>=0;i--){
            int a1 = o1.charAt(i) - '0';
            int a2 = o2.charAt(i) - '0';

            if(carrying){
                a1--;
                carrying = false;
            }

            if(a1<a2){
                carrying = true;
                a1 += 10;
            }

            int dif = a1 - a2;
            result = Integer.toString(dif) + result;
        }

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

            if(carrying){
                a1--;
                carrying = false;
            }

            if(a1<a2){
                carrying = true;
                a1 += 10;
            }

            int dif = a1 - a2;
            result = Integer.toString(dif) + result;
        }

        return trimZeroes(result);
    }

    public AFloat add(AFloat other){
        if(this.isZero() && other.isZero()){
            return new AFloat();
        }
        else if(!(this.isNeg() || other.isNeg())){
            return AFloat.parse(addString(this.value, other.value));
        }
        else if(this.isNeg() && !other.isNeg()){
            return other.subtract(this.makePos());
        }
        else if(!this.isNeg()){
            return this.subtract(other.makePos());
        }
        else{
            return AFloat.parse("-" + this.makePos().add(other.makePos()).value);
        }
    }

    public AFloat subtract(AFloat other){
        if(this.isZero() && other.isZero()){
            return new AFloat();
        }
        else if(!(this.isNeg() || other.isNeg())){
            if(this.isGreater(other)){
                return AFloat.parse(subtractString(this.value, other.value));
            }
            else{
                return AFloat.parse("-" + subtractString(other.value, this.value));
            }
        }
        else if(this.isNeg() && !other.isNeg()){
            return AFloat.parse("-" + this.makePos().add(other).value);
        }
        else if(!this.isNeg()){
            return this.add(other.makePos());
        }
        else{
            return other.subtract(this.makePos());
        }
    
    }

    public AFloat multiply(AFloat other){
        return new AFloat();
    }

    public AFloat divide(AFloat other){
        return new AFloat();

    }
}
