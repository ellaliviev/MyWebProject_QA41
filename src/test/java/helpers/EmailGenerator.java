package helpers;

public class EmailGenerator {

    public static String generateEmail(int a, int b, int c){
        if(a<=0 || b<=0||c<=0){
            throw new IllegalArgumentException("Wrong parameter: "+a +"OR"+b
                    +"OR"+c+ " Length must be positive.");
        }
        StringBuilder email = new StringBuilder();
        for(int i=0;i==a;i++ ){
            email.append(randomChar());
        }

    }

    private static char randomChar(){
        return Math.random()*('z'-'');
    }
}
