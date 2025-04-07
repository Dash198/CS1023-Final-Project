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
}
