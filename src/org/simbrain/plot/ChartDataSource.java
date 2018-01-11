package org.simbrain.plot;

/**
 * Helper which encapsulates a source of data for charts.
 */
public interface ChartDataSource {

    /** Get a description of the data source. */
    String getDescription();

    /** Set the value of the data source. The behavior of this method will depend on the type of data source. */
    void setValue(double value);

}
