package net.ilexiconn.magister;

public class Mark {
    private Items[] Items;

    public Mark.Items[] getItems() {
        return Items;
    }

    public class Items {
        private int CijferId;
        private String CijferStr;
        private boolean IsVoldoende;
        private String DatumIngevoerd;
        private CijferPeriode CijferPeriode;
        private Vak Vak;
        private boolean Inhalen;
        private boolean Vrijstelling;
        private boolean TeltMee;

        private int CijferKolomIdEloOpdracht;
        private String Docent;
        private boolean VakDispensatie;
        private boolean VakVrijstelling;

        public int getMarkId() {
            return CijferId;
        }

        public String getMark() {
            return CijferStr;
        }

        public boolean isSufficient() {
            return IsVoldoende;
        }

        public String getDate() {
            return DatumIngevoerd;
        }

        public Mark.Items.CijferPeriode getMarkPeriod() {
            return CijferPeriode;
        }

        public Mark.Items.Vak getSubject() {
            return Vak;
        }

        public boolean canCatch() {
            return Inhalen;
        }

        public boolean hasExemption() {
            return Vrijstelling;
        }

        public boolean doesCount() {
            return TeltMee;
        }

        public int getMarkColumn() {
            return CijferKolomIdEloOpdracht;
        }

        public String getTeacher() {
            return Docent;
        }

        public boolean getMarkDispensation() {
            return VakDispensatie;
        }

        public boolean hasSubjectExemption() {
            return VakVrijstelling;
        }

        public class CijferPeriode {
            private int Id;
            private String Naam;
            private int VolgNummer;

            public int getId() {
                return Id;
            }

            public String getName() {
                return Naam;
            }

            public int getFollowNumber() {
                return VolgNummer;
            }
        }

        public class Vak {
            private int Id;
            private String Afkorting;
            private String Omschrijving;
            private int Volgnr;

            public int getId() {
                return Id;
            }

            public String getAbbreviation() {
                return Afkorting;
            }

            public String getDescription() {
                return Omschrijving;
            }

            public int getFollowNumber() {
                return Volgnr;
            }
        }

        public class CijferKolom {
            private int Id;
            private String KolomNaam;
            private String KolomNummer;
            private String KolomVolgNummer;
            private String KolomKop;
            private String KolomOmschrijving;
            private int KolomSoort;
            private boolean IsHerkansingKolom;
            private boolean IsDocentKolom;
            private boolean HeeftOnderliggendeKolommen;
            private boolean IsPTAKolom;

            public int getId() {
                return Id;
            }

            public String getColumnName() {
                return KolomNaam;
            }

            public String getColumnNumber() {
                return KolomNummer;
            }

            public String getColumnFollowNumber() {
                return KolomVolgNummer;
            }

            public String getColumnHeading() {
                return KolomKop;
            }

            public String getColumnDescription() {
                return KolomOmschrijving;
            }

            public int getColumnType() {
                return KolomSoort;
            }

            public boolean isResitColumn() {
                return IsHerkansingKolom;
            }

            public boolean isTeacherColumn() {
                return IsDocentKolom;
            }

            public boolean hasColumnUnderneath() {
                return HeeftOnderliggendeKolommen;
            }

            public boolean isPTAColumn() {
                return IsPTAKolom;
            }
        }
    }
}