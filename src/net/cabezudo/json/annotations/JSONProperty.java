package net.cabezudo.json.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * Marks an object attribute like a property of a JSON object. You can also indicate with the 
 * annotation the name you want for the field in
 * the JSON object using the element {code name}. Also you can indicate if you want to show the
 * property in the result JSON object if the property value is zero or if the property is {code
 * null}. If the field will be a referenciable field you can specify the field that be used like
 * reference with the element {code field}. Also you can specify if the field can be referenced or
 * not using the element {@code referenced}.
 *
 * @author Esteban Cabezudo
 * @version 1.0
 * @since 1.7
 * @date 07/06/2016
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JSONProperty {

  /**
   * A string indicating if the name of the field will be the same for the JSON object property.
   */
  public static final String DEFAULT_NAME = "The same as the name of the property.";

  /**
   * The default property name for referenced fields.
   */
  public static final String DEFAULT_REFERENCED_PROPERTY = "id";

  /**
   * The name of the JSON object.
   *
   * @return a string indicating that the name of the JSON object property. If the method return
   * {code DEFAULT_NAME} the library must use the field name.
   */
  String name() default DEFAULT_NAME;

  /**
   * Indicate if the property should be included in the JSON object if is zero or not.
   *
   * @return {@code true if the property with null value will not show in the JSON object, {@code false} otherwise.
   */
  boolean dontShowIfZero() default false;

  /**
   * Indicate if the property should be included in the JSON object if it is null or not.
   *
   * @return {@code true if the property with null value will not show in the JSON object, {@code false} otherwise.
   */
  boolean dontShowIfNull() default false;

  /**
   *
   * Return the referenced field name used for an object in the annotated field. The default value
   * is {@code id}.
   *
   * @return the name of the field used like reference.
   */
  String field() default DEFAULT_REFERENCED_PROPERTY;

  /**
   *
   * Return if the field can be referenced or not. The default value is {code true}.
   *
   * @return {@code true} if the field is referenced {code false} otherwise.
   */
  boolean referenced() default true;
}
