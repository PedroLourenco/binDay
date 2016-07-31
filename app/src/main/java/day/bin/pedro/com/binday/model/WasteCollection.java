package day.bin.pedro.com.binday.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by pedro on 30/07/16.
 */
public class WasteCollection {

    @SerializedName("WasteType")
    private String wasteType;
    @SerializedName("WasteTypeDescription")
    private String wasteTypeDescription;
    @SerializedName("NextCollection")
    private String nextCollection;
    @SerializedName("Collectionday")
    private String collectionday;
    @SerializedName("MaterialsCollected")
    private String materialsCollected;
    @SerializedName("ShortAddress")
    private String shortAddress;
    @SerializedName("PropertyNumber")
    private int propertyNumber;
    @SerializedName("PropertyName")
    private String propertyName;
    @SerializedName("Street")
    private String street;
    @SerializedName("Locality")
    private String locality;
    @SerializedName("Postcode")
    private String postcode;
    @SerializedName("CollectionAvailable")
    private String collectionAvailable;
    @SerializedName("LastCollection")
    private String lastCollection;
    @SerializedName("BinType")
    private String binType;
    @SerializedName("Provider")
    private String provider;

    public String getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(String shortAddress) {
        shortAddress = shortAddress;
    }

    public int getPropertyNumber() {
        return propertyNumber;
    }

    public void setPropertyNumber(int propertyNumber) {
        propertyNumber = propertyNumber;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        propertyName = propertyName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        street = street;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        locality = locality;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        postcode = postcode;
    }

    public String getCollectionAvailable() {
        return collectionAvailable;
    }

    public void setCollectionAvailable(String collectionAvailable) {
        collectionAvailable = collectionAvailable;
    }

    public String getLastCollection() {
        return lastCollection;
    }

    public void setLastCollection(String lastCollection) {
        lastCollection = lastCollection;
    }

    public String getBinType() {
        return binType;
    }

    public void setBinType(String binType) {
        binType = binType;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        provider = provider;
    }

    public String getWasteType() {
        return wasteType;
    }

    public void setWasteType(String wasteType) {
        wasteType = wasteType;
    }

    public String getWasteTypeDescription() {
        return wasteTypeDescription;
    }

    public void setWasteTypeDescription(String wasteTypeDescription) {
        wasteTypeDescription = wasteTypeDescription;
    }

    public String getNextCollection() {
        return nextCollection;
    }

    public void setNextCollection(Date nextCollection) {
        nextCollection = nextCollection;
    }

    public String getCollectionday() {
        return collectionday;
    }

    public void setCollectionday(String collectionday) {
        collectionday = collectionday;
    }

    public String getMaterialsCollected() {
        return materialsCollected;
    }

    public void setMaterialsCollected(String materialsCollected) {
        materialsCollected = materialsCollected;
    }
}
