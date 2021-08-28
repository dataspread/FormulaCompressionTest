import win32com.client

class Excel:

  def __init__(self):
    self.wb = None
    self.excel = None

  def start(self):
    if self.excel is None:
      # A full list of Excel's properties may be found here: 
      # https://docs.microsoft.com/en-us/office/vba/api/excel.application(object)
      self.excel = win32com.client.DispatchEx('Excel.Application')
      self.excel.ScreenUpdating   = False
      self.excel.DisplayAlerts    = False
      self.excel.Visible          = False
    else:
      raise Exception('You must quit the application before starting a new Excel instance.')

  def open_wb(self, path):
    if self.wb is None:
      self.wb = self.excel.Workbooks.Open(path)
    else:
      raise Exception('Workbook is already open.')

  def update_cell(self, cell, value):
    if self.wb is not None:
      ws = self.wb.Worksheets("Sheet1")
      ws.Range(cell).Value = str(value)
    else:
      raise Exception('Must call open_wb() before updating a cell.')

  def close_wb(self):
    if self.wb is not None:
      self.wb.Close()
      self.wb = None
    else:
      raise Exception('Must call open_wb() before closing the workbook.')

  def end(self):
    if self.excel is not None:
      self.excel.Application.Quit()
      self.excel = None
    else:
      raise Exception('Application is already closed.')