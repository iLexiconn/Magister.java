/**
 * Copyright (c) 2015 iLexiconn
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
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
