import java.util.*;

public class test {
    public static int fib(int n){
        if (n <= 2) {
            return 1;
        } else {
            return fib(n-1) + fib(n-2);
        }
    }

    public static int fac(int n){
        if (n == 1) {
            return 1;
        } else {
            return (n * fac(n-1));
        }
    }

    public static void subarrays(int[] arr, int n){
        int sz = arr.length;
        int[] sum = new int[sz];
        int curr_sum = 0;
        int src = -1;
        int dst = -1;
        
        for (int i = 0; i < sz; i++) {
            curr_sum += arr[i];
            sum[i] = curr_sum;

            //System.out.println("cs: "+curr_sum);
            
            if (curr_sum > n) {
                for (int j = 0; j < sz; j++) {
                    if (curr_sum - sum[j] == n) {
                        src = j+1;
                        dst = i;
                    }
                }
                
                if (src != -1 && dst != -1) {
                    break;
                }
            }

            else if (curr_sum == n) {
                src = 0;
                dst = i;
                break;
            }
        }
        //System.out.println(src+" "+dst);

        if (src != -1 && dst != -1) {
            for (int k = src; k <= dst; k++) {
                System.out.println(arr[k]);
            }
        } else {
            System.out.println("not possible");
        }
    }

    public static void permutation(String word){
        permutation("", word);
    }

    public static void permutation(String pre, String word){
        int n = word.length();

        System.out.println(pre+" | "+word);

        if (n == 0) {
            System.out.println(pre+"\n");
        } else {
            for (int i = 0; i < n; i++) {
                permutation(pre + word.charAt(i),  word.substring(0,i) + word.substring(i+1,n));
            }
        }
    }

    public static void substrings(String s){
        int l;
        
        l = s.length();

        for (int i = 0; i < l; i++) {
            for (int j = 1; j <= l-i; j++) {
                System.out.println(s.substring(i,i+j));
            }
        }
    }

    public static void hanoiTower (int n) {
        Stack l = new Stack();
        Stack r = new Stack();
        Stack m = new Stack();

        for (int i = n; i > 0; i--) {
            l.push(i);
        }
        l.pop();
        System.out.println("L: "+Arrays.toString(l.toArray())+
                "\nM: "+Arrays.toString(m.toArray())+
                "\nR: "+Arrays.toString(r.toArray()));

        
    
    }

    public static void main (String[] args) {
        //int[] arr = {-10, 0, 2, -2, -20, 10};
        int[] arr = {1, 4, 20, 3, 10, 5};
        //System.out.println(fib(9));
        //System.out.println(fac(5));
        subarrays(arr, 33);
        //permutation("abc");
        //substrings("hello");
        //hanoiTower(5);
    }
}
