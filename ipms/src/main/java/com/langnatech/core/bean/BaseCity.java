
package com.langnatech.core.bean;

import java.io.Serializable;

public class BaseCity implements Serializable {

    private static final long serialVersionUID = 7426624084199723479L;

    /** cityId:地市编码。 */

    private String cityId;

    /** cityName:地市名称。 */

    private String cityName;

    /** countyId:区县编码。 */

    private String countyId;

    /** countyName:区县名称。 */

    private String countyName;

    /** sectionId:片区编码。 */

    private String sectionId;

    /** sectionName:片区名称。 */

    private String sectionName;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

}
