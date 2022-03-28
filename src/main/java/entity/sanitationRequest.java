package entity;

public class sanitationRequest {

  public enum Size {
    Light,
    Medium,
    Heavy
  }

  public enum Biohazard {
    Yes,
    No,
    Unsure
  }

  public enum Critical {
    Critical,
    NotCritical
  }

  String location;
  String additionalNotes;
  Size sizeOfCleaning;
  Biohazard biohazardOnSite;
  Critical urgency;

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getAdditionalNotes() {
    return additionalNotes;
  }

  public void setAdditionalNotes(String additionalNotes) {
    this.additionalNotes = additionalNotes;
  }

  public Size getSizeOfCleaning() {
    return sizeOfCleaning;
  }

  public void setSizeOfCleaning(Size sizeOfCleaning) {
    this.sizeOfCleaning = sizeOfCleaning;
  }

  public Biohazard getBiohazardOnSite() {
    return biohazardOnSite;
  }

  public void setBiohazardOnSite(Biohazard biohazardOnSite) {
    this.biohazardOnSite = biohazardOnSite;
  }

  public Critical getUrgency() {
    return urgency;
  }

  public void setUrgency(Critical urgency) {
    this.urgency = urgency;
  }
}
