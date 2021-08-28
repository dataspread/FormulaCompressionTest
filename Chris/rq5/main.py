from utils.exporter import Exporter
from utils.excel import Excel

import subprocess
import traceback
import datetime
import pathlib
import random
import sys
import os

INPUTS_PATH = os.path.join('inputs')
OUTPUT_PATH = os.path.join('output')
TRIALS = 3

if __name__ == "__main__":
  
  # Ensures Excel is fully terminated before starting
  print("Closing all running instances of EXCEL.EXE (if any)")
  subprocess.call(["taskkill", "/f", "/im", "EXCEL.EXE"], stderr=subprocess.DEVNULL)

  # Create output directories
  if not os.path.exists(OUTPUT_PATH): os.makedirs(OUTPUT_PATH)

  # Collect a list of tuples of the form (relative_path_to_xl_file, relative_path_to_output_folder, input_size)
  pairs = []
  for folder in os.listdir(INPUTS_PATH):
    for fname in os.listdir(os.path.join(INPUTS_PATH, folder)):
      if fname.endswith('.xlsx'):
        rows = int(fname[fname.index('-')+1:fname.index('.')])
        pairs.append((os.path.join(folder, fname), os.path.join(folder), rows))

  # Randomize input order
  random.shuffle(pairs)

  # Iterate over input files
  for (relative_input_path, relative_output_path, rows) in pairs:

    # Get the full path to the excel workbook    
    absolute_path = os.path.join(pathlib.Path.cwd() / INPUTS_PATH, relative_input_path)

    # Start a fresh Excel process
    xl = Excel()
    xl.start()

    try:
    
      # Open, update, close,  measure, repeat
      for t in range(TRIALS):
        print(f"Running: {relative_input_path} (trial {t + 1})")
        test_start_time = datetime.datetime.now()
        xl.open_wb(absolute_path)
        update_start_time = datetime.datetime.now()
        xl.update_cell("A1", 20)
        update_final_time = datetime.datetime.now()
        xl.close_wb()
        test_final_time = datetime.datetime.now()
        Exporter.corestat(
          os.path.join(
            OUTPUT_PATH, 
            relative_output_path, 
            f'RUN-{t}', 
            f'ROWS-{rows}'
          ),
          rows,
          (test_final_time - test_start_time).total_seconds() * 1000,
          (update_final_time - update_start_time).total_seconds() * 1000
        )
        print(f"Finished: {relative_input_path} (trial {t + 1})")

    except Exception as e:
        
      # Completely end the program if an exception is raised
      traceback.print_exc()
      sys.exit()
    
    finally:

      # Close Excel
      xl.end()