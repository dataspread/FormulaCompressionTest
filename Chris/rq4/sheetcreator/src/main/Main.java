import java.util.concurrent.ExecutorService;
import java.io.FileInputStream;
import java.util.Properties;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;
import java.io.File;

import creator.*;
import sheets.*;
import utils.*;

public class Main {

    private static final Creatable INST;
    private static final Path PATH;
    private static final boolean XLSX;
    private static final int STEP;
    private static final int ROWS;
    private static final int ITRS;
    private static final int POOL;

    static {
        Properties pr = new Properties();
        try (FileInputStream in = new FileInputStream("sheet.config")) {
            pr.load(in);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        PATH = Path.of(pr.getProperty("PATH"));
        STEP = Integer.parseInt(pr.getProperty("STEP"));
        ROWS = Integer.parseInt(pr.getProperty("ROWS"));
        ITRS = Integer.parseInt(pr.getProperty("ITRS"));
        POOL = Integer.parseInt(pr.getProperty("POOL"));
        INST = Main.resolveName(pr.getProperty("INST"));
        XLSX = Boolean.parseBoolean(pr.getProperty("XLSX"));
    }

    /**
     * @param s
     * @return The spreadsheet creator corresponding to `s`.
     */
    private static Creatable resolveName(String s) {
        if (s.toLowerCase().equals("rate")) {
            return new Rate();
        } else if (s.toLowerCase().equals("runtotalfast")) {
            return new RunTotalFast();
        } else if (s.toLowerCase().equals("runtotalslow")) {
            return new RunTotalSlow();
        } else {
            System.out.println("Invalid INST.");
            System.exit(1);
            return null;
        }
    }

    /**
     * @return The path to the output directory.
     */
    private static String createDirectories() {
        File dir = Path.of(Main.PATH.toString()).toFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.toString();
    }

    /**
     * A wrapper method for creating spreadsheets. By default,
     * each sheet is created with uniformly random float values
     * between 0.0 and 1.0.
     * 
     * @param path
     * @param rows
     */
    private static void createSpreadsheet(String path, int rows) {
        System.out.println("Creating a sheet with " + rows + " row(s)");
        if (Main.XLSX) {
            Creator.createExcelSheet(Main.INST, path, rows, (row) -> (new Random()).nextDouble());
        } else {
            Creator.createCalcSheet(Main.INST, path, rows, (row) -> (new Random()).nextDouble());
        }
    }

    public static void main(String[] args) throws IOException {

        /** Setup */
        String path = Main.createDirectories();
        Stopwatch stopw = new Stopwatch();

        /** Create datasets */
        stopw.start();
        if (Main.POOL == 1) {
            for (int i = 0, r = Main.ROWS; i < Main.ITRS; i++, r += Main.STEP) {
                Main.createSpreadsheet(path, r);
            }
        } else {
            ExecutorService exc = SimpleThreadPoolExecutor.getNewExecutor(Main.POOL);
            for (int i = 0, r = Main.ROWS; i < Main.ITRS; i++, r += Main.STEP) {
                int[] rSize = { r };
                exc.submit(() -> {
                    Main.createSpreadsheet(path, rSize[0]);
                });
            }
            SimpleThreadPoolExecutor.wait(exc);
        }
        stopw.printDuration();

    }
}
