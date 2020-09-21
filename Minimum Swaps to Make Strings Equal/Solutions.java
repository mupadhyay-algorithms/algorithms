class Solution {
    public int minimumSwap(String s1, String s2) {
        int x, y;
        x = y = 0;
        if(s1.length() != s2.length())  return -1;
        int[] a = new int[2];
        int l = s1.length();
        for(int i = 0;i<l;i++){
            char s1char = s1.charAt(i), s2char = s2.charAt(i);
            if(s1char == 'x')    x++;
            if(s2char == 'x')    x++;
            if(s1char== 'y')    y++;
            if(s2char == 'y')    y++;
            if(s1char != s2char) {
                if(s1char == 'x'){
                    a[0]++;
                }
                else if(s1char == 'y'){
                    a[1]++;
                }
            }
        }
        if(x % 2 != 0 || y % 2 != 0){
            return -1;
        }
        return ((a[0] / 2) + (a[0] % 2) + (a[1] / 2) + (a[1] % 2));       
    }
}
