import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface FileWriting {

    default void write(String s) {
        try(FileWriter writer = new FileWriter("note.txt", true))
        {
            writer.append('\n');
            SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss,SSS");
            writer.write(s + " time=" + formater.format(new Date()));
            writer.append('\n');
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}