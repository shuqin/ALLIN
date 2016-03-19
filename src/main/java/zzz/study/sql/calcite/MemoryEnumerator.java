package zzz.study.sql.calcite;

 import org.apache.calcite.linq4j.Enumerator;

import java.util.List;

 import static zzz.study.sql.calcite.DataConverter.convertCellValue;

public class MemoryEnumerator<E>  implements Enumerator<E> {
    private List<List<String>> data = null;
    private int currentIndex = -1;
    private RowConverter<E> rowConvert;
    private List<String> columnTypes;
    
    public MemoryEnumerator(int fieldCount, List<String> types, List<List<String>> data) {
        this.data = data;
        this.columnTypes = types;
        rowConvert = (RowConverter<E>) new ArrayRowConverter(fieldCount);
    }
    
    abstract static class RowConverter<E>{
        abstract E convertRow(List<String> rows, List<String> columnTypes);
    }
    
    static class ArrayRowConverter extends RowConverter<Object[]> {
        private int fieldCount;
        
        public ArrayRowConverter(int fieldCount) {
            this.fieldCount = fieldCount;
        }
        
        @Override
        Object[] convertRow(List<String> rows, List<String> columnTypes) {
            Object[] objects = new Object[fieldCount];
            int i = 0 ;
            while (i < fieldCount) {
                objects[i] = convertCellValue(rows.get(i), columnTypes.get(i));
                i+=1;
            }
            return objects;
        }
    }

	@Override
    public void close() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public E current() {
        List<String> dataline = data.get(currentIndex);
        E objs = rowConvert.convertRow(dataline, this.columnTypes);
        return objs;
	}

	@Override
    public boolean moveNext() {
        return ++currentIndex < data.size();
	}

	@Override
    public void reset() {
		// TODO Auto-generated method stub
		
	}

}
