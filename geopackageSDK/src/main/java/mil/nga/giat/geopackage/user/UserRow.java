package mil.nga.giat.geopackage.user;

import mil.nga.giat.geopackage.GeoPackageException;
import mil.nga.giat.geopackage.db.GeoPackageDataType;
import android.content.ContentValues;
import android.database.Cursor;

/**
 * User Row containing the values from a single cursor row
 * 
 * @param <TColumn>
 * @param <TTable>
 * 
 * @author osbornb
 */
public abstract class UserRow<TColumn extends UserColumn, TTable extends UserTable<TColumn>> {

	/**
	 * User table
	 */
	private final TTable table;

	/**
	 * Cursor column types of this row, based upon the data values
	 */
	private final int[] columnTypes;

	/**
	 * Array of row values
	 */
	private final Object[] values;

	/**
	 * Constructor
	 * 
	 * @param table
	 * @param columnTypes
	 * @param values
	 */
	protected UserRow(TTable table, int[] columnTypes, Object[] values) {
		this.table = table;
		this.columnTypes = columnTypes;
		this.values = values;
	}

	/**
	 * Constructor to create an empty row
	 * 
	 * @param table
	 */
	protected UserRow(TTable table) {
		this.table = table;
		// Default column types will all be 0 which is null
		// (Cursor.FIELD_TYPE_NULL)
		this.columnTypes = new int[table.columnCount()];
		this.values = new Object[table.columnCount()];
	}

	/**
	 * Get the column count
	 * 
	 * @return
	 */
	public int columnCount() {
		return table.columnCount();
	}

	/**
	 * Get the column names
	 * 
	 * @return
	 */
	public String[] getColumnNames() {
		return table.getColumnNames();
	}

	/**
	 * Get the column name at the index
	 * 
	 * @param index
	 * @return
	 */
	public String getColumnName(int index) {
		return table.getColumnName(index);
	}

	/**
	 * Get the index of the column name
	 * 
	 * @param columnName
	 * @return
	 */
	public int getColumnIndex(String columnName) {
		return table.getColumnIndex(columnName);
	}

	/**
	 * Get the value at the index
	 * 
	 * @param index
	 * @return
	 */
	public Object getValue(int index) {
		return values[index];
	}

	/**
	 * Get the value of the column name
	 * 
	 * @param columnName
	 * @return
	 */
	public Object getValue(String columnName) {
		return values[table.getColumnIndex(columnName)];
	}

	/**
	 * Get the row values
	 * 
	 * @return
	 */
	public Object[] getValues() {
		return values;
	}

	/**
	 * Get the Cursor column data types
	 * 
	 * @return
	 * @see Cursor#FIELD_TYPE_STRING
	 * @see Cursor#FIELD_TYPE_INTEGER
	 * @see Cursor#FIELD_TYPE_FLOAT
	 * @see Cursor#FIELD_TYPE_BLOB
	 * @see Cursor#FIELD_TYPE_NULL
	 */
	public int[] getRowColumnTypes() {
		return columnTypes;
	}

	/**
	 * Get the Cursor column data type at the index
	 * 
	 * @param index
	 * @return
	 * @see Cursor#FIELD_TYPE_STRING
	 * @see Cursor#FIELD_TYPE_INTEGER
	 * @see Cursor#FIELD_TYPE_FLOAT
	 * @see Cursor#FIELD_TYPE_BLOB
	 * @see Cursor#FIELD_TYPE_NULL
	 */
	public int getRowColumnType(int index) {
		return columnTypes[index];
	}

	/**
	 * Get the Cursor column data type of the column name
	 * 
	 * @param columnName
	 * @return
	 * @see Cursor#FIELD_TYPE_STRING
	 * @see Cursor#FIELD_TYPE_INTEGER
	 * @see Cursor#FIELD_TYPE_FLOAT
	 * @see Cursor#FIELD_TYPE_BLOB
	 * @see Cursor#FIELD_TYPE_NULL
	 */
	public int getRowColumnType(String columnName) {
		return columnTypes[table.getColumnIndex(columnName)];
	}

	/**
	 * Get the table
	 * 
	 * @return
	 */
	public TTable getTable() {
		return table;
	}

	/**
	 * Get the column at the index
	 * 
	 * @param index
	 * @return
	 */
	public TColumn getColumn(int index) {
		return table.getColumn(index);
	}

	/**
	 * Get the column of the column name
	 * 
	 * @param columnName
	 * @return
	 */
	public TColumn getColumn(String columnName) {
		return table.getColumn(columnName);
	}

	/**
	 * Get the id value, which is the value of the primary key
	 * 
	 * @return
	 */
	public long getId() {
		long id;
		Object objectValue = getValue(getPkColumnIndex());
		if (objectValue == null) {
			throw new GeoPackageException("Row Id was null. Table: "
					+ table.getTableName() + ", Column Index: "
					+ getPkColumnIndex() + ", Column Name: "
					+ getPkColumn().getName());
		}
		if (objectValue instanceof Number) {
			id = ((Number) objectValue).longValue();
		} else {
			throw new GeoPackageException("Row Id was not a number. Table: "
					+ table.getTableName() + ", Column Index: "
					+ getPkColumnIndex() + ", Column Name: "
					+ getPkColumn().getName());
		}

		return id;
	}

