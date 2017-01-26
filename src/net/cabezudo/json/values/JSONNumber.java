package net.cabezudo.json.values;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import net.cabezudo.json.Position;

/**
 * A {@link JSONNumber} is an object extended from {@link JSONValue} object in order to represent a
 * number that can be used to create JSON structures.
 *
 * @author <a href="http://cabezudo.net">Esteban Cabezudo</a>
 * @version 1.0
 * @since 1.7
 * @date 10/01/2014
 */
public class JSONNumber extends JSONValue<JSONNumber> {

  /**
   * De default scale for the BigDecimal conversions.
   */
  public static final int DEFAULT_SCALE = 6;

  private final BigDecimal value;

  /**
   * Construct a {@code JSONNumber} using the {@code String} passed.
   *
   * @param value a {@code String} with a number.
   */
  public JSONNumber(String value) {
    this(new BigDecimal(value));
  }

  /**
   * Construct a {@code JSONNumber} using the {@code Byte} passed.
   *
   * @param value a {@code Byte} with the number.
   */
  public JSONNumber(Byte value) {
    this(new BigDecimal(value));
  }

  /**
   * Construct a {@code JSONNumber} using the {@code Short} passed.
   *
   * @param value a {@code Short} with the number.
   */
  public JSONNumber(Short value) {
    this(new BigDecimal(value));
  }

  /**
   * Construct a {@code JSONNumber} using the {@code Integer} passed.
   *
   * @param value a {@code Integer} with the number.
   */
  public JSONNumber(Integer value) {
    this(new BigDecimal(value));
  }

  /**
   * Construct a {@code JSONNumber} using the {@code Long} passed.
   *
   * @param value a {@code Long} with the number.
   */
  public JSONNumber(Long value) {
    this(new BigDecimal(value));
  }

  /**
   * Construct a {@code JSONNumber} using the {@code Float} passed.
   *
   * @param value a {@code Float} with the number.
   */
  public JSONNumber(Float value) {
    this(new BigDecimal(value));
  }

  /**
   * Construct a {@code JSONNumber} using the {@code Double} passed.
   *
   * @param value a {@code Double} with the number.
   */
  public JSONNumber(Double value) {
    this(new BigDecimal(value));
  }

  /**
   * Construct a {@code JSONNumber} using the {@code BigDecimal} passed.
   *
   * @param value a {@code BigDecimal} with the number.
   */
  public JSONNumber(BigDecimal value) {
    this(value, null);
  }

  /**
   * Construct a {@code JSONNumber} using the {@code BigDecimal} passed. The method also take a
   * {@link Position} in order to save the position in origin and give it when a parse error is
   * throw.
   *
   * @param value a {@code BigDecimal} with the number.
   * @param position the position of the value in the source.
   */
  public JSONNumber(BigDecimal value, Position position) {
    super(position);
    if (value == null) {
      throw new IllegalArgumentException("You can't create an object using null.");
    }
    this.value = value.round(MathContext.UNLIMITED).setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
  }

  /**
   * Construct a {@code JSONNumber} using the {@code BigInteger} passed.
   *
   * @param value a {@code BigInteger} with a number.
   */
  public JSONNumber(BigInteger value) {
    super(null);
    if (value == null) {
      throw new IllegalArgumentException("You can't create an object using null.");
    }
    this.value = new BigDecimal(value).setScale(DEFAULT_SCALE).stripTrailingZeros();
  }

  /**
   * Compares two {@code JSONNumber} objects.
   *
   * @param jsonNumber the {@code JSONNumber} to be compared.
   *
   * @return the value {@code 0} if {@code this} {@code JSONNumber} is equal to the argument
   * {@code JSONNumber}; a value less than {@code 0} if {@code this} {@code JSONNumber} is less than
   * the argument {@code JSONNumber}; and a value greater than {@code 0} if {@code this}
   * {@code JSONNumber} is greater than the argument {@code JSONNumber}.
   */
  @Override
  public int compareTo(JSONNumber jsonNumber) {
    return value.compareTo(jsonNumber.value);
  }

  /**
   * Compares two {@code JSONNumber} objects.
   *
   * @param o the {@code Object} to be compared.
   * @return {@code true} if {@code this} {@code JSONNumber} is equal to the argument {@code false}
   * otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (getClass() != o.getClass()) {
      return false;
    }
    final JSONNumber jsonNumber = (JSONNumber) o;
    return value.equals(jsonNumber.value);
  }

  /**
   * Return the referenced element for {@code this} object. For a {@code JSONNumber} object,
   * {@code this} object and the referenced version is the same.
   *
   * @return {@code this} object.
   */
  @Override
  public JSONNumber toReferencedElement() {
    return this;
  }

