package net.cabezudo.json.values;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import net.cabezudo.json.JSON;
import net.cabezudo.json.Position;
import net.cabezudo.json.exceptions.JSONCastException;

/**
 * A {@link JSONString} is an object extended from {@link JSONValue} object in order to represent a JSON string that can be used to create
 * JSON structures.
 *
 * @author <a href="http://cabezudo.net">Esteban Cabezudo</a>
 * @version 1.0
 * @since 1.7
 * @date 10/01/2014
 */
public class JSONString extends JSONValue<JSONString> {

  private final String value;

  /**
   * Initializes a newly created {@link JSONString} object so that it represents the same string as the argument.
   *
   * @param value A {@code String}
   */
  public JSONString(String value) {
    this(value, null);
  }

  /**
   * Initializes a newly created {@link JSONString} object so that it represents the same string as the argument.
   *
   * @param value A {@code String}
   * @param position The position of the {@code String} in origen
   */
  public JSONString(String value, Position position) {
    super(position);
    this.value = value;
  }

  /**
   * Initializes a newly created {@link JSONString} object using the {@code Character} passed as argument.
   *
   * @param character A {@code Character} object.
   */
  public JSONString(Character character) {
    super(null);
    this.value = character.toString();
  }

  /**
   * Initializes a newly created {@link JSONString} object using the {@code BigInteger} object passed as argument. The object convertion is
   * made using the {@code toString()} method of the {@code BigInteger} object.
   *
   * @param bigInteger A {@code BigInteger} object.
   */
  public JSONString(BigInteger bigInteger) {
    super(null);
    this.value = bigInteger.toString();
  }

  /**
   * Initializes a newly created {@link JSONString} object using the {@code BigdECIMAL} object passed as argument. The object convertion is
   * made using the {@code toString()} method of the {@code BigdECIMAL} object.
   *
   * @param bigDecimal A {@code BigDecimal} object.
   */
  public JSONString(BigDecimal bigDecimal) {
    super(null);
    this.value = bigDecimal.toString();
  }

  /**
   * Compares the string values represented by two {@link JSONString} objects.
   *
   * @param jsonString the {@link JSONString} to be compared.
   * @return the integer value {@code 0} if the argument {@link JSONString} is equal to this {@link JSONString}; a value less than {@code 0}
   * if this {@link JSONString} is lexicographically less than the {@link JSONString} argument; and a value greater than {@code 0} if this
   * {@link JSONString} is lexicographically greater than the {@link JSONString} argument.
   */
  @Override
  public int compareTo(JSONString jsonString) {
    return value.compareTo(jsonString.value);
  }

