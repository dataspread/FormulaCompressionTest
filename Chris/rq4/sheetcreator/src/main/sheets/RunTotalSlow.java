package sheets;

import java.io.IOException;
import java.util.function.Function;
import com.github.jferard.fastods.TableRowImpl;
import com.github.jferard.fastods.Table;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFRow;
import creator.Creatable;

public class  RunTotalSlow implements Creatable {

  private static final String FORMULA = "SUM(A1:%s%d)";

  @Override
  public void createExcelSheet(SXSSFSheet sheet, int rows, Function<Integer, Double> valueGenerator) {
    for (int r = 0; r < rows; r++) {
      SXSSFRow row = sheet.createRow(r);
      row.createCell(0).setCellValue(valueGenerator.apply(r));
      row.createCell(1).setCellFormula(String.format(FORMULA, "A", r + 1));
    }
  }

  @Override
  public void createCalcSheet(Table sheet, int rows, Function<Integer, Double> valueGenerator) throws IOException {
    for (int r = 0; r < rows; r++) {
      TableRowImpl row = sheet.getRow(r);
      row.getOrCreateCell(0).setFloatValue(valueGenerator.apply(r));
      row.getOrCreateCell(1).setFormula(String.format(FORMULA, "A", r + 1));
    }
  }

}
