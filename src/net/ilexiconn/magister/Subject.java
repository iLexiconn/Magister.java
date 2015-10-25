package net.ilexiconn.magister;

public class Subject {
    private int id;
    private int studieVakId;
    private int studieId;
    private String afkorting;
    private String omschrijving;
    private boolean vrijstelling;
    private boolean dispensatie;
    private int volgnr;
    private String docent;
    private String begindatum;
    private String einddatum;
    private boolean hogerNiveau;

    public int getId() {
        return id;
    }

    public int getStudySubjectId() {
        return studieVakId;
    }

    public int getStudyId() {
        return studieId;
    }

    public String getAbbreviation() {
        return afkorting;
    }

    public String getDescription() {
        return omschrijving;
    }

    public boolean hasExemption() {
        return vrijstelling;
    }

    public boolean hasDispensation() {
        return dispensatie;
    }

    public int getFollowNumber() {
        return volgnr;
    }

    public String getTeacher() {
        return docent;
    }

    public String getStart() {
        return begindatum;
    }

    public String getEnd() {
        return einddatum;
    }

    public boolean isHigherLevel() {
        return hogerNiveau;
    }
}
