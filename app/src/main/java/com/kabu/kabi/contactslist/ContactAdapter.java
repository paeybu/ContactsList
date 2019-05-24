package com.kabu.kabi.contactslist;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContactAdapter extends CursorAdapter {
    public ContactAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int ColumnIndex_ID = cursor.getColumnIndex(ContactsContract.Contacts._ID);
        int ColumnIndex_DISPLAY_NAME = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        int ColumnIndex_HAS_PHONE_NUMBER = cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER);

        String id = cursor.getString(ColumnIndex_ID);
        String name = cursor.getString(ColumnIndex_DISPLAY_NAME);
        String has_phone = cursor.getString(ColumnIndex_HAS_PHONE_NUMBER);

        TextView nameTv = view.findViewById(R.id.name);
        nameTv.setText(name);
        String number = "";

        if (!has_phone.endsWith("0")) {
            number = GetPhoneNumber(context, id);
        }
        TextView numberTv = view.findViewById(R.id.number);
        numberTv.setText(number);
    }

    private String GetPhoneNumber(Context context, String id) {
        String number = "";
        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (phones.getCount() > 0) {
            phones.moveToPosition(Integer.parseInt(id)-1);
            number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }
        Log.v(ContactAdapter.class.getSimpleName(), "number @ id " + id + " = " + number + "\nphones count = " + phones.getCount());
        return number;
    }
}
