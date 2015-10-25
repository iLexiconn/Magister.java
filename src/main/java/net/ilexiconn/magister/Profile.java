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

public class Profile {
    private Persoon Persoon;
    private Groep[] Groep;

    public Persoon getPerson() {
        return Persoon;
    }

    public Groep[] getGroups() {
        return Groep;
    }

    public class Persoon {
        private int Id;
        private String Roepnaam;
        private String Tussenvoegsel;
        private String Achternaam;
        private String OfficieleVoornamen;
        private String Voorletters;
        private String OfficieleTussenvoegsels;
        private String OfficieleAchternaam;
        private String Geboortedatum;
        private String GeboorteAchternaam;
        private String GeboortenaamTussenvoegsel;
        private boolean GebruikGeboortenaam;

        public int getId() {
            return Id;
        }

        public String getNickname() {
            return Roepnaam;
        }

        public String getSurnamePrefix() {
            return Tussenvoegsel;
        }

        public String getSurname() {
            return Achternaam;
        }

        public String getOfficialInitials() {
            return OfficieleVoornamen;
        }

        public String getInitials() {
            return Voorletters;
        }

        public String getOfficialSurnamePrefix() {
            return OfficieleTussenvoegsels;
        }

        public String getOfficialSurname() {
            return OfficieleAchternaam;
        }

        public String getDateOfBirth() {
            return Geboortedatum;
        }

        public String getBirthSurname() {
            return GeboorteAchternaam;
        }

        public String getBirthSurnamePrefix() {
            return GeboortenaamTussenvoegsel;
        }

        public boolean useBirthName() {
            return GebruikGeboortenaam;
        }
    }

    public class Groep {
        private String Naam;
        private Privileges[] Privileges;

        public String getName() {
            return Naam;
        }

        public Privileges[] getPrivileges() {
            return Privileges;
        }

        public class Privileges {
            private String Naam;
            private String[] AccessType;

            public String getName() {
                return Naam;
            }

            public String[] getAccessType() {
                return AccessType;
            }
        }
    }
}
