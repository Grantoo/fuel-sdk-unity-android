package com.fuelpowered.lib.fuelsdk.unity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import com.fuelpowered.lib.propeller.PropellerSDKUtil.LogLevel;

/*******************************************************************************
 * JSON helper utility class.
 */
final public class JSONHelper {

	/***************************************************************************
	 * Constructor.
	 */
	private JSONHelper() {
		throw new AssertionError("Cannot instantiate " + getClass().getSimpleName());
	}

	/***************************************************************************
	 * Converts the given JSON object into a native bundle.
	 * 
	 * @param jsonObject JSON object to convert.
	 * @return The converted JSON object.
	 * @throws JSONException Thrown when a JSON parsing error occurs.
	 */
	@SuppressWarnings("unchecked")
	public static Bundle toBundle(JSONObject jsonObject) throws JSONException {
		if (jsonObject == null) {
			return null;
		}

		Bundle bundle = new Bundle();

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();
			Object value = jsonObject.get(key);
			Object convertedValue = fromJSON(value, true);

			if (convertedValue instanceof Boolean) {
				bundle.putBoolean(key, (Boolean) convertedValue);
			} else if (convertedValue instanceof Double) {
				bundle.putDouble(key, (Double) convertedValue);
			} else if (convertedValue instanceof Integer) {
				bundle.putInt(key, (Integer) convertedValue);
			} else if (convertedValue instanceof Long) {
				bundle.putLong(key, (Long) convertedValue);
			} else if (convertedValue instanceof String) {
				bundle.putString(key, (String) convertedValue);
			} else if (convertedValue instanceof Bundle) {
				bundle.putBundle(key, (Bundle) convertedValue);
			} else if (convertedValue instanceof ArrayList<?>) {
				bundle.putParcelableArrayList(key, (ArrayList<? extends Parcelable>) convertedValue);
			}
		}

