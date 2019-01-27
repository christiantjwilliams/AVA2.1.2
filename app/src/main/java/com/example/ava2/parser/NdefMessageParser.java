package com.example.ava2.parser;


import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;

import com.example.ava2.R;
//import com.example.ava2.model.History;
import com.example.ava2.record.ParsedNdefRecord;
import com.example.ava2.record.SmartPoster;
import com.example.ava2.record.TextRecord;
import com.example.ava2.record.UriRecord;
import com.example.ava2.utils.NFCReaderApp;

import java.util.ArrayList;
import java.util.List;


public class NdefMessageParser {

    private NdefMessageParser() {
    }

    public static List<ParsedNdefRecord> parse(NdefMessage message) {
        return getRecords(message.getRecords());
    }

    public static List<ParsedNdefRecord> getRecords(NdefRecord[] records) {
        List<ParsedNdefRecord> elements = new ArrayList<ParsedNdefRecord>();

        for (final NdefRecord record : records) {
            if (UriRecord.isUri(record)) {
                elements.add(UriRecord.parse(record));
            } else if (TextRecord.isText(record)) {
                elements.add(TextRecord.parse(record));
            } else if (SmartPoster.isPoster(record)) {
                elements.add(SmartPoster.parse(record));
            } else {
                elements.add(new ParsedNdefRecord() {
                    public String str() {
                        return new String(record.getPayload());
                    }
                });
            }
        }

        return elements;
    }
}