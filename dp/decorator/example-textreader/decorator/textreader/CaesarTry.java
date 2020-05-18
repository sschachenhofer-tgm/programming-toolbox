package decorator.textreader;

public class CaesarTry {

    public static void main(String[] args) {
        String s = String.valueOf(Character.MAX_VALUE) +
                (char) (Character.MAX_VALUE - 1) +
                (char) (Character.MAX_VALUE - 2) +
                (char) (Character.MAX_VALUE - 3);
        String enc = Caesar.encode(s, "1234");

        String dec = Caesar.decode(enc, "1234");

        for (int i = 0; i < s.length(); i++) {
            System.out.println("Original - char [" + i + "]: " + (int) s.charAt(i));
            System.out.println("Encoded  - char [" + i + "]: " + (int) enc.charAt(i));
            System.out.println("Decoded  - char [" + i + "]: " + (int) dec.charAt(i));
            System.out.println();
        }
    }
}