  /**
   * Returns the hash code for this {@code JSONNumber}.
   *
   * @return hash code for this {@code JSONNumber}.
   */
  @Override
  public int hashCode() {
    return value.hashCode();
  }

  /**
   * Returns whether the element is a {@code JSONNumber} or not.
   *
   * @return {@code true}.
   */
  @Override
  public boolean isNumber() {
    return true;
  }

  /**
   * Convert {@code this} object to a {@code BigDecimal} object.
   *
   * @return a {@code BigDecimal} object if the conversion is possible.
   */
  @Override
  public BigDecimal toBigDecimal() {
    return value;
  }

  /**
   * Convert {@code this} object to a {@code BigInteger} object.
   *
   * @return a {@code BigDecBigIntegerimal} object if the conversion is possible.
   */
  @Override
  public BigInteger toBigInteger() {
    return value.toBigInteger();
  }

  /**
   * Convert {@code this} object to a {@code Byte} object.
   *
   * @return a {@code Byte} object if the conversion is possible.
   */
  @Override
  public Byte toByte() {
    byte b = value.byteValueExact();
    return b;
  }

  /**
   * Convert {@code this} object to an array of {@code byte} primitive.
   *
   * @return an array of {@code byte} primitive if the conversion is possible.
   */
  @Override
  public byte[] toByteArray() {
    byte[] byteArray = new byte[1];
    byteArray[0] = this.toByte();
    return byteArray;
  }

  /**
   * Convert {@code this} object to a {@code Calendar} object.
   *
   * @return a {@code Calendar} object if the conversion is possible.
   */
  @Override
  public Calendar toCalendar() {
    long time = this.toLong();
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(time);
    return calendar;
  }

  /**
   * Convert {@code this} object to a {@code Character} object.
   *
   * @return a {@code Character} object if the conversion is possible.
   */
  @Override
  public Character toCharacter() {
    Character character = (char) value.intValue();
    return character;
  }

  /**
   * Convert {@code this} object to a {@code Double} object.
   *
   * @return a {@code Double} object if the conversion is possible.
   */
  @Override
  public Double toDouble() {
    return value.doubleValue();
  }

  /**
   * Convert {@code this} object to a {@code Float} object.
   *
   * @return a {@code Float} object if the conversion is possible.
   */
  @Override
  public Float toFloat() {
    float f = value.floatValue();
    return f;
  }

  /**
   * Convert {@code this} object to a {@code Integer} object.
   *
   * @return a {@code Integer} object if the conversion is possible.
   */
  @Override
  public Integer toInteger() {
    return value.intValueExact();
  }

  /**
   * Convert {@code this} object to a string with the representation of the JSON structure in a JSON
   * string form.
   *
   * @return a {@code String} with the JSON string representation of {@code this} object.
   */
  @Override
  public String toJSON() {
    if (value.intValue() == 0) { // To fix Java 7 trailing bug
      return "0";
    }
    String out = value.stripTrailingZeros().toPlainString();
    return out;
  }

  /**
   * Convert {@code this} object to a {@link JSONArray} object.
   *
   * @return a {@link JSONArray} object with only {@code this} element.
   */
  @Override
  public JSONArray toJSONArray() {
    JSONArray jsonArray = new JSONArray();
    jsonArray.add(value);
    return jsonArray;
  }

  /**
   * Convert {@code this} object to a {@link JSONString} object.
   *
   * @return a {@link JSONString} object if the conversion is possible.
   */
  @Override
  public JSONString toJSONString() {
    JSONString jsonString = new JSONString(value);
    return jsonString;
  }

  /**
   * Convert {@code this} object to a {@code List} of {@link JSONValue} objects.
   *
   * @return a {@code List} of {@link JSONValue} with {@code this} element.
   */
  @Override
  public List<JSONValue> toList() {
    List<JSONValue> list = new ArrayList<>();
    list.add(this);
    return list;
  }

  /**
   * Convert {@code this} object to a {@code Long} object.
   *
   * @return a {@code Long} object if the conversion is possible.
   */
  @Override
  public Long toLong() {
    return value.longValueExact();
  }

  /**
   * Convert {@code this} object to a {@code Short} object.
   *
   * @return a {@code Short} object if the conversion is possible.
   */
  @Override
  public Short toShort() {
    short s = value.shortValueExact();
    return s;
  }

  /**
   * Convert {@code this} object to a {@code String} using {@link JSONNumber#toJSON()}.
   *
   * @return a {@code Stirng}.
   */
  @Override
  public String toString() {
    return toJSON();
  }

  /**
   * Convert {@code this} object to an array of {@code String} objects.
   *
   * @return an array of {@code String} object with one element.
   */
  @Override
  public String[] toStringArray() {
    String[] s = new String[1];
    s[0] = value.toString();
    return s;
  }

}
