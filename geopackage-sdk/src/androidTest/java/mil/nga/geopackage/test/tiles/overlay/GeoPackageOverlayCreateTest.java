package mil.nga.geopackage.test.tiles.overlay;

import java.sql.SQLException;

import mil.nga.geopackage.test.CreateGeoPackageTestCase;

/**
 * Test GeoPackage Overlay from a created database
 * 
 * @author osbornb
 */
public class GeoPackageOverlayCreateTest extends CreateGeoPackageTestCase {

	/**
	 * Constructor
	 */
	public GeoPackageOverlayCreateTest() {

	}

	/**
	 * Test overlay
	 * 
	 * @throws SQLException
	 */
	public void testOverlay() throws SQLException {

		GeoPackageOverlayUtils.testOverlay(geoPackage);

	}

}