		return bundle;
	}

	/***************************************************************************
	 * Converts the given JSON object into a native map.
	 * 
	 * @param jsonObject JSON object to convert.
	 * @return The converted JSON object.
	 * @throws JSONException Thrown when a JSON parsing error occurs.
	 */
	public static Map<String, Object> toMap(JSONObject jsonObject) throws JSONException {
		if (jsonObject == null) {
			return null;
		}

		Map<String, Object> map = new HashMap<String, Object>();

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();
			Object value = jsonObject.get(key);
			Object convertedValue = fromJSON(value, false);

			map.put(key, convertedValue);
		}

		return map;
	}

	/***************************************************************************
	 * Converts the given JSON array into a native list.
	 * 
	 * @param jsonArray JSON array to convert.
	 * @param bundleCompatible Flags whether or not the converted object should
	 *        be compatible for storage within a bundle. If true then JSONObject
	 *        types are converted to Bundles, otherwise JSONObject types are
	 *        converted to Maps.
	 * @return The converted JSON array.
	 * @throws JSONException Thrown when a JSON parsing error occurs.
	 */
	public static List<Object> toList(JSONArray jsonArray, boolean bundleCompatible) throws JSONException {
		if (jsonArray == null) {
			return null;
		}

		List<Object> list = new ArrayList<Object>();
		int count = jsonArray.length();

		for (int index = 0; index < count; index++) {
			Object value = jsonArray.get(index);
			Object convertedValue = fromJSON(value, bundleCompatible);

			if (!bundleCompatible ||
				(convertedValue == null) ||
				(convertedValue instanceof Bundle) ||
				(convertedValue instanceof List)) {
				list.add(convertedValue);
			} else {
				list.add(new PrimitiveParcelable(convertedValue));
			}
		}

		return list;
	}

	/***************************************************************************
	 * Converts the given JSON primitive object into native data types.
	 * 
	 * @param object JSON primitive object to convert.
	 * @param bundleCompatible Flags whether or not the converted object should
	 *        be compatible for storage within a bundle. If true then JSONObject
	 *        types are converted to Bundles, otherwise JSONObject types are
	 *        converted to Maps.
	 * @return The converted JSON primitive object.
	 * @throws JSONException Thrown when a JSON parsing error occurs.
	 */
	private static Object fromJSON(Object object, boolean bundleCompatible) throws JSONException {
		if (object == JSONObject.NULL) {
			return null;
		} else if (object instanceof JSONObject) {
			if (bundleCompatible) {
				return toBundle((JSONObject) object);
			} else {
				return toMap((JSONObject) object);
			}
		} else if (object instanceof JSONArray) {
			return toList((JSONArray) object, bundleCompatible);
		} else {
			return object;
		}
	}

	/***************************************************************************
	 * Parses a JSONObject from the given JSON object string.
	 * 
	 * @param jsonObjectString JSON object string to parse.
	 * @return JSONObject parsed from the given JSON object string, null
	 *         otherwise.
	 */
	public static JSONObject parseJSONObject(String jsonObjectString) {
		if (TextUtils.isEmpty(jsonObjectString)) {
			return null;
		}

		try {
			return new JSONObject(jsonObjectString);
		} catch (JSONException jsonException) {
			return null;
		}
	}

	/***************************************************************************
	 * Parses a JSONArray from the given JSON array string.
	 * 
	 * @param jsonArrayString JSON array string to parse.
	 * @return JSONArray parsed from the given JSON array string, null
	 *         otherwise.
	 */
	public static JSONArray parseJSONArray(String jsonArrayString) {
		if (TextUtils.isEmpty(jsonArrayString)) {
			return null;
		}

		try {
			return new JSONArray(jsonArrayString);
		} catch (JSONException jsonException) {
			return null;
		}
	}

	/***************************************************************************
	 * Converts the given data bundle into a JSONObject.
	 * 
	 * @param data Data bundle to convert.
	 * @return The converted data bundle.
	 * @throws JSONException Thrown when a JSON parsing error occurs.
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject toJSONObject(Bundle data) throws JSONException {
		if (data == null) {
			return null;
		}

		JSONObject result = new JSONObject();

		for (String key : data.keySet()) {
			Object value = data.get(key);

			if (value instanceof Bundle) {
				JSONObject jsonObject =
					toJSONObject((Bundle) value);

				if (jsonObject == null) {
					result.put(key, JSONObject.NULL);
				} else {
					result.put(key, jsonObject);
				}
			} else if (value instanceof List) {
				JSONArray jsonArray =
					toJSONArray((List<Object>) value);

				if (jsonArray == null) {
					result.put(key, JSONObject.NULL);
				} else {
					result.put(key, jsonArray);
				}
			} else {
				result.put(key, value);
			}
		}

		return result;
	}

	/***************************************************************************
	 * Converts the given data map into a JSONObject.
	 * 
	 * @param data Data map to convert.
	 * @return The converted data map.
	 * @throws JSONException Thrown when a JSON parsing error occurs.
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject toJSONObject(Map<String, Object> data) throws JSONException {
		if (data == null) {
			return null;
		}

		JSONObject result = new JSONObject();

		for (String key : data.keySet()) {
			Object value = data.get(key);

			if (value instanceof Map) {
				JSONObject jsonObject =
					toJSONObject((Map<String, Object>) value);

				if (jsonObject == null) {
					result.put(key, JSONObject.NULL);
				} else {
					result.put(key, jsonObject);
				}
			} else if (value instanceof List) {
				JSONArray jsonArray =
					toJSONArray((List<Object>) value);

				if (jsonArray == null) {
					result.put(key, JSONObject.NULL);
				} else {
					result.put(key, jsonArray);
				}
			} else {
				result.put(key, value);
			}
		}

		return result;
	}

	/***************************************************************************
	 * Converts the given data list into a JSONArray.
	 * 
	 * @param data Data list to convert.
	 * @return The converted data list.
	 * @throws JSONException Thrown when a JSON parsing error occurs.
	 */
	@SuppressWarnings("unchecked")
	public static JSONArray toJSONArray(List<Object> data) throws JSONException {
		if (data == null) {
			return null;
		}

		JSONArray result = new JSONArray();

		for (Object value : data) {
			if (value instanceof Bundle) {
				result.put(toJSONObject((Bundle) value));
			} else if (value instanceof Map) {
				result.put(toJSONObject((Map<String, Object>) value));
			} else if (value instanceof List) {
				result.put(toJSONArray((List<Object>) value));
			} else {
				result.put(value);
			}
		}

		return result;
	}

}
