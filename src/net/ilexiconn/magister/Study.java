package net.ilexiconn.magister;

public class Study {
    private Items[] Items;
    private int TotalCount;

    public Items[] getItems() {
        return Items;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public class Items {
        private int Id;
        private Links[] Links;
        private int LeerlingId;
        private String Start;
        private String Einde;
        private String Lesperiode;
        private Studie Studie;
        private Groep Groep;
        private boolean AanBronMelden;

        public int getId() {
            return Id;
        }

        public Links[] getLinks() {
            return Links;
        }

        public int getPupilId() {
            return LeerlingId;
        }

        public String getStart() {
            return Start;
        }

        public String getEnd() {
            return Einde;
        }

        public String getClassPeriod() {
            return Lesperiode;
        }

        public Studie getStudy() {
            return Studie;
        }

        public Groep getGroup() {
            return Groep;
        }

        public boolean notifySource() {
            return AanBronMelden;
        }

        public class Links {
            private String href;
            private String rel;

            public String getHref() {
                return href;
            }

            public String getRel() {
                return rel;
            }
        }

        public class Studie {
            private int Id;
            private String Omschrijving;

            public int getId() {
                return Id;
            }

            public String getDescription() {
                return Omschrijving;
            }
        }

        public class Groep {
            private int Id;
            private Links[] Links;
            private String Omschrijving;
            private int LocatieId;

            public int getId() {
                return Id;
            }

            public Links[] getLinks() {
                return Links;
            }

            public String getDescription() {
                return Omschrijving;
            }

            public int getLocationId() {
                return LocatieId;
            }
        }
    }
}