	/**
	 * Get the primary key column index
	 * 
	 * @return
	 */
	public int getPkColumnIndex() {
		return table.getPkColumnIndex();
	}

	/**
	 * Get the primary key column
	 * 
	 * @return
	 */
	public TColumn getPkColumn() {
		return table.getPkColumn();
	}

	/**
	 * Set the column value at the index
	 * 
	 * @param index
	 * @param value
	 */
	public void setValue(int index, Object value) {
		if (index == table.getPkColumnIndex()) {
			throw new GeoPackageException(
					"Can not update the primary key of the row. Table Name: "
							+ table.getTableName() + ", Index: " + index
							+ ", Name: " + table.getPkColumn().getName());
		}
		values[index] = value;
	}

	/**
	 * Set the column value of the column name
	 * 
	 * @param columnName
	 * @param value
	 */
	public void setValue(String columnName, Object value) {
		setValue(getColumnIndex(columnName), value);
	}

	/**
	 * Set the id, package access only for the DAO
	 * 
	 * @param id
	 */
	void setId(long id) {
		values[getPkColumnIndex()] = id;
	}

	/**
	 * Clears the id so the row can be used as part of an insert or create
	 */
	public void resetId() {
		values[getPkColumnIndex()] = null;
	}

	/**
	 * Convert the row to content values
	 * 
	 * @return
	 */
	public ContentValues toContentValues() {

		ContentValues contentValues = new ContentValues();
		for (TColumn column : table.getColumns()) {

			if (!column.isPrimaryKey()) {

				Object value = values[column.getIndex()];
				String columnName = column.getName();

				if (value == null) {
					contentValues.putNull(columnName);
				} else {
					columnToContentValue(contentValues, column, value);
				}

			}

		}

		return contentValues;
	}

	/**
	 * Map the column to the content values
	 * 
	 * @param contentValues
	 * @param column
	 * @param value
	 * @return
	 */
	protected void columnToContentValue(ContentValues contentValues,
			TColumn column, Object value) {

		String columnName = column.getName();

		if (value instanceof Number) {
			if (value instanceof Byte) {
				validateValue(column, value, Byte.class, Short.class,
						Integer.class, Long.class);
				contentValues.put(columnName, (Byte) value);
			} else if (value instanceof Short) {
				validateValue(column, value, Short.class, Integer.class,
						Long.class);
				contentValues.put(columnName, (Short) value);
			} else if (value instanceof Integer) {
				validateValue(column, value, Integer.class, Long.class);
				contentValues.put(columnName, (Integer) value);
			} else if (value instanceof Long) {
				validateValue(column, value, Long.class, Double.class);
				contentValues.put(columnName, (Long) value);
			} else if (value instanceof Float) {
				validateValue(column, value, Float.class);
				contentValues.put(columnName, (Float) value);
			} else if (value instanceof Double) {
				validateValue(column, value, Double.class);
				contentValues.put(columnName, (Double) value);
			} else {
				throw new GeoPackageException("Unsupported Number type: "
						+ value.getClass().getSimpleName());
			}
		} else if (value instanceof String) {
			validateValue(column, value, String.class);
			String stringValue = (String) value;
			if (column.getMax() != null
					&& stringValue.length() > column.getMax()) {
				throw new GeoPackageException(
						"String is larger than the column max. Size: "
								+ stringValue.length() + ", Max: "
								+ column.getMax() + ", Column: " + columnName);
			}
			contentValues.put(columnName, stringValue);
		} else if (value instanceof byte[]) {
			validateValue(column, value, byte[].class);
			byte[] byteValue = (byte[]) value;
			if (column.getMax() != null && byteValue.length > column.getMax()) {
				throw new GeoPackageException(
						"Byte array is larger than the column max. Size: "
								+ byteValue.length + ", Max: "
								+ column.getMax() + ", Column: " + columnName);
			}
			contentValues.put(columnName, byteValue);
		} else if (value instanceof Boolean) {
			validateValue(column, value, Boolean.class);
			Boolean booleanValue = (Boolean) value;
			short shortBoolean = booleanValue ? (short) 1 : (short) 0;
			contentValues.put(columnName, shortBoolean);
		} else {
			throw new GeoPackageException(
					"Unsupported update column value. column: " + columnName
							+ ", value: " + value);
		}
	}

	/**
	 * Validate the value and its actual value types against the column data
	 * type class
	 * 
	 * @param column
	 * @param value
	 * @param valueTypes
	 */
	private void validateValue(TColumn column, Object value,
			Class<?>... valueTypes) {

		GeoPackageDataType dataType = column.getDataType();
		Class<?> dataTypeClass = dataType.getClassType();

		boolean valid = false;
		for (Class<?> valueType : valueTypes) {
			if (valueType.equals(dataTypeClass)) {
				valid = true;
				break;
			}
		}

		if (!valid) {
			throw new GeoPackageException("Illegal value. Column: "
					+ column.getName() + ", Value: " + value
					+ ", Expected Type: " + dataTypeClass.getSimpleName()
					+ ", Actual Type: " + valueTypes[0].getSimpleName());
		}

	}

}