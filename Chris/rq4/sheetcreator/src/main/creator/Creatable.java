package creator;

import org.apache.poi.xssf.streaming.SXSSFSheet;
import com.github.jferard.fastods.Table;
import java.io.IOException;
import java.util.function.Function;

public interface Creatable {

    public void createExcelSheet (SXSSFSheet sheet, int rows, Function<Integer, Number> valueGenerator);
    public void createCalcSheet  (Table      sheet, int rows, Function<Integer, Number> valueGenerator) throws IOException;

}
