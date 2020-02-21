import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogToCSV extends Observer{
    private FileWriter fw;
    private BufferedWriter pw;
    private String file;

    public LogToCSV(String file) {
        this.file = file;
    }

    public void write(int age, String time) {
        try {
            fw = new FileWriter(this.file, true);
            pw = new BufferedWriter(fw);
            pw.newLine();
            pw.append("Age: " + age + ",Time: " + time);
            pw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