  /**
   * Compares this string to the specified object. The result is {@code
   * true} if and only if the argument is not {@code null} and is a {@code
   * String} object that represents the same sequence of characters as this object.
   *
   * @param object The object to compare this {@code String} against
   *
   * @return {@code true} if the given object represents a {@code String} equivalent to this string, {@code false} otherwise
   */
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null) {
      return false;
    }
    if (getClass() != object.getClass()) {
      return false;
    }
    JSONString other = (JSONString) object;
    return Objects.equals(this.value, other.value);
  }

  /**
   * /**
   * Returns {@code this} {@link JSONString} object, a non referenced {@link JSONString} object is the same object than a referenced
   * {@link JSONString} object because is not an JSON object to be referenced.
   *
   * @return The element whose referenciable objects have been replaced with their references.
   */
  @Override
  public JSONValue getReferencedElement() {
    return this;
  }

  /**
   * Returns a hash code for this string. The hash code for a {@link JSONString} is the same hash that a {@code String} object.
   *
   * @return a hash code value for this object.
   */
  @Override
  public int hashCode() {
    return this.value.hashCode();
  }

  /**
   * Returns if the JSON element is a {@link JSONString}. This object represent a JSON string, so allways return a true.
   *
   * @return <code>true</code> because the object is a {@link JSONString}.
   */
  @Override
  public boolean isString() {
    return true;
  }

  /**
   * Convert the {@link JSONString} into an array of {@link JSONValue} objects. The result array is an array of only one element, the
   * {@code this} object.
   *
   * @return an array of {@link JSONValue} with a single element.
   */
  @Override
  public JSONValue[] toArray() {
    JSONValue array[] = new JSONValue[1];
    array[0] = new JSONString(value, getPosition());
    return array;
  }

  /**
   * Try to create, using the {@code BigDecimal} constructor, a {@code BigDecimal} object using the {@code BigDecimal} rules for
   * {@code String} and throw a {@code NumberFormatException} if the {@link JSONString} don't have a valid representation for a {code
   * BigDecimal}.
   *
   * @return a {code BigDecimal} result to translate the {@code String} represented in {@link JSONString}.
   * @throws NumberFormatException if {@code value} is not a valid representation of a {@code BigDecimal}.
   */
  @Override
  public BigDecimal toBigDecimal() {
    return new BigDecimal(value);
  }

  /**
   * Try to create, using the {@code BigInteger} constructor, a {@code BigInteger} object using the {@code BigInteger} rules for {code
   * String} and throw a {@code NumberFormatException} if the {@link JSONString} don't have a valid representation for a {code BigInteger}.
   *
   * @return a {code BigInteger} result to translate the {@code String} represented in {@link JSONString}.
   * @throws NumberFormatException if {@code value} is not a valid representation of a {@code BigInteger}.
   */
  @Override
  public BigInteger toBigInteger() {
    return new BigInteger(value);
  }

  /**
   * Returns a {@code Boolean} with the boolean value represented by the specified string. The {@code Boolean} returned represents a true
   * value if the string represented in {@code this} is not {@code null} and is equal, ignoring case, to the string {@code "true"}.
   *
   * @return a {code Boolean} result to convert, using {@code Boolean.valueOf()}, the {@code String} represented in {@link JSONString}.
   */
  @Override
  public Boolean toBoolean() {
    return Boolean.valueOf(value);
  }

  /**
   * Constructs an return a newly allocated {@code Byte} object that represents the value indicated by the {@code String} value in the
   * {@link JSONString}. The string is converted to a {@code Byte} value in exactly the manner used by the {@code parseByte} method for
   * radix 10.
   *
   * @return the value of this {@link JSONString} as a {@code Byte}.
   * @throws NumberFormatException if the {@code String} does not contain a parsable {@code Byte}.
   */
  @Override
  public Byte toByte() {
    return new Byte(value);
  }

  /**
   * Convert the {@link JSONString} into an array of {@code byte} primitives. The result array is an array of only one element, the
   * converted value of {@code this} object.
   *
   * @return an array of {@link JSONValue} with a only one element.
   */
  @Override
  public byte[] toByteArray() {
    byte[] b = new byte[1];
    b[0] = toByte();
    return b;
  }

  /**
   * Constructs an return a newly allocated {@code Calendar} object that represents the value indicated by the {@code String} represented in
   * the {@link JSONString}. The string is converted to a {@code Calendar} value using the {@code SimpleDateFormat} {@code parse} method and
   * the pattern specified for {@link JSON#SIMPLE_DATE_FORMAT_PATTERN}.
   *
   * @return the value of this {@link JSONString} as a {@code Calendar}.
   * @throws JSONCastException if the {@code String} does not contain a parsable {@code Calendar}.
   */
  @Override
  public Calendar toCalendar() {
    SimpleDateFormat sdf = new SimpleDateFormat(JSON.SIMPLE_DATE_FORMAT_PATTERN);
    Date date;
    try {
      date = sdf.parse(value);
    } catch (ParseException e) {
      throw new JSONCastException("I can't convert a " + value + " to a Calendar.", e);
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar;
  }

  /**
   * Convert the {@link JSONString} into a {@code Character} object using the {@code this} value. The result {@code Character} is a
   * {@code Character} object created with the first character of the value {@code String}.
   *
   * @return a {@code Character}.
   * @exception IndexOutOfBoundsException if the value {@code String} length is less than one.
   *
   */
  @Override
  public Character toCharacter() {
    return value.charAt(0);
  }

  /**
   * Constructs an return a newly allocated {@code Double} object that represents the value indicated by the {@code String} represented in
   * the {@link JSONString}. The string is converted to a {@code Double} using the {@code Double} constructor.
   *
   * @return the value of this {@link JSONString} as a {@code Double}.
   * @throws NumberFormatException if the {@code String} value of {@code this} {@link JSONString} does not contain a parseable double
   * number.
   */
  @Override
  public Double toDouble() {
    return new Double(value);
  }

  /**
   * Constructs an return a newly allocated {@code Float} object that represents the value indicated by the {@code String} represented in
   * the {@link JSONString}. The string is converted to a {@code Float} using the {@code Float} constructor.
   *
   * @return the value of this {@link JSONString} as a {@code Float}.
   * @throws NumberFormatException if the {@code String} value of {@code this} {@link JSONString} does not contain a parseable float number.
   */
  @Override
  public Float toFloat() {
    return new Float(value);
  }

  /**
   * Constructs an return a newly allocated {@code Integer} object that represents the value indicated by the {@code String} represented in
   * the {@link JSONString}. The string is converted to a {@code Integer} using the {@code Integer} constructor.
   *
   * @return the value of this {@link JSONString} as a {@code Integer}.
   * @throws NumberFormatException if the {@code String} value of {@code this} {@link JSONString} does not contain a parseable integer
   * number.
   */
  @Override
  public Integer toInteger() {
    return new Integer(value);
  }

  /**
   * Return a {@code String} with the value of {@code this} {@code JSONString} object converted to a JSON valid representation. A JSON
   * string is always a string in double quotes. This method is used to create JSON strings.
   *
   * @return a {@code String} with the JSON formated {@code String}.
   */
  @Override
  public String toJSON() {
    if (value == null) {
      return null;
    }
    return "\"" + value + "\"";
  }

  /**
   * Convert the {@link JSONString} into a {@link JSONArray} object. The result is a {@link JSONArray} with only one {@link JSONString}
   * created with the value of {@code this} {@link JSONString} object.
   *
   * @return a {@link JSONArray} with only one element.
   */
  @Override
  public JSONArray toJSONArray() {
    return new JSONArray(value);
  }

  /**
   * Constructs an return a newly allocated {@link JSONString} object that represents the value indicated by the {@code String} value of
   * {@code this} {@link JSONString}. The string is created using the {@link JSONSting} constructor.
   *
   * @return a {@link JSONString}.
   */
  @Override
  public JSONString toJSONString() {
    return new JSONString(value, getPosition());
  }

  /**
   * Convert the {@link JSONString} into an {@code ArrayList} of {@link JSONValue} objects. The result list has only one element, the
   * {@code this} object.
   *
   * @return an {@code ArrayList} of {@link JSONValue} objects.
   */
  @Override
  public List<JSONValue> toList() {
    List<JSONValue> list = new ArrayList<>();
    list.add(this);
    return list;
  }

  /**
   * Constructs an return a newly allocated {@code Long} object that represents the value indicated by the {@code String} represented in the
   * {@link JSONString}. The string is converted to a {@code Long} using the {@code Long} constructor.
   *
   * @return the value of this {@link JSONString} as a {@code Long}.
   * @throws NumberFormatException if the {@code String} value of {@code this} {@link JSONString} does not contain a parseable long number.
   */
  @Override
  public Long toLong() {
    return new Long(value);
  }

  /**
   * Constructs an return a newly allocated {@code Short} object that represents the value indicated by the {@code String} represented in
   * the {@link JSONString}. The string is converted to a {@code Short} using the {@code Short} constructor.
   *
   * @return the value of this {@link JSONString} as a {@code Short}.
   * @throws NumberFormatException if the {@code String} value of {@code this} {@link JSONString} does not contain a parseable short number.
   */
  @Override
  public Short toShort() {
    return new Short(value);
  }

  /**
   * Returns a string representation of the {@code this} object. The string representacion for the object is the {@code String} represented
   * for the {@link JSONString} object and is the same object that the internal {@code String} value of the object.
   *
   * @return a <code>String</code> representation of the value of {@code this} object.
   */
  @Override
  public String toString() {
    return value;
  }

  /**
   * Convert the {@link JSONString} into an array of {@code String} objects. The result array is an array with only one element, the
   * {@code this} object.
   *
   * @return an array of {@code String} with a single element.
   */
  @Override
  public String[] toStringArray() {
    String[] array = new String[1];
    array[0] = value;
    return array;
  }
}