package mil.nga.geopackage.test.extension.index;

import java.sql.SQLException;

import mil.nga.geopackage.test.CreateGeoPackageTestCase;

/**
 * Test Feature Table Index from a created database
 *
 * @author osbornb
 */
public class FeatureTableIndexCreateTest extends CreateGeoPackageTestCase {

    /**
     * Constructor
     */
    public FeatureTableIndexCreateTest() {

    }

    /**
     * Test index
     *
     * @throws SQLException
     */
    public void testIndex() throws SQLException {

        FeatureTableIndexUtils.testIndex(geoPackage);

    }

    /**
     * Test delete all table indices
     *
     * @throws SQLException
     */
    public void testDeleteAll() throws SQLException {

        FeatureTableIndexUtils.testDeleteAll(geoPackage);

    }

    @Override
    public boolean allowEmptyFeatures() {
        return false;
    }

}
