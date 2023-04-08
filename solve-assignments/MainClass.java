public class MainClass {

     public static void main(String[] args) {

		String s1 = "abd(jnb)asdf";
        String s2 = "abdjnbasdf";
        String s3 = "dd(df)a(ghhh)";


        System.out.println("Reversing \"" + s1 + "\" will result in \"" + reverseStringInParent(s1) + "\"" + ", success= " + reverseStringInParent(s1).equals("abd(bnj)asdf") );
        System.out.println("Reversing \"" + s2 + "\" will result in \"" + reverseStringInParent(s2) + "\"" + ", success= " + reverseStringInParent(s2).equals("abdjnbasdf") );
        System.out.println("Reversing \"" + s3 + "\" will result in \"" + reverseStringInParent(s3) + "\"" + ", success= " + reverseStringInParent(s3).equals("dd(fd)a(hhhg)") );



    }

    public static String reverseStringInParent(String string) {

        StringBuilder result = new StringBuilder();
        StringBuilder str = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (c == '(') {
                str.append(result);
                result.setLength(0);
                result.append(')');
            } else if (c == ')') {
                result.append('(');
                result.reverse();
                result.insert(0, str);
                str.setLength(0);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
