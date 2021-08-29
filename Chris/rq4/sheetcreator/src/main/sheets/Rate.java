package sheets;

import java.io.IOException;
import java.util.function.Function;
import com.github.jferard.fastods.TableRowImpl;
import com.github.jferard.fastods.Table;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFRow;
import creator.Creatable;

public class Rate implements Creatable {

  private static final String FORMULA = "$A$1 * B%d";

  @Override
  public void createExcelSheet(SXSSFSheet sheet, int rows, Function<Integer, Double> valueGenerator) {
    SXSSFRow row = sheet.createRow(0);
    row.createCell(0).setCellValue(valueGenerator.apply(0));
    row.createCell(1).setCellValue(valueGenerator.apply(0));
    row.createCell(2).setCellFormula(String.format(FORMULA, 1));
    for (int r = 1; r < rows; r++) {
      row = sheet.createRow(r);
      row.createCell(1).setCellValue(valueGenerator.apply(r));
      row.createCell(2).setCellFormula(String.format(FORMULA, r + 1));
    }
  }

  @Override
  public void createCalcSheet(Table sheet, int rows, Function<Integer, Double> valueGenerator) throws IOException {
    TableRowImpl row = sheet.getRow(0);
    row.getOrCreateCell(0).setFloatValue(valueGenerator.apply(0));
    row.getOrCreateCell(1).setFloatValue(valueGenerator.apply(0));
    row.getOrCreateCell(2).setFormula(String.format(FORMULA, 1));
    for (int r = 1; r < rows; r++) {
      row = sheet.getRow(r);
      row.getOrCreateCell(0).setFloatValue(valueGenerator.apply(r));
      row.getOrCreateCell(1).setFormula(String.format(FORMULA, r + 1));
    }
  }

}
