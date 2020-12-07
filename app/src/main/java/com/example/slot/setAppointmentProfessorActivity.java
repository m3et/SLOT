package com.example.slot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class setAppointmentProfessorActivity extends AppCompatActivity {
    private Button setDate;
    private Button startTime, endTime;
    private Button back;
    private Calendar calendar;
    private DatePickerDialog dpd;
    private TimePickerDialog Stpd, Etpd;
    private int startHour, startMinute, endHour, endMinute;
    private int day,month,year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_appointment_professor);

        back=findViewById(R.id.backfromsetproffesor);
        setDate=findViewById(R.id.pickdate);
        startTime=findViewById(R.id.pickstarttime);
        endTime=findViewById(R.id.pickendtime);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(setAppointmentProfessorActivity.this,LecturerMainActivity.class));
                finish();
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();
                startHour=calendar.get(Calendar.HOUR_OF_DAY);
                startMinute=calendar.get(Calendar.MINUTE);
                Stpd= new TimePickerDialog(setAppointmentProfessorActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Toast.makeText(setAppointmentProfessorActivity.this, "שעת התחלה: "+hourOfDay+":"+minute,Toast.LENGTH_LONG).show();
                    }
                },startHour,startMinute,true);
                Stpd.show();
            }

        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();
                endHour=calendar.get(Calendar.HOUR_OF_DAY);
                endMinute=calendar.get(Calendar.MINUTE);
                Stpd= new TimePickerDialog(setAppointmentProfessorActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Toast.makeText(setAppointmentProfessorActivity.this, "שעת סיום: "+hourOfDay+":"+minute,Toast.LENGTH_LONG).show();
                    }
                },endHour,endMinute,true);
                Stpd.show();
            }
        });

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();

                day=calendar.get(Calendar.DAY_OF_MONTH);
                month=calendar.get(Calendar.MONTH);
                year=calendar.get(Calendar.YEAR);
                dpd=new DatePickerDialog(setAppointmentProfessorActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        Toast.makeText(setAppointmentProfessorActivity.this, "תאריך שהוגדר: "+dayOfMonth+"/"+month+"/"+year,Toast.LENGTH_LONG).show();
                    }
                },year,month,day);
                System.out.println();
                dpd.show();
            }
        });

    }


}