package edu.ranken.emeier.chatterboxlab.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Message {

    // fields
    private String _user;
    private String _message;
    private String _time;

    public Message(String user, String message, String time) {
        _user = user;
        _message = message;

        // convert time to desired format
        // [ 12:53 ]
        try {
//            SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.US);
//            Date lastUpdateDate = format.parse(time);
//            _time = format.format(lastUpdateDate);

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z", Locale.US);
            SimpleDateFormat format2 = new SimpleDateFormat("HH:mm", Locale.US);
            Date date = format1.parse(time);
            _time = format2.format(date);
        } catch (Exception e) { // catches ParseException
            // if parse fails, keep unformatted string
            _time = time;
        }
    }

    public String getUser() {
        return _user;
    }

    public String getMessage() {
        return _message;
    }

    public String getTime() {
        return _time;
    }
}
