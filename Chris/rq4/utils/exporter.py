import os 

class Exporter:

  @staticmethod
  def corestat(output_path, rows, total_time, update_time):
    os.makedirs(output_path)
    with open(os.path.join(output_path, 'core.stat'), 'w') as f:
      f.write(f'Total test time (ms): {total_time}\n')
      f.write(f'Number of cells to update: {rows + 1}\n')
      f.write(f'Number of cells updated: {rows + 1}\n')
      f.write(f'Total time to update cells (ms): {update_time}\n')
      f.write(f'Total time after the update (ms): {None}\n')
      f.write(f'Total time of getting dependents (ms): {None}\n')
      f.write(f'Total time of adding the batch (ms): {None}\n')
      f.write(f'Total time of refreshing the cache (ms): {None}\n')
      f.write(f'Area under curve: {None}\n')
