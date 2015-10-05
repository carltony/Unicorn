/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package im.yangqiang.android.unicorn.data.database.table;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import im.yangqiang.android.unicorn.data.database.utils.DBClassUtils;
import im.yangqiang.android.unicorn.data.database.utils.FieldUtils;
import im.yangqiang.android.unicorn.exception.database.DBException;

public class TableInfo
{

	private String className;
	private String tableName;

	private Id id;

	public final HashMap<String, Property>  propertyMap  = new HashMap<String, Property>();
	public final HashMap<String, OneToMany> oneToManyMap = new HashMap<String, OneToMany>();
	public final HashMap<String, ManyToOne> manyToOneMap = new HashMap<String, ManyToOne>();

	private boolean checkDatabese;// 在对实体进行数据库操作的时候查询是否已经有表了，只需查询一遍，用此标示

	private static final HashMap<String, TableInfo> tableInfoMap = new HashMap<String, TableInfo>();

	private TableInfo()
	{
	}

	@SuppressWarnings("unused")
	public static TableInfo get(Class<?> clazz)
	{
		if (clazz == null) throw new DBException("table info get error,because the clazz is null");

		TableInfo tableInfo = tableInfoMap.get(clazz.getName());
		if (tableInfo == null)
		{
			tableInfo = new TableInfo();

			tableInfo.setTableName(DBClassUtils.getTableName(clazz));
			tableInfo.setClassName(clazz.getName());

			Field idField = DBClassUtils.getPrimaryKeyField(clazz);
			if (idField != null)
			{
				Id id = new Id();
				id.setColumn(FieldUtils.getColumnByField(idField));
				id.setFieldName(idField.getName());
				id.setSet(FieldUtils.getFieldSetMethod(clazz, idField));
				id.setGet(FieldUtils.getFieldGetMethod(clazz, idField));
				id.setDataType(idField.getType());

				tableInfo.setId(id);
			}
			else
			{
				throw new DBException("the class[" + clazz + "]'s idField is null , \n you can define _id,id property or use annotation @id to solution this exception");
			}

			List<Property> pList = DBClassUtils.getPropertyList(clazz);
			if (pList != null)
			{
				for (Property p : pList)
				{
					if (p != null)
					{
						tableInfo.propertyMap.put(p.getColumn(), p);
					}
				}
			}

			List<ManyToOne> mList = DBClassUtils.getManyToOneList(clazz);
			if (mList != null)
			{
				for (ManyToOne m : mList)
				{
					if (m != null) tableInfo.manyToOneMap.put(m.getColumn(), m);
				}
			}

			List<OneToMany> oList = DBClassUtils.getOneToManyList(clazz);
			if (oList != null)
			{
				for (OneToMany o : oList)
				{
					if (o != null) tableInfo.oneToManyMap.put(o.getColumn(), o);
				}
			}

			tableInfoMap.put(clazz.getName(), tableInfo);
		}

		if (tableInfo == null) throw new DBException("the class[" + clazz + "]'s table is null");

		return tableInfo;
	}

	public static TableInfo get(String className)
	{
		try
		{
			return get(Class.forName(className));
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public Id getId()
	{
		return id;
	}

	public void setId(Id id)
	{
		this.id = id;
	}

	public boolean isCheckDatabese()
	{
		return checkDatabese;
	}

	public void setCheckDatabese(boolean checkDatabese)
	{
		this.checkDatabese = checkDatabese;
	}

}
