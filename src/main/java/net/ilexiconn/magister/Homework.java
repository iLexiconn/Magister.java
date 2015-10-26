/*
 * Copyright (c) 2015 iLexiconn
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */
package net.ilexiconn.magister;

public class Homework {
    private Items[] Items;
    private int TotalCount;

    public Homework.Items[] getItems() {
        return Items;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public class Items {
        private int Id;
        private Links[] Links;
        private String Start;
        private String Einde;
        private int LesuurVan;
        private int LesuurTotMet;
        private boolean DuurtHeleDag;
        private String Omschrijving;
        private String Lokatie;
        private int Status;
        private int Type;
        private int WeergaveType;
        private int InfoType;
        private String Aantekening;
        private boolean Afgerond;
        private Vakken[] Vakken;
        private Docenten[] Docenten;
        private Lokalen[] Lokalen;
        private int OpdrachtId;
        private boolean HeeftBijlagen;

        public int getId() {
            return Id;
        }

        public Links[] getLinks() {
            return Links;
        }

        public String getStart() {
            return Start;
        }

        public String getEnd() {
            return Einde;
        }

        public int getStartingHour() {
            return LesuurVan;
        }

        public int getEndingHour() {
            return LesuurTotMet;
        }

        public boolean isWholeDay() {
            return DuurtHeleDag;
        }

        public String getDescription() {
            return Omschrijving;
        }

        public String getLoction() {
            return Lokatie;
        }

        public int getStatus() {
            return Status;
        }

        public int getType() {
            return Type;
        }

        public int getDisplayType() {
            return WeergaveType;
        }

        public int getInfoType() {
            return InfoType;
        }

        public String getNote() {
            return Aantekening;
        }

        public boolean isFinished() {
            return Afgerond;
        }

        public Vakken[] getSubjects() {
            return Vakken;
        }

        public Docenten[] getTeachers() {
            return Docenten;
        }

        public Lokalen[] getClassrooms() {
            return Lokalen;
        }

        public int getExerciseId() {
            return OpdrachtId;
        }

        public boolean hasAttachment() {
            return HeeftBijlagen;
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

        public class Vakken {
            private int Id;
            private String Naam;

            public int getId() {
                return Id;
            }

            public String getName() {
                return Naam;
            }
        }

        public class Docenten {
            private int Id;
            private String Naam;
            private String Docentcode;

            public int getId() {
                return Id;
            }

            public String getNaame() {
                return Naam;
            }

            public String getTeacherCode() {
                return Docentcode;
            }
        }

        public class Lokalen {
            private String Naam;

            public String getName() {
                return Naam;
            }
        }
    }
}
