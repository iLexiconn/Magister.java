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

import net.ilexiconn.magister.cache.Cachable;
import net.ilexiconn.magister.cache.ContainerCache;
import net.ilexiconn.magister.container.sub.Link;

import java.io.Serializable;

public class Contact implements Serializable, Cachable {
    private int Id;
    private Link[] Links;
    private String Achternaam;
    private String Voornaam;
    private String Tussenvoegsel;
    private String Naam;
    private int Type;

    public Contact(int i, Link[] l, String s, String f, String p, String n, int t) {
        Id = i;
        Links = l;
        Achternaam = s;
        Voornaam = f;
        Tussenvoegsel = p;
        Naam = n;
        Type = t;

        if (ContainerCache.get(Id, getClass()) == null) {
            ContainerCache.put(this, getClass());
        }
    }

    public int getId() {
        return Id;
    }

    public Link[] getLinks() {
        return Links;
    }

    public String getSurname() {
        return Achternaam;
    }

    public String getFirstName() {
        return Voornaam;
    }

    public String getSurnamePrefix() {
        return Tussenvoegsel;
    }

    public String getFullName() {
        return Naam;
    }

    public int getType() {
        return Type;
    }
}
