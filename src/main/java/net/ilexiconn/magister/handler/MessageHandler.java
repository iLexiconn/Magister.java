/*
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

package net.ilexiconn.magister.handler;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import net.ilexiconn.magister.Magister;
import net.ilexiconn.magister.adapter.ArrayAdapter;
import net.ilexiconn.magister.adapter.MessageAdapter;
import net.ilexiconn.magister.adapter.SingleMessageAdapter;
import net.ilexiconn.magister.container.Message;
import net.ilexiconn.magister.container.MessageFolder;
import net.ilexiconn.magister.container.SingleMessage;
import net.ilexiconn.magister.container.type.MessageType;
import net.ilexiconn.magister.exeption.PrivilegeException;
import net.ilexiconn.magister.util.GsonUtil;
import net.ilexiconn.magister.util.HttpUtil;
import net.ilexiconn.magister.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageHandler implements IHandler {
    private Gson gson;
    private Magister magister;

    public MessageHandler(Magister magister) {
        this.magister = magister;
        Map<Class<?>, TypeAdapter<?>> map = new HashMap<Class<?>, TypeAdapter<?>>();
        map.put(MessageFolder[].class, new ArrayAdapter<MessageFolder>(MessageFolder.class, MessageFolder[].class));
        map.put(Message[].class, new MessageAdapter());
        map.put(SingleMessage[].class, new SingleMessageAdapter());
        gson = GsonUtil.getGsonWithAdapters(map);
    }

    /**
     * Get an array with all the {@link MessageFolder}s of this profile.
     *
     * @return an array with all the {@link MessageFolder}s of this profile.
     * @throws IOException        if there is no active internet connection.
     * @throws PrivilegeException if the profile doesn't have the privilege to perform this action.
     */
    public MessageFolder[] getMessageFolders() throws IOException {
        return gson.fromJson(HttpUtil.httpGet(magister.school.url + "/api/personen/" + magister.profile.id + "/berichten/mappen"), MessageFolder[].class);
    }

    /**
     * Get an array of {@link Message}s of a specific {@link MessageFolder}.
     *
     * @param folder the {@link MessageFolder} instance.
     * @return an array of {@link Message}s.
     * @throws IOException        if there is no active internet connection.
     * @throws PrivilegeException if the profile doesn't have the privilege to perform this action.
     */
    public Message[] getMessagesPerFolder(MessageFolder folder) throws IOException {
        return getMessagesPerFolder(folder.id);
    }

    /**
     * Get an array of {@link Message}s of a specific {@link MessageFolder}.
     *
     * @param folderID the {@link MessageFolder} ID.
     * @return an array of {@link Message}s.
     * @throws IOException        if there is no active internet connection.
     * @throws PrivilegeException if the profile doesn't have the privilege to perform this action.
     */
    public Message[] getMessagesPerFolder(int folderID) throws IOException {
        return gson.fromJson(HttpUtil.httpGet(magister.school.url + "/api/personen/" + magister.profile.id + "/berichten?mapId=" + folderID + "&orderby=soort+DESC&skip=0&top=25"), Message[].class);
    }

    /**
     * Get an array of {@link SingleMessage}s of this specific {@link Message}.
     *
     * @param message the {@link Message} instance.
     * @return an array of {@link SingleMessage}s.
     * @throws IOException        if there is no active internet connection.
     * @throws PrivilegeException if the profile doesn't have the privilege to perform this action.
     */
    public SingleMessage[] getSingleMessage(Message message) throws IOException {
        return getSingleMessage(message.id, message.type);
    }

    /**
     * Get an array of {@link SingleMessage}s of this specific {@link Message}.
     *
     * @param messageID the {@link Message} ID.
     * @return an array of {@link SingleMessage}s.
     * @throws IOException        if there is no active internet connection.
     * @throws PrivilegeException if the profile doesn't have the privilege to perform this action.
     */
    public SingleMessage[] getSingleMessage(int messageID, MessageType messageType) throws IOException {
        return gson.fromJson(HttpUtil.httpGet(magister.school.url + "/api/personen/" + magister.profile.id + "/berichten/" + messageID + "?berichtSoort=" + messageType.getName()), SingleMessage[].class);
    }

    /**
     * Post a single message to Magister.
     *
     * @param message the message.
     * @return true if the message got sent.
     */
    public boolean postMessage(SingleMessage message) {
        try {
            String data = gson.toJson(message);
            InputStreamReader respose = HttpUtil.httpPostRaw(magister.school.url + "/api/personen/" + magister.profile.id + "/berichten", data);
            return true;
        } catch (IOException e) {
            LogUtil.printError(e.getMessage(), e.getCause());
            return false;
        }
    }

    public Message updateMessage(SingleMessage message) throws IOException {
        String data = gson.toJson(message);
        return gson.fromJson(new JsonReader(HttpUtil.httpPut(magister.school.url + "/api/personen/" + magister.profile.id + "/berichten/" + message.id, data)), SingleMessage.class);
    }

    public Message markMessageRead(SingleMessage message, boolean isRead) throws IOException {
        message.isRead = isRead;
        String data = gson.toJson(message);
        return updateMessage(message);
    }

    public Message moveMessageTo(SingleMessage message, MessageFolder messageFolder) throws IOException {
        message.mapId = messageFolder.id;
        message.mapTitle = messageFolder.title;
        return updateMessage(message);
    }

    public void emptyMessageFolder(MessageFolder messageFolder) throws IOException {
        HttpUtil.httpDelete(magister.school.url + "api/personen/" + magister.profile.id + "/berichten/map/" + messageFolder.id);
    }

    /**
     * Get the attachments from a message and download them to a directory.
     *
     * @param message     the message.
     * @param downloadDir the directory to download the files to.
     * @return a list of files with the attachments. Will return null if there aren't any attachments bound with the
     * message.
     * @throws IOException if there is no active internet connection.
     */
    public File[] getAttachmentsOfMessage(SingleMessage message, File downloadDir) throws IOException {
        URL[] urls = message.getAttachmentsUrls(magister);
        if (urls == null || urls.length == 0) {
            return null;
        }
        List<File> files = new ArrayList<File>();
        for (URL url : urls) {
            files.add(HttpUtil.httpGetFile(url.toString(), downloadDir));
        }
        if (files.size() == 0) {
            return null;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrivilege() {
        return "Berichten";
    }
}
