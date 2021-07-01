EXPLAIN ANALYZE WITH RECURSIVE deps AS (SELECT dep_bookname, dep_sheetname, dep_range::text, must_expand FROM dependency 
      WHERE  bookname  = 'testBook1624576014606'
      AND    sheetname =  'Sheet1'
      AND    range && box '((0,0),(0,0))'
      UNION
      SELECT d.dep_bookname, d.dep_sheetname, d.dep_range::text, d.must_expand FROM dependency d 
      INNER JOIN deps t
      ON  d.bookname   =  t.dep_bookname
      AND t.must_expand
      AND d.sheetname =  t.dep_sheetname
      AND d.range && t.dep_range::box) 
SELECT dep_bookname, dep_sheetname, dep_range::box FROM deps;

