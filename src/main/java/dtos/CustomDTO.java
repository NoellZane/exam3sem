package dtos;

/**
 *
 * @author Noell Zane
 */
public class CustomDTO {
    
    String joke1;
    String ref1;
    String joke2;
    String ref2;
    String picURL;
    String ref3;

    public CustomDTO(String joke1, String ref1, String joke2, String ref2, String picURL, String ref3) {
        this.joke1 = joke1;
        this.ref1 = ref1;
        this.joke2 = joke2;
        this.ref2 = ref2;
        this.picURL = picURL;
        this.ref3 = ref3;
    }


    public String getJoke1() {
        return joke1;
    }

    public String getRef1() {
        return ref1;
    }

    public String getJoke2() {
        return joke2;
    }

    public String getRef2() {
        return ref2;
    }

    public String getPicURL() {
        return picURL;
    }

    public String getRef3() {
        return ref3;
    }
    

    
}
