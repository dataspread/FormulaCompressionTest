package sheets;

import java.io.IOException;
import java.util.function.Function;
import com.github.jferard.fastods.TableRowImpl;
import com.github.jferard.fastods.Table;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFRow;
import creator.Creatable;

public class RunTotalFast implements Creatable {
  
  private static final String FORMULA = "A%d + B%d";

  @Override
  public void createExcelSheet(SXSSFSheet sheet, int rows, Function<Integer, Double> valueGenerator) {
    SXSSFRow row = sheet.createRow(0);
    row.createCell(0).setCellValue(valueGenerator.apply(0));
    row.createCell(1).setCellFormula("A1");    
    for (int r = 1; r < rows; r++) {
      row = sheet.createRow(r);
      row.createCell(0).setCellValue(valueGenerator.apply(r));
      row.createCell(1).setCellFormula(String.format(FORMULA, r + 1, r));
    }
  }

  @Override
  public void createCalcSheet(Table sheet, int rows, Function<Integer, Double> valueGenerator) throws IOException {
    TableRowImpl row = sheet.getRow(0);
    row.getOrCreateCell(0).setFloatValue(valueGenerator.apply(0));
    row.getOrCreateCell(1).setFormula("A1");
    for (int r = 1; r < rows; r++) {
      row = sheet.getRow(r);
      row.getOrCreateCell(0).setFloatValue(valueGenerator.apply(r));
      row.getOrCreateCell(1).setFormula(String.format(FORMULA, r + 1, r));
    }
  }

}
