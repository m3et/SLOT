package com.example.slot.professor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.slot.InternetBroadcastReceiver;
import com.example.slot.MainActivity;
import com.example.slot.utilclasses.Appointment;
import com.example.slot.R;
import com.example.slot.utilclasses.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Map;

public class setAppointmentProfessorActivity extends AppCompatActivity {
    private Button setDate;
    private Button startTime, endTime;
    private Button back;
    private Button set;
    private Calendar calendar;
    private DatePickerDialog dpd;
    private TimePickerDialog Stpd, Etpd;
    private int startHour, startMinute, endHour, endMinute, interval;
    private int day,month,year;
    private String course;
    private EditText courseName;
    private EditText editTextInterval;
    private FirebaseAuth auth;
    private User user;
    InternetBroadcastReceiver ibr=new InternetBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_appointment_professor);
        courseName = findViewById(R.id.Coursename);
        back = findViewById(R.id.backfromsetproffesor);
        setDate = findViewById(R.id.pickdate);
        startTime = findViewById(R.id.pickstarttime);
        endTime = findViewById(R.id.pickendtime);
        editTextInterval = findViewById(R.id.interval);
        set = findViewById(R.id.setmeetingProffesor);


//        String course= courseName.getText().toString();
        //int Interval=Integer.parseInt(interval.getText().toString());
        auth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("LecturerUser").child(auth.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                System.out.println(dataSnapshot.getValue(User.class));
                user = dataSnapshot.getValue(User.class);
//                System.out.println(user.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*
         * Save to DB
         * */
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interval = Integer.parseInt(editTextInterval.getText().toString());
//                System.out.println(startHour + "," + startMinute + "," + endHour + "," + endMinute + "," + interval + "," + day + "," + month + "," + year);
                Appointment appointment = new Appointment(startHour, startMinute, endHour, endMinute, interval, day, month, year);
//                System.out.println(appointment.toMap());
                String course = courseName.getText().toString();
                String key = course + "-" + user.getName() + "-" +appointment.getDate();

                Map<String, Object> dataMap = appointment.toMap();
                dataMap.put("LecturerID",FirebaseAuth.getInstance().getUid());
                FirebaseDatabase.getInstance().getReference().child("appointments").child(key).setValue(dataMap); //putting appointments in the DB

                startActivity(new Intent(setAppointmentProfessorActivity.this, ProfessorMainActivity.class));
                finish();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(setAppointmentProfessorActivity.this, ProfessorMainActivity.class));
                finish();
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(setAppointmentProfessorActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        startHour = selectedHour;
                        startMinute = selectedMinute;
                        Toast.makeText(setAppointmentProfessorActivity.this, "שעת התחלה: "+ selectedHour + ":" + selectedMinute, Toast.LENGTH_LONG).show();
                    }
                },hour,minute,true);
                timePickerDialog.setTitle("בחר שעת התחלה");
                timePickerDialog.show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(setAppointmentProfessorActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        endHour = selectedHour;
                        endMinute = selectedMinute;
                        Toast.makeText(setAppointmentProfessorActivity.this, "שעת סיום: "+ selectedHour + ":" +selectedMinute, Toast.LENGTH_LONG).show();
                    }
                },hour,minute,true);
                timePickerDialog.setTitle("בחר שעת סיום");
                timePickerDialog.show();
            }
        });

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                int mMonth = calendar.get(Calendar.MONTH);
                int mYear = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(setAppointmentProfessorActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        year = selectedYear;
                        month = selectedMonth + 1;
                        day = selectedDay;
                        Toast.makeText(setAppointmentProfessorActivity.this, "תאריך שהוגדר: "+selectedDay+"/"+(selectedMonth+1)+"/"+selectedYear,Toast.LENGTH_LONG).show();
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.setTitle("בחר תאריך");
                datePickerDialog.show();
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(ibr, filter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(ibr);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_logout){
            auth.signOut();
            startActivity(new Intent(setAppointmentProfessorActivity.this, MainActivity.class));
            finish();
        }else if(item.getItemId()==R.id.menu_backToMain){
            startActivity(new Intent(setAppointmentProfessorActivity.this, ProfessorMainActivity.class));
            finish();
        }else{
            return super.onOptionsItemSelected(item);
        }
        return true;
    }


}