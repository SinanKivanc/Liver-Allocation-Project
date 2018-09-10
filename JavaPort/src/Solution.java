
import java.util.HashMap;

public class Solution {
    
    //main to test the method
    public static void main(String args[]){
        
        String str = "aaba kouq bux";
        System.out.println(removeChar(str,1));
        System.out.println(removeChar(str,2));
        System.out.println(removeChar(str,3));
        System.out.println(removeChar(str,4));
        System.out.println("TEST BREAK");
        str="AAAAAAAAAAA";
        System.out.println(removeChar(str,1));
        System.out.println(removeChar(str,2));
        System.out.println(removeChar(str,3));
        System.out.println(removeChar(str,4));
        System.out.println("TEST BREAK");
        str="AAAAAAAAAAAbbb";
        System.out.println(removeChar(str,1));
        System.out.println(removeChar(str,2));
        System.out.println(removeChar(str,3));
        System.out.println(removeChar(str,4));
        System.out.println("TEST BREAK");
        str="abbcccdddd";
        System.out.println(removeChar(str,1));
        System.out.println(removeChar(str,2));
        System.out.println(removeChar(str,3));
        System.out.println(removeChar(str,4));
        System.out.println("TEST BREAK");
        str="ab b c ccddd deff";
        System.out.println(removeChar(str,1));
        System.out.println(removeChar(str,2));
        System.out.println(removeChar(str,3));
        System.out.println(removeChar(str,4));
        System.out.println("TEST BREAK");
    }
    
    public static String removeChar(String str, int n){
        
        //base cases
        if(str.equals(null)){
            return null;
        }
        if(n <=1){
            return null;
        }
        
        //create a map to store occurences
        HashMap<Character,Integer> occurences = new HashMap<Character,Integer>();
        
        //initialize the map
        for(char i :str.toCharArray()){
            //System.out.println(i);
            if(i !=' ') {  
                if(occurences.containsKey(i)){
                    occurences.put(i, occurences.get(i)+1);
                    //System.out.println(i);
                    //remove all occurences of a charachter if it occurs more than n times
                    if(occurences.get(i) >=n){
                        str =str.replace(Character.toString(i),"");
                        if(str.length()==0){
                            return null;
                        }
                    }
                } else {
                    occurences.put(i, 1);
                }
            }

        }
        
        //get rid of the first space if there is any
        if(str.charAt(0)==' '){
            str=str.substring(1);
        }
        
        
        return str;
        
    }
}
