public class App {

    public static void main(String... args){
        try{
            String csvPath = args[0];
            int loanRequest = Integer.parseInt(args[1]);
        }catch (Exception e){
            System.out.println("Unable to parse command line parameters [path/file.csv 1000]");
        }
    }
}
