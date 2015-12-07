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

package net.ilexiconn.magister.container;


public class User {
    private String Gebruikersnaam;
    private String Wachtwoord;
    private boolean IngelogdBlijven;

    public User(String username, String password, boolean stayLoggedIn) {
        this.Gebruikersnaam = username;
        this.Wachtwoord = password;
        this.IngelogdBlijven = stayLoggedIn;
    }
    public String getPassword() {
        return Wachtwoord;
    }

    public void setPassword(String password) {
        Wachtwoord = password;
    }

    public String getUsername() {
        return Gebruikersnaam;
    }

    public void setUsername(String username) {
        Gebruikersnaam = username;
    }

    public boolean isStayLoggedIn() {
        return IngelogdBlijven;
    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        IngelogdBlijven = stayLoggedIn;
    }
}
