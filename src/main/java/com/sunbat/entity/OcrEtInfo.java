package com.sunbat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author CPR066
 * @since 2020-12-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OcrEtInfo {

    private Map<String,String> data;
}
