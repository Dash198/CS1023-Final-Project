package arbitraryarithmetic;

public class AInteger {
    private String value;

    AInteger(){
        this.value = "0";
    }

    public AInteger(String s){
        this.value = s;
    }

    public AInteger(AInteger other){
        this.value = other.value;
    }

    public AInteger parse(String s){
        return new AInteger(s);
    }

    public String getValue(){
        return this.value;
    }

    public boolean isGreater(AInteger other){
        String x1 = this.value;
        String x2 = other.value;

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

    public AInteger add(AInteger other){
        if(other.value.charAt(0)!='-'){
            String result = "";

            String x1 = this.value;
            String x2 = other.value;

            int n1 = x1.length(), n2 = x2.length();
            int carry = 0;

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

            for(int i = n1-1; i>=0; i--){
                int a1 = x1.charAt(i) - '0';
                int a2 = x2.charAt(i) - '0';

                int sum = a1 + a2 + carry;
                carry = sum/10;
                
                sum%=10;
                result = Integer.toString(sum) + result;
            }

            if(carry > 0){
                result = Integer.toString(carry) + result;
            }

            return new AInteger(result);
        }
        else{
            return subtract(new AInteger(other.value.substring(1)));
        }
    }

    public AInteger subtract(AInteger other){
        if(other.value.charAt(0)=='-'){
            return this.add(new AInteger(other.value.substring(1)));
        }
        else{
            if(this.isGreater(other)){
                String result = "";

                String x1 = this.value;
                String x2 = other.value;

                int n1 = x1.length();
                int n2 = x2.length();

                int diff = (n1<n2?n2-n1:n1-n2);
                String zeroes = "";

                for(int i=0;i<diff;i++){
                    zeroes += "0";
                }

                if(n1<n2){
                    x1 = zeroes + x1;
                    n1 = n2;
                }
                else{
                    x2 = zeroes + x2;
                    n2 = n1;
                }

                boolean carrying = false;

                for(int i = n1-1; i>=0; i--){
                    int a1 = x1.charAt(i) - '0';
                    int a2 = x2.charAt(i) - '0';

                    if(carrying){
                        a1 -= 1;
                        carrying = false;
                    }

                    if(a1<a2 && i>0){
                        carrying = true;
                        a1 += 10;
                    }

                    int d = a1 - a2;
                    result = Integer.toString(d)+result;
                }

                int index = 0;
                while(index<result.length() && result.charAt(index)=='0'){
                    index++;
                }

                result = result.substring(index);

                return new AInteger(result);
            }
            else if(this.value.equals(other.value)){
                return new AInteger();
            }
            else{
                return new AInteger('-' + other.subtract(this).getValue());
            }
        }
    }

    public AInteger multiply(AInteger other){
        String result = "";
        return new AInteger(result);
    }

    public AInteger divide(AInteger other){
        String result = "";
        return new AInteger(result);
    }
}