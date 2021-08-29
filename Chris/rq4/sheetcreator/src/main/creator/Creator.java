package creator;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.github.jferard.fastods.AnonymousOdsFileWriter;
import com.github.jferard.fastods.OdsFactory;
import com.github.jferard.fastods.Table;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.io.File;

import java.util.function.Function;
import java.util.logging.Logger;
import java.util.Locale;

public abstract class Creator {

    private static final OdsFactory odsFactory = OdsFactory.create(Logger.getLogger("logger"), Locale.US);

    public static void createExcelSheet (Creatable createable, String path, int rows, Function<Integer, Double> valueGenerator) {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(1)) {
            String filename = Path.of(path, "ROWS-" + rows + ".xlsx").toString();
            if (!(new File(filename)).exists()) {
                workbook.setCompressTempFiles(true);
                SXSSFSheet sheet = workbook.createSheet("Sheet1");
                createable.createExcelSheet(sheet, rows, valueGenerator);
                Creator.saveWorkbook(workbook, filename);
                workbook.dispose();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createCalcSheet (Creatable createable, String path, int rows, Function<Integer, Double> valueGenerator) {
        File filename = Path.of(path, "ROWS-" + rows + ".ods").toFile();
        if (!filename.exists()) {
            try {
                final AnonymousOdsFileWriter writer = Creator.odsFactory.createWriter();
                Table sheet = writer.document().addTable("Sheet1");
                createable.createCalcSheet(sheet, rows, valueGenerator);
                writer.saveAs(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveWorkbook (Workbook wb, String name) {
        try (FileOutputStream fileOut = new FileOutputStream(name)) {
            wb.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
