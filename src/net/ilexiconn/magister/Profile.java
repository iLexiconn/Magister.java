package net.ilexiconn.magister;

public class Profile
{
    private Persoon Persoon;
    private Groep[] Groep;

    public class Persoon
    {
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

        public int getId()
        {
            return Id;
        }

        public String getNickname()
        {
            return Roepnaam;
        }

        public String getSurnamePrefix()
        {
            return Tussenvoegsel;
        }

        public String getSurname()
        {
            return Achternaam;
        }

        public String getOfficialInitials()
        {
            return OfficieleVoornamen;
        }

        public String getInitials()
        {
            return Voorletters;
        }

        public String getOfficialSurnamePrefix()
        {
            return OfficieleTussenvoegsels;
        }

        public String getOfficialSurname()
        {
            return OfficieleAchternaam;
        }

        public String getDateOfBirth()
        {
            return Geboortedatum;
        }

        public String getBirthSurname()
        {
            return GeboorteAchternaam;
        }

        public String getBirthSurnamePrefix()
        {
            return GeboortenaamTussenvoegsel;
        }

        public boolean useBirthName()
        {
            return GebruikGeboortenaam;
        }
    }

    public class Groep
    {
        private String Naam;
        private Privileges[] Privileges;

        public class Privileges
        {
            private String Naam;
            private String[] AccessType;

            public String getName()
            {
                return Naam;
            }

            public String[] getAccessType()
            {
                return AccessType;
            }
        }

        public String getName()
        {
            return Naam;
        }

        public Privileges[] getPrivileges()
        {
            return Privileges;
        }
    }

    public Persoon getPerson()
    {
        return Persoon;
    }

    public Groep[] getGroups()
    {
        return Groep;
    }
}
