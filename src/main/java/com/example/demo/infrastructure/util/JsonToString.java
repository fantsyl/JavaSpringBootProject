package com.example.demo.infrastructure.util;

import com.example.demo.infrastructure.handler.JsonToStringDeserializerHandler;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.RoundingMode;

/**
 * 轉字串
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@JacksonAnnotationsInside
@JsonSerialize(using = JsonToStringDeserializerHandler.class)
public @interface JsonToString {

  /**
   * 是否使用四捨五入設定,預設false;
   */
  boolean isRounding() default false;

  /**
   * 進位位數,預設0;
   */
  int scale() default 0;

  /**
   * 四捨五入模式,預設HALF_UP;
   * @see RoundingMode
   */
  RoundingMode mode() default RoundingMode.HALF_UP;

  /**
   * 是否使用DecimalFormat設定,預設false;
   * @see java.text.DecimalFormat
   */
  boolean isDecimalFormat() default false;

  /**
   * 最大的小數位呈現,當isDecimalFormat=true時生效,且設定值應和scale相同;
   */
  int maximumFractionDigits() default 0;

  /**
   * 最小的小數位呈現,當isDecimalFormat=true時生效,預設0;
   * 假如值設定2,當value為1234時,會轉換成字串"1234.00"
   */
  int minimumFractionDigits() default 0;

  /**
   * 是否使用千位分號,當isDecimalFormat=true時生效,預設false;
   */
  boolean isGroupingUsed() default false;

}
