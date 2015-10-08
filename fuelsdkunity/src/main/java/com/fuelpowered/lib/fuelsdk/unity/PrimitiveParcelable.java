package com.fuelpowered.lib.fuelsdk.unity;

import android.os.Parcel;
import android.os.Parcelable;

/*******************************************************************************
 * Parcelable class used for parceling primitive types.
 */
class PrimitiveParcelable implements Parcelable {

	/**
	 * Creator method.
	 */
	public static final Parcelable.Creator<PrimitiveParcelable> CREATOR =
		new Parcelable.Creator<PrimitiveParcelable>() {

			@Override
			public PrimitiveParcelable createFromParcel(Parcel in) {
				return new PrimitiveParcelable(in);
			}

			@Override
			public PrimitiveParcelable[] newArray(int size) {
				return new PrimitiveParcelable[size];
			}

		};

	/**
	 * Enumeration of supported primitive types.
	 */
	private static enum PrimitiveType {
		BOOLEAN,
		BYTE,
		DOUBLE,
		FLOAT,
		INTEGER,
		LONG,
		STRING
	};

	/**
	 * Primitive value to store.
	 */
	private Object mValue;

	/**
	 * Primitive value type.
	 */
	private PrimitiveType mType;

	/***************************************************************************
	 * Constructor.
	 * 
	 * @param value Primitive object value to construct the PrimitiveParcel
	 *        object with.
	 * @throws IllegalArgumentException thrown if the value argument is not
	 *         defined, or unsupported.
	 */
	public PrimitiveParcelable(Object value) {
		if (value == null) {
			throw new IllegalArgumentException("PrimitiveParcelable value cannot be null");
		}

		mValue = value;

		if (value instanceof Boolean) {
			mType = PrimitiveType.BOOLEAN;
		} else if (value instanceof Byte) {
			mType = PrimitiveType.BYTE;
		} else if (value instanceof Double) {
			mType = PrimitiveType.DOUBLE;
		} else if (value instanceof Float) {
			mType = PrimitiveType.FLOAT;
		} else if (value instanceof Integer) {
			mType = PrimitiveType.INTEGER;
		} else if (value instanceof Long) {
			mType = PrimitiveType.LONG;
		} else if (value instanceof String) {
			mType = PrimitiveType.STRING;
		} else {
			throw new IllegalArgumentException("Unsupported primitive type " + value.getClass().getSimpleName());
		}
	}

	/***************************************************************************
	 * Constructor.
	 * 
	 * @param in Parcel used to construct the PrimitiveParcelable object.
	 */
	public PrimitiveParcelable(Parcel in) {
		mType = PrimitiveType.values()[in.readInt()];

		switch (mType) {
			case BOOLEAN:
				mValue = Boolean.valueOf(in.readInt() == 1);
				break;
			case BYTE:
				mValue = Byte.valueOf(in.readByte());
				break;
			case DOUBLE:
				mValue = Double.valueOf(in.readDouble());
				break;
			case FLOAT:
				mValue = Float.valueOf(in.readFloat());
				break;
			case INTEGER:
				mValue = Integer.valueOf(in.readInt());
				break;
			case LONG:
				mValue = Long.valueOf(in.readLong());
				break;
			case STRING:
				mValue = in.readString();
				break;
			default:
		}
	}

	/***************************************************************************
	 * Retrieves the primitive value.
	 * 
	 * @return Primitive value.
	 */
	public Object value() {
		return mValue;
	}

	/***************************************************************************
	 * Retrieves the primitive type.
	 * 
	 * @return Primitive type.
	 */
	public PrimitiveType type() {
		return mType;
	}

	/***************************************************************************
	 * Describe the kinds of special objects contained in this Parcelable's
	 * marshalled representation.
	 * 
	 * @return A bitmask indicating the set of special object types marshalled
	 *         by the Parcelable.
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	/***************************************************************************
	 * Flatten this object in to a Parcel.
	 * 
	 * @param dest The Parcel in which the object should be written.
	 * @param flags Additional flags about how the object should be written. May
	 *        be 0 or PARCELABLE_WRITE_RETURN_VALUE.
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mType.ordinal());

		switch (mType) {
			case BOOLEAN:
				dest.writeInt(((Boolean) mValue).booleanValue() ? 1 : 0);
				break;
			case BYTE:
				dest.writeByte(((Byte) mValue).byteValue());
				break;
			case DOUBLE:
				dest.writeDouble(((Double) mValue).doubleValue());
				break;
			case FLOAT:
				dest.writeFloat(((Float) mValue).floatValue());
				break;
			case INTEGER:
				dest.writeInt(((Integer) mValue).intValue());
				break;
			case LONG:
				dest.writeLong(((Long) mValue).longValue());
				break;
			case STRING:
				dest.writeString((String) mValue);
				break;
			default:
		}
	}

}
