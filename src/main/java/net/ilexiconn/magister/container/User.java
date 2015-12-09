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

import com.google.gson.annotations.SerializedName;
import net.ilexiconn.magister.Magister;
import net.ilexiconn.magister.exeption.PrivilegeException;
import net.ilexiconn.magister.util.HttpUtil;
import net.ilexiconn.magister.util.LogUtil;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    private Magister magister;

    @SerializedName("Gebruikersnaam")
    private String username;

    @SerializedName("Wachtwoord")
    private String password;

    @SerializedName("IngelogdBlijven")
    private boolean stayLoggedIn;

    public User(Magister magister, String username, String password, boolean stayLoggedIn) {
        this.magister = magister;
        this.username = username;
        this.password = password;
        this.stayLoggedIn = stayLoggedIn;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean stayLoggedIn() {
        return stayLoggedIn;
    }

    /**
     * Change the password of the current profile.
     *
     * @param oldPassword  the current password.
     * @param newPassword  the new password.
     * @param newPassword2 the new password.
     * @return a String with the response. 'Successful' if the password changed successfully.
     * @throws IOException               if there is no active internet connection.
     * @throws InvalidParameterException if one of the parameters is null or empty, or when the two new passwords aren't
     *                                   the same.
     * @throws PrivilegeException        if the profile doesn't have the privilege to perform this action.
     */
    public String changePassword(String oldPassword, String newPassword, String newPassword2) throws IOException, InvalidParameterException, PrivilegeException {
        if (!magister.hasPrivilege("WachtwoordWijzigen")) {
            throw new PrivilegeException();
        }
        if (oldPassword == null || oldPassword.isEmpty() || newPassword == null || newPassword.isEmpty() || newPassword2 == null || newPassword2.isEmpty()) {
            throw new InvalidParameterException("Parameters can't be null or empty!");
        } else if (!newPassword.equals(newPassword2)) {
            throw new InvalidParameterException("New passwords don't match!");
        }
        Map<String, String> nameValuePairMap = new HashMap<String, String>();
        nameValuePairMap.put("HuidigWachtwoord", oldPassword);
        nameValuePairMap.put("NieuwWachtwoord", newPassword);
        nameValuePairMap.put("PersoonId", magister.profile.id + "");
        nameValuePairMap.put("WachtwoordBevestigen", newPassword2);
        Response response = magister.gson.fromJson(HttpUtil.httpPost(magister.school.url + "/api/personen/account/wachtwoordwijzigen?persoonId=" + magister.profile.id, nameValuePairMap), Response.class);
        if (response == null) {
            return "Successful";
        } else {
            LogUtil.printError(response.message, new InvalidParameterException());
            return response.message;
        }
    }
}
