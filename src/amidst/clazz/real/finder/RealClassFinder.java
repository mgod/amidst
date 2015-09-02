package amidst.clazz.real.finder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import amidst.clazz.real.RealClass;
import amidst.clazz.real.finder.detect.RealClassDetector;
import amidst.clazz.real.finder.prepare.RealClassPreparer;
import amidst.logging.Log;

public class RealClassFinder {
	public static RCFBuilder builder() {
		return RCFBuilder.builder();
	}

	public static Map<String, RealClass> findAllClasses(
			List<RealClass> realClasses, List<RealClassFinder> finders) {
		Map<String, RealClass> result = new HashMap<String, RealClass>();
		for (RealClassFinder finder : finders) {
			RealClass realClass = finder.find(realClasses);
			if (realClass != null) {
				if (!result.containsKey(finder.getSymbolicClassName())) {
					result.put(finder.getSymbolicClassName(), realClass);
				}
				Log.debug("Found: " + realClass.getRealClassName() + " as "
						+ finder.getSymbolicClassName());
			} else {
				Log.debug("Missing: " + finder.getSymbolicClassName());
			}
		}
		return result;
	}

	private String symbolicClassName;
	private RealClassDetector detector;
	private RealClassPreparer preparer;

	public RealClassFinder(String symbolicClassName,
			RealClassDetector detector, RealClassPreparer preparer) {
		this.symbolicClassName = symbolicClassName;
		this.detector = detector;
		this.preparer = preparer;
	}

	public boolean find(RealClass realClass) {
		if (detector.detect(realClass)) {
			preparer.prepare(realClass);
			return true;
		} else {
			return false;
		}
	}

	public RealClass find(List<RealClass> realClasses) {
		for (RealClass realClass : realClasses) {
			if (find(realClass)) {
				return realClass;
			}
		}
		return null;
	}

	public String getSymbolicClassName() {
		return symbolicClassName;
	}
}
