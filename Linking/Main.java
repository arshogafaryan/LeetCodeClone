public class Main {
    public static void main(String[] args) {
        int num1 = Integer.parseInt(args[0]);
        int num2 = Integer.parseInt(args[1]);

        Solution solution = new Solution();
        int result = solution.sumOfTwoNumbers(num1, num2);

        System.out.println(result);
    }
}
