package com.example.ahmetburak.mirrorandroidapp;

import android.os.AsyncTask;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ApiAsyncTask extends AsyncTask<Void, Void, Void> {
    private CalendarActivity cActivity;


    ApiAsyncTask(CalendarActivity activity)
		//Constructor (CalendarActivity'i oluşturur)
    {
        this.cActivity = activity;
    }

	// Arkaplan görevlerini düzenler.
    @Override
    protected Void doInBackground(Void... params) {
        try {
            cActivity.clearResultsText();
            cActivity.updateResultsText(getDataFromApi());

        } catch (final GooglePlayServicesAvailabilityIOException availabilityException) {
            cActivity.showGooglePlayServicesAvailabilityErrorDialog(
                    availabilityException.getConnectionStatusCode());

        } catch (UserRecoverableAuthIOException userRecoverableException) {
            cActivity.startActivityForResult(
                    userRecoverableException.getIntent(),
                    CalendarActivity.REQUEST_AUTHORIZATION);

        } catch (IOException e) {
            cActivity.updateStatus("Hata meydana geldi: " +
                    e.getMessage());
        }
        return null;
    }

	/**
     * Takvimde kayıtlı etkinliklerden ilk 15 tanesini basar.
     * @return Tanımlanmış etkinliklerin string listesi.
     * @throws IOException
     */

    private List<String> getDataFromApi() throws IOException {

        DateTime now = new DateTime(System.currentTimeMillis());
        List<String> eventStrings = new ArrayList<String>();

        eventStrings.add(
            (String.format("%s ", "Tarih : " + now + "\n")));

        Events events = cActivity.mService.events().list("primary")
                .setMaxResults(15)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();

        for (Event event : items) {
            DateTime start = event.getStart().getDateTime();
            if (start == null) {
                start = event.getStart().getDate();
            }
            eventStrings.add(
                    String.format("%s (%s)", "Etkinlik : " + event.getSummary() +"\n", start));
        }
        return eventStrings;
    }

}