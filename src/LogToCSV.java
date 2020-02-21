import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogToCSV {
    private FileWriter fw;
    private BufferedWriter pw;
    private String file;

    public LogToCSV(String file) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String newFile = file + "_" +dtf.format(now);

        File checkFile = new File(newFile);

        int i = 1;
        while (checkFile.exists()) {
            newFile = newFile + "-" + i;
            checkFile = new File(newFile);
            i++;
        }
        this.file = newFile + ".csv";
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
