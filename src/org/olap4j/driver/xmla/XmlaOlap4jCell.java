/*
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2007-2007 Julian Hyde
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package org.olap4j.driver.xmla;

import org.olap4j.*;
import org.olap4j.impl.ArrayMap;
import org.olap4j.metadata.Property;

import java.sql.ResultSet;
import java.util.*;

/**
 * Implementation of {@link org.olap4j.Cell}
 * for XML/A providers.
 *
 * @author jhyde
 * @version $Id: $
 * @since Dec 5, 2007
 */
class XmlaOlap4jCell implements Cell {
    private final XmlaOlap4jCellSet cellSet;
    private final int ordinal;
    private final Object value;
    private final String formattedValue;
    private final Map<Property, Object> propertyValues;

    XmlaOlap4jCell(
        XmlaOlap4jCellSet cellSet,
        int ordinal,
        Object value,
        String formattedValue,
        Map<Property, Object> propertyValues)
    {
        this.cellSet = cellSet;
        this.ordinal = ordinal;
        this.value = value;
        this.formattedValue = formattedValue;

        // Use emptyMap and ArrayMap for memory efficiency, because cells
        // typically have few properties, but there are a lot of cells
        this.propertyValues =
            propertyValues.isEmpty()
                ? Collections.<Property, Object>emptyMap()
                : new ArrayMap<Property, Object>(propertyValues);
    }

    public CellSet getCellSet() {
        return cellSet;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public List<Integer> getCoordinateList() {
        return cellSet.ordinalToCoordinates(ordinal);
    }

    public Object getPropertyValue(Property property) {
        return propertyValues.get(property);
    }

    public boolean isEmpty() {
        // FIXME
        return isNull();
    }

    public boolean isError() {
        return false;
    }

    public boolean isNull() {
        return value == null;
    }

    public double getDoubleValue() throws OlapException {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else {
            return Double.valueOf(String.valueOf(value));
        }
    }

    public String getErrorText() {
        return null; // FIXME:
    }

    public Object getValue() {
        return value;
    }

    public String getFormattedValue() {
        return formattedValue;
    }

    public ResultSet drillThrough() throws OlapException {
        throw new UnsupportedOperationException();
    }
}

// End XmlaOlap4jCell.java
