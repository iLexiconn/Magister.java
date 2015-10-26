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

public class Contact {
    private int Id;
    private Link[] Links;
    private String Achternaam;
    private String Voornaam;
    private String Tussenvoegsel;
    private String Naam;
    private int Type;

    public int getId() {
        return Id;
    }

    public void setId(int i) {
        Id = i;
    }

    public Link[] getLinks() {
        return Links;
    }

    public void setLinks(Link[] l) {
        Links = l;
    }

    public String getSurname() {
        return Achternaam;
    }

    public void setSurname(String s) {
        Achternaam = s;
    }

    public String getFirstName() {
        return Voornaam;
    }

    public void setFirstName(String s) {
        Voornaam = s;
    }

    public String getSurnamePrefix() {
        return Tussenvoegsel;
    }

    public void setSurnamePrefix(String s) {
        Tussenvoegsel = s;
    }

    public String getFullName() {
        return Naam;
    }

    public void setFullName(String s) {
        Naam = s;
    }

    public int getType() {
        return Type;
    }

    public void setType(int i) {
        Type = i;
    }
}
