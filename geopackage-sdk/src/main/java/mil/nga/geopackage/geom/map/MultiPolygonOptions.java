package mil.nga.geopackage.geom.map;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.PolygonOptions;

/**
 * Multiple Polygon Options object
 * 
 * @author osbornb
 */
public class MultiPolygonOptions {

	private List<PolygonOptions> polygonOptions = new ArrayList<PolygonOptions>();

	private PolygonOptions options;

	public void add(PolygonOptions polygonOption) {
		polygonOptions.add(polygonOption);
	}

	public List<PolygonOptions> getPolygonOptions() {
		return polygonOptions;
	}

	public PolygonOptions getOptions() {
		return options;
	}

	public void setOptions(PolygonOptions options) {
		this.options = options;
	}

	public void setPolygonOptions(List<PolygonOptions> polygonOptions) {
		this.polygonOptions = polygonOptions;
	}

}
