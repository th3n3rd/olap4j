/*
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2007-2007 Julian Hyde
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.olap4j;

import mondrian.olap.Property;
import mondrian.rolap.RolapAggregator;
import mondrian.rolap.RolapStoredMeasure;
import org.olap4j.metadata.Datatype;
import org.olap4j.metadata.Measure;

/**
 * Implementation of {@link org.olap4j.metadata.Measure}
 * for the Mondrian OLAP engine,
 * as a wrapper around a mondrian
 * {@link mondrian.rolap.RolapStoredMeasure}.
 *
 * @author jhyde
 * @version $Id: $
 * @since Dec 10, 2007
 */
public class MondrianOlap4jMeasure
    extends MondrianOlap4jMember
    implements Measure
{
    MondrianOlap4jMeasure(
        MondrianOlap4jSchema olap4jSchema,
        RolapStoredMeasure measure)
    {
        super(olap4jSchema, measure);
    }

    public Aggregator getAggregator() {
        final RolapAggregator aggregator =
            ((RolapStoredMeasure) member).getAggregator();
        return Aggregator.valueOf(aggregator.getName().toUpperCase());
    }

    public Datatype getDatatype() {
        final String datatype =
            (String) member.getPropertyValue(Property.DATATYPE.getName());
        if (datatype != null) {
            if (datatype.equals("Integer")) {
                return Datatype.INTEGER;
            } else if (datatype.equals("Numeric")) {
                return Datatype.DOUBLE;
            }
        }
        return Datatype.STRING;
    }

    public boolean isVisible() {
        return (Boolean) member.getPropertyValue(Property.VISIBLE.getName());
    }
}

// End MondrianOlap4jMeasure.java
