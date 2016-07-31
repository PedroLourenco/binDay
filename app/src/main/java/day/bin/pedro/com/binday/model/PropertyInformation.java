package day.bin.pedro.com.binday.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pedro on 28/07/16.
 */
public class PropertyInformation {

    @SerializedName("Uprn")
    private String uprn;
    @SerializedName("ShortAddress")
    private String shortAddress;
    @SerializedName("PropertyNumber")
    private int propertyNumber;
    //@SerializedName("PropertyName")
   // private String propertyName;
    @SerializedName("Street")
    private String street;
    @SerializedName("Locality")
    private String locality;
    @SerializedName("Postcode")
    private String postcode;

    public String getUprn() {
        return uprn;
    }

    public void setUprn(String uprn) {
        this.uprn = uprn;
    }

    public String getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(String shortAddress) {
        this.shortAddress = shortAddress;
    }

    public int getPropertyNumber() {
        return propertyNumber;
    }

    public void setPropertyNumber(int propertyNumber) {
        this.propertyNumber = propertyNumber;
    }

    //public String getPropertyName() {
    //    return propertyName;
    //}

    //public void setPropertyName(String propertyName) {
      //  this.propertyName = propertyName;
    //}

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
